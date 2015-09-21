/*
 * #%L
 * JavaCreed Secure Properties Examples
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
package com.javacreed.api.secureproperties.examples.example1;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.db.ResultSetPropertyParser;

public class Example implements AutoCloseable {

  private static final Logger LOGGER = LoggerFactory.getLogger(Example.class);

  public static void main(final String[] args) throws Exception {
    try (Example example = new Example()) {
      // Connect to the database and setup the required table
      example.connect();
      example.setup();

      // Add some properties
      example.addProperty("name1", 0, "value1", true);
      example.addProperty("name2", 0, "value1", false);
      example.addProperty("name3", 1, "value2", true);
      example.addProperty("name4", 2, "value3", true);
      example.addProperty("name5", 0, null, true);

      final String query = "SELECT * FROM `properties` WHERE `enabled`=1";

      // Load all properties
      final List<PropertyEntry> properties = ResultSetPropertyParser.readAndClose(example.getConnection(), query,
          new CustomResultSetExtractor());

      Example.LOGGER.debug("Found {} properties", properties.size());
      for (final PropertyEntry propertyEntry : properties) {
        Example.LOGGER.debug("  >> {} (of type: {})", propertyEntry, propertyEntry.getClass());
      }
    }
  }

  private BasicDataSource dataSource;

  private void addProperty(final String name, final int type, final String value, final boolean enabled)
      throws SQLException {
    if (value == null) {
      execute("INSERT INTO `properties` VALUES ('" + name + "', " + type + ", NULL, " + (enabled ? 1 : 0) + ")");
    } else {
      execute("INSERT INTO `properties` VALUES ('" + name + "', " + type + ", '" + value + "', " + (enabled ? 1 : 0)
          + ")");
    }
  }

  @Override
  public void close() throws Exception {
    if (dataSource != null) {
      dataSource.close();
    }
  }

  private void connect() {
    dataSource = new BasicDataSource();
    dataSource.setDriverClassName("org.h2.Driver");
    dataSource.setUrl("jdbc:h2:./target/test");
    dataSource.setUsername("sa");
    dataSource.setPassword("");
  }

  private void execute(final String query) throws SQLException {
    try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
      statement.execute(query);
    }
  }

  private Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  private void setup() throws SQLException {
    execute("DROP TABLE IF EXISTS `properties`");
    execute(//@formatter:off
        " CREATE TABLE `properties` ( " +
        "   `name`    VARCHAR(64)  NOT NULL, " +
        "   `encoded` TINYINT(1)   NOT NULL, " +
        "   `value`   VARCHAR(255) DEFAULT NULL, " +
        "   `enabled` TINYINT(1)   NOT NULL DEFAULT 1, " +
        "   PRIMARY KEY(`name`) " +
        " )");//@formatter:off

  }
}
