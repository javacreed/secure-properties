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
package com.javacreed.secureproperties.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * @author Albert Attard
 */
public class DbHelper {

  /**
   *
   * @param closeable
   */
  public static void closeQuietly(final AutoCloseable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (final Exception e) {}
    }
  }

  /**
   *
   * @return
   * @throws SQLException
   */
  public static DbHelper create() throws SQLException {
    final BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName("org.h2.Driver");
    dataSource.setUrl("jdbc:h2:./target/test");
    dataSource.setUsername("sa");
    dataSource.setPassword("");

    final DbHelper helper = new DbHelper(dataSource);
    return helper;
  }

  /** */
  private final BasicDataSource dataSource;

  /**
   *
   * @param dataSource
   * @throws NullPointerException
   */
  public DbHelper(final BasicDataSource dataSource) throws NullPointerException {
    this.dataSource = Objects.requireNonNull(dataSource);
  }

  /**
   *
   */
  public void close() {
    DbHelper.closeQuietly(dataSource);
  }

  // public Statement createStatement() throws SQLException {
  // return connection.createStatement();
  // }

  /**
   *
   * @param query
   * @throws SQLException
   */
  public void execute(final String query) throws SQLException {
    try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
      statement.execute(query);
    }
  }

  /**
   *
   * @return
   * @throws SQLException
   */
  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  /**
   *
   * @return
   */
  public DataSource getDataSource() {
    return dataSource;
  }

  /**
   *
   * @param query
   * @return
   * @throws SQLException
   */
  public String queryForSingleValue(final String query) throws SQLException {
    try (Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);) {
      if (resultSet.next()) {
        final String value = resultSet.getString(1);

        if (resultSet.next()) {
          throw new SQLException("More than one row was returned");
        }

        return value;
      }
    }

    throw new SQLException("No rows were returned");
  }
}
