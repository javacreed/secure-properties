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
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.javacreed.api.secureproperties.cipher.CipherFactory;
import com.javacreed.api.secureproperties.encoder.PropertiesEncoder;
import com.javacreed.api.secureproperties.properties.PropertiesTable;

/**
 *
 * @author Albert Attard
 */
public class PropertiesTablePlaceholderConfigurer extends PropertyPlaceholderConfigurer {

  /** */
  private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesTablePlaceholderConfigurer.class);

  private final PropertiesTable properties = new PropertiesTable();

  /**
   *
   * @param dataSource
   * @throws NullPointerException
   */
  public PropertiesTablePlaceholderConfigurer(final DataSource dataSource) throws NullPointerException {
    this.properties.setDataSource(dataSource);
  }

  @Override
  protected void loadProperties(final Properties properties) throws IOException {
    this.properties.loadProperties(properties);
  }

  public void setCipherFactory(final CipherFactory cipherFactory) throws NullPointerException {
    properties.setCipherFactory(cipherFactory);
  }

  public void setDataSource(final DataSource dataSource) throws NullPointerException {
    properties.setDataSource(dataSource);
  }

  public void setEncoder(final PropertiesEncoder encoder) throws NullPointerException {
    properties.setEncoder(encoder);
  }

  public void setKey(final String key) throws NullPointerException {
    properties.setKey(key);
  }

  public void setNameColumnName(final String nameColumnName) {
    properties.setNameColumnName(nameColumnName);
  }

  public void setTableName(final String tableName) {
    properties.setTableName(tableName);
  }

  public void setValueColumnName(final String valueColumnName) {
    properties.setValueColumnName(valueColumnName);
  }

}
