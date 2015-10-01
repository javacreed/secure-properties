/*
 * #%L
 * JavaCreed Secure Properties Spring Encoder
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
package com.javacreed.api.secureproperties.spring;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.api.secureproperties.encoder.EncodedProperties;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.db.ResultSetPropertyParser;
import com.javacreed.api.secureproperties.writer.db.DbPropertyEntryWriter;

/**
 *
 * @author Albert Attard
 */
public class PropertiesTablePlaceholderConfigurer extends AbstractPropertyPlaceholderConfigurer {

  /** */
  private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesTablePlaceholderConfigurer.class);

  /** */
  private final DataSource dataSource;

  private String tableName = "properties";

  /** */
  private String query = "SELECT * FROM `properties`";

  /**
   *
   * @param dataSource
   * @throws NullPointerException
   */
  public PropertiesTablePlaceholderConfigurer(final DataSource dataSource) throws NullPointerException {
    this.dataSource = Objects.requireNonNull(dataSource);
  }

  @Override
  protected void loadProperties(final Properties properties) throws IOException {
    List<PropertyEntry> list;
    try {
      list = ResultSetPropertyParser.readAndClose(dataSource, query);
    } catch (final SQLException e) {
      throw new IOException("Failed to read the properties", e);
    }
    if (PropertiesTablePlaceholderConfigurer.LOGGER.isDebugEnabled()) {
      PropertiesTablePlaceholderConfigurer.LOGGER.debug("Query: {} returned {} entries", query, list.size());
      for (final PropertyEntry entry : list) {
        PropertiesTablePlaceholderConfigurer.LOGGER.debug("  >> {}", entry);
      }
    }

    PropertiesTablePlaceholderConfigurer.LOGGER.debug("Encoding properties");
    final EncodedProperties encodedProperties = encoder.encode(list);
    PropertiesTablePlaceholderConfigurer.LOGGER.debug("Properties encoding complete");

    if (encodedProperties.wereEncoded()) {
      PropertiesTablePlaceholderConfigurer.LOGGER.debug("Writing properties to table: {}", tableName);
      try {
        DbPropertyEntryWriter.write(dataSource, encodedProperties);
      } catch (final Exception e) {
        throw new IOException("Failed to write the properties", e);
      }
    } else {
      PropertiesTablePlaceholderConfigurer.LOGGER.debug("No properties required encoding");
    }
  }

  public void setQuery(final String query) throws NullPointerException {
    this.query = Objects.requireNonNull(query);
  }

  /**
   *
   * @param tableName
   * @throws NullPointerException
   */
  public void setTableName(final String tableName) throws NullPointerException {
    this.tableName = Objects.requireNonNull(tableName);
    setQuery("SELECT * FROM `" + tableName + "`");
  }
}
