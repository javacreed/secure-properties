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
package com.javacreed.api.secureproperties.parser.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.AbstractPropertiesParser;

/**
 *
 * @author Albert Attard
 */
public class ResultSetPropertyParser extends AbstractPropertiesParser {

  public static List<PropertyEntry> readAndClose(final Connection connection, final String query) throws SQLException {
    return ResultSetPropertyParser.readAndClose(connection, query, new DefaultResultSetExtractor());
  }

  public static List<PropertyEntry> readAndClose(final Connection connection, final String query,
      final ResultSetExtractor extractor) throws SQLException {
    try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
      final ResultSetPropertyParser parser = new ResultSetPropertyParser(resultSet, extractor);
      return parser.getProperties();
    }
  }

  public static List<PropertyEntry> readAndClose(final DataSource dataSource, final String query) throws SQLException {
    return ResultSetPropertyParser.readAndClose(dataSource, query, new DefaultResultSetExtractor());
  }

  public static List<PropertyEntry> readAndClose(final DataSource dataSource, final String query,
      final ResultSetExtractor extractor) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      return ResultSetPropertyParser.readAndClose(connection, query, extractor);
    }
  }

  /** */
  private final ResultSet resultSet;

  /** */
  private final ResultSetExtractor extractor;

  /**
   *
   * @param resultSet
   * @throws NullPointerException
   */
  public ResultSetPropertyParser(final ResultSet resultSet) throws NullPointerException {
    this(resultSet, new DefaultResultSetExtractor());
  }

  /**
   *
   * @param resultSet
   * @param extractor
   * @throws NullPointerException
   */
  public ResultSetPropertyParser(final ResultSet resultSet, final ResultSetExtractor extractor)
      throws NullPointerException {
    this.resultSet = Objects.requireNonNull(resultSet);
    this.extractor = Objects.requireNonNull(extractor);
  }

  @Override
  protected PropertyEntry readNext() throws Exception {
    if (resultSet.next()) {
      return extractor.extract(resultSet);
    }

    return null;
  }

}
