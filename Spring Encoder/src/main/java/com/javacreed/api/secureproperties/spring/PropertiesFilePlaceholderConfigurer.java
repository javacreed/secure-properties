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

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import com.javacreed.api.secureproperties.cipher.CipherFactory;
import com.javacreed.api.secureproperties.encoder.PropertiesEncoder;
import com.javacreed.api.secureproperties.parser.io.LinePropertyEntryParser;
import com.javacreed.api.secureproperties.properties.PropertiesFile;

/**
 * TODO: we need to make this configurable
 */
public class PropertiesFilePlaceholderConfigurer extends PropertyPlaceholderConfigurer {

  private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesFilePlaceholderConfigurer.class);

  private final PropertiesFile propertiesFile = new PropertiesFile();

  @Override
  protected String convertProperty(final String propertyName, final String propertyValue) {
    return super.convertPropertyValue(propertiesFile.convertProperty(propertyName, propertyValue));
  }

  private Resource[] encodeProperties(final Resource[] modifiableLocations) throws Exception {
    for (final Resource resource : modifiableLocations) {
      final File file = resource.getFile().getAbsoluteFile();
      if (file.isFile()) {
        PropertiesFilePlaceholderConfigurer.LOGGER.debug("Processing properties file: {}", file);
        propertiesFile.loadProperties(file);
      }
    }

    return modifiableLocations;
  }

  public void setCipherFactory(final CipherFactory cipherFactory) {
    propertiesFile.setCipherFactory(cipherFactory);
  }

  public void setEncoder(final PropertiesEncoder encoder) throws NullPointerException {
    this.propertiesFile.setEncoder(encoder);
  }

  public void setKey(final String key) {
    this.propertiesFile.setKey(key);
  }

  public void setLineParser(final LinePropertyEntryParser lineParser) throws NullPointerException {
    propertiesFile.setLineParser(lineParser);
  }

  public void setLineParserLabels(final String plainTextLabel, final String encodedLabel) throws NullPointerException,
  IllegalArgumentException {
    propertiesFile.setLineParserLabels(plainTextLabel, encodedLabel);
  }

  public void setModifiableLocations(final Resource[] modifiableLocations) throws Exception {
    encodeProperties(modifiableLocations);
    setLocations(modifiableLocations);
  }
}
