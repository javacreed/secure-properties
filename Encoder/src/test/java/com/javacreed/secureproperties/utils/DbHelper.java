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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * Created by Albert on 24/08/2015.
 */
public class DbHelper {

  public static void close(final AutoCloseable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (final Exception e) {}
    }
  }

  public static DbHelper create() throws SQLException {
    final Connection connection = DriverManager.getConnection("jdbc:h2:./target/test", "sa", "");
    final DbHelper helper = new DbHelper(connection);
    return helper;
  }

  private final Connection connection;

  public DbHelper(final Connection connection) {
    this.connection = Objects.requireNonNull(connection);
  }

  public void close() {
    DbHelper.close(connection);
  }

  public Statement createStatement() throws SQLException {
    return connection.createStatement();
  }

  public void execute(final String query) throws SQLException {
    try (Statement statement = createStatement()) {
      statement.execute(query);
    }
  }

  public Connection getConnection() {
    return connection;
  }

  public String queryForSingleValue(final String query) throws SQLException {
    try (Statement statement = createStatement(); ResultSet resultSet = statement.executeQuery(query);) {
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
