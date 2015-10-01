/*
 * #%L
 * JavaCreed Secure Properties Encoder
 * %%
 * Copyright (C) 2012 - 2015 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.api.secureproperties.writer.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import javax.sql.DataSource;

import net.jcip.annotations.NotThreadSafe;

import com.javacreed.api.secureproperties.encoder.EncodedProperties;
import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.model.DefaultValueLabel;
import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.model.ValueLabel;
import com.javacreed.api.secureproperties.utils.ExceptionsUtils;
import com.javacreed.api.secureproperties.writer.AbstractPropertyEntryWriter;

/**
 * Writes the properties to the database.
 * <p>
 * This class requires the database to support transactions. If the database does not support transactions, then the
 * behaviour of this class may be compromised. The properties should be persisted only when the {@link #commit()} method
 * is invoked. With a non-transactional database the properties may be persisted with every write instead when
 * committed.
 *
 * @author Albert Attard
 */
@NotThreadSafe
public class DbPropertyEntryWriter extends AbstractPropertyEntryWriter {

  /**
   *
   * @author Albert Attard
   */
  private class EncodedNameValuePairHandler extends AbstractHandler<EncodedNameValuePropertyEntry> {

    private EncodedNameValuePairHandler() {
      super(EncodedNameValuePropertyEntry.class);
    }

    @Override
    public void handle(final EncodedNameValuePropertyEntry entry) throws Exception {
      executeUpdate(entry.getName(), valueLabel.getEncodedLabel() + Objects.requireNonNull(entry.getValue()));
    }
  }

  /**
   *
   * @author Albert Attard
   */
  private class NameValuePairHandler extends AbstractHandler<NameValuePropertyEntry> {

    private NameValuePairHandler() {
      super(NameValuePropertyEntry.class);
    }

    @Override
    public void handle(final NameValuePropertyEntry entry) throws Exception {
      executeUpdate(entry.getName(), entry.getValue());
    }
  }

  /**
   *
   * @author Albert Attard
   */
  private class PlainTextNameValuePairHandler extends AbstractHandler<PlainTextNameValuePropertyEntry> {

    private PlainTextNameValuePairHandler() {
      super(PlainTextNameValuePropertyEntry.class);
    }

    @Override
    public void handle(final PlainTextNameValuePropertyEntry entry) throws Exception {
      executeUpdate(entry.getName(), valueLabel.getPlainTextLabel() + Objects.requireNonNull(entry.getValue()));
    }
  }

  /** */
  public static final String DEFAULT_TABLE_NAME = "properties";

  /**
   *
   * @param dataSource
   * @param properties
   * @throws Exception
   */
  public static void write(final DataSource dataSource, final EncodedProperties properties) throws Exception {
    final DbPropertyEntryWriter dbpew = new DbPropertyEntryWriter(dataSource);

    // TODO: this should be moved elsewhere
    try {
      dbpew.begin();
      for (final PropertyEntry entry : properties.getEntries()) {
        dbpew.write(entry);
      }
      dbpew.commit();
    } catch (final Exception e) {
      dbpew.failed(e);
    }
  }

  /** */
  private final ValueLabel valueLabel;

  /** */
  private final DataSource dataSource;

  /** */
  private final String tableName;

  /** */
  private Connection connection;

  /**
   *
   * @param dataSource
   * @throws NullPointerException
   */
  public DbPropertyEntryWriter(final DataSource dataSource) throws NullPointerException {
    this(dataSource, DbPropertyEntryWriter.DEFAULT_TABLE_NAME);
  }

  /**
   *
   * @param dataSource
   * @param tableName
   * @throws NullPointerException
   */
  public DbPropertyEntryWriter(final DataSource dataSource, final String tableName) throws NullPointerException {
    this(dataSource, new DefaultValueLabel(), tableName);
  }

  /**
   *
   * @param dataSource
   * @param valueLabel
   * @param tableName
   * @throws NullPointerException
   */
  public DbPropertyEntryWriter(final DataSource dataSource, final ValueLabel valueLabel, final String tableName)
      throws NullPointerException {
    this.dataSource = Objects.requireNonNull(dataSource);
    this.valueLabel = Objects.requireNonNull(valueLabel);
    this.tableName = Objects.requireNonNull(tableName);
    registerHandlers();
  }

  @Override
  public void begin() throws EncoderException {
    if (connection != null) {
      throw new EncoderException(
          "Another connection is already established.  Please close the previous connection first");
    }

    try {
      connection = dataSource.getConnection();
      connection.setAutoCommit(false);
    } catch (final SQLException e) {
      throw EncoderException.launder(e);
    }
  }

  /**
   * Closes the connection and suppressing any exceptions.
   */
  private void closeConnectionQuietly() {
    if (connection != null) {
      try {
        connection.close();
      } catch (final Exception e) {
        if (ExceptionsUtils.isCausedBy(e, InterruptedException.class)) {
          Thread.currentThread().interrupt();
        }
        // Suppress error
      } finally {
        connection = null;
      }
    }
  }

  @Override
  public void commit() throws EncoderException {
    try {
      connection.commit();
    } catch (final SQLException e) {
      throw EncoderException.launder(e);
    } finally {
      closeConnectionQuietly();
    }
  }

  /**
   *
   * @param connection
   * @param name
   * @param value
   * @return
   * @throws SQLException
   * @throws NullPointerException
   */
  private PreparedStatement createPreparedStatement(final Connection connection, final String name, final String value)
      throws SQLException, NullPointerException {
    final String query = "UPDATE `" + tableName + "` SET `value`=? WHERE `name`=?";
    final PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setString(1, value);
    preparedStatement.setString(2, name);
    return preparedStatement;
  }

  /**
   *
   * @param name
   * @param value
   * @throws SQLException
   */
  private void executeUpdate(final String name, final String value) throws SQLException {
    try (PreparedStatement preparedStatement = createPreparedStatement(connection, name, value)) {
      final int updateCount = preparedStatement.executeUpdate();
      if (updateCount != 1) {
        throw new SQLException("Expected one update but found " + updateCount);
      }
    }
  }

  @Override
  public void failed(final Exception e) {
    try {
      connection.rollback();
    } catch (final SQLException e2) {
      throw EncoderException.launder(e2);
    } finally {
      closeConnectionQuietly();
    }
  }

  /**
   *
   */
  private void registerHandlers() {
    registerHandler(new NameValuePairHandler());
    registerHandler(new EncodedNameValuePairHandler());
    registerHandler(new PlainTextNameValuePairHandler());
  }
}
