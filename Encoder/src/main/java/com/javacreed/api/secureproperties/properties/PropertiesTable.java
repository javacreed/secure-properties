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
package com.javacreed.api.secureproperties.properties;

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
import com.javacreed.api.secureproperties.utils.EntryWriterUtils;
import com.javacreed.api.secureproperties.writer.db.DbPropertyEntryWriter;
import com.javacreed.api.secureproperties.writer.db.DbPropertyEntryWriter.Builder;

public class PropertiesTable extends AbstractProperties {

  private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesTable.class);

  private DataSource dataSource;

  private String tableName = "properties";

  private String nameColumnName = "name";

  private String valueColumnName = "value";

  protected String createQuery() {
    return "SELECT `" + nameColumnName + "`, `" + valueColumnName + "` FROM `" + tableName + "`";
  }

  public void loadProperties(final Properties properties) throws IOException {

    final String query = createQuery();

    List<PropertyEntry> list;
    try {
      list = ResultSetPropertyParser.readAndClose(dataSource, query);
    } catch (final SQLException e) {
      throw new IOException("Failed to read the properties", e);
    }
    if (PropertiesTable.LOGGER.isDebugEnabled()) {
      PropertiesTable.LOGGER.debug("Query: {} returned {} entries", query, list.size());
      for (final PropertyEntry entry : list) {
        PropertiesTable.LOGGER.debug("  >> {}", entry);
      }
    }

    PropertiesTable.LOGGER.debug("Encoding properties");
    final EncodedProperties encodedProperties = encoder.encode(list);
    PropertiesTable.LOGGER.debug("Properties encoding complete");

    if (encodedProperties.wereEncoded()) {
      PropertiesTable.LOGGER.debug("Writing properties to table: {}", tableName);
      try {
        final Builder builder = new DbPropertyEntryWriter.Builder(dataSource);
        builder.setTableName(tableName);
        builder.setPropertyNameColumnName(nameColumnName);
        builder.setPropertyValueColumnName(valueColumnName);
        EntryWriterUtils.write(builder.build(), encodedProperties);
      } catch (final Exception e) {
        throw new IOException("Failed to write the properties", e);
      }
    } else {
      PropertiesTable.LOGGER.debug("No properties required encoding");
    }
  }

  public void setDataSource(final DataSource dataSource) throws NullPointerException {
    this.dataSource = Objects.requireNonNull(dataSource);
  }

  public void setNameColumnName(final String nameColumnName) {
    this.nameColumnName = Objects.requireNonNull(nameColumnName);
  }

  public void setTableName(final String tableName) {
    this.tableName = Objects.requireNonNull(tableName);
  }

  public void setValueColumnName(final String valueColumnName) {
    this.valueColumnName = Objects.requireNonNull(valueColumnName);
  }
}
