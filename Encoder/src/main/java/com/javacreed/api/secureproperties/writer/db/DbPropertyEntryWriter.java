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

import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.writer.AbstractPropertyEntryWriter;

/**
 * TODO: we should not allow write before begin
 */
public class DbPropertyEntryWriter extends AbstractPropertyEntryWriter {

  private class NameValuePairHandler extends AbstractHandler<NameValuePropertyEntry> {

    private NameValuePairHandler() {
      super(NameValuePropertyEntry.class);
    }

    @Override
    public void handle(final NameValuePropertyEntry entry) throws Exception {
      try (PreparedStatement preparedStatement = createPreparedStatement(connection, entry)) {
        final int updateCount = preparedStatement.executeUpdate();
        if (updateCount != 1) {
          throw new SQLException("Expected one update but found " + updateCount);
        }
      }
    }
  }

  private Connection connection;

  private String tableName = "properties";

  public DbPropertyEntryWriter() {
    registerHandlers();
  }

  public DbPropertyEntryWriter(final Connection connection) {
    setConnection(connection);
    registerHandlers();
  }

  public DbPropertyEntryWriter(final Connection connection, final String tableName) {
    setConnection(connection);
    setTableName(tableName);
    registerHandlers();
  }

  @Override
  public void begin() throws EncoderException {
    try {
      connection.setAutoCommit(false);
      connection.rollback();
    } catch (final SQLException e) {
      throw EncoderException.launder(e);
    }
  }

  @Override
  public void commit() throws EncoderException {
    try {
      connection.commit();
    } catch (final SQLException e) {
      throw EncoderException.launder(e);
    }
  }

  // public boolean accept(PropertyEntry entry){
  // return (entry instanceof NameValuePropertyEntry);
  // }

  protected PreparedStatement createPreparedStatement(final Connection connection, final NameValuePropertyEntry entry)
      throws SQLException {
    final String query = "UPDATE `" + tableName + "` SET `value`=? WHERE `name`=?";
    final PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setString(1, entry.getValue());
    preparedStatement.setString(2, entry.getName());
    return preparedStatement;
  }

  @Override
  public void failed(final Exception e) {
    try {
      connection.rollback();
    } catch (final SQLException e2) {
      throw EncoderException.launder(e2);
    }
  }

  private void registerHandlers() {
    registerHandler(new NameValuePairHandler());
  }

  public void setConnection(final Connection connection) {
    this.connection = Objects.requireNonNull(connection);
  }

  public void setTableName(final String tableName) {
    this.tableName = Objects.requireNonNull(tableName);
  }
}
