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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.api.secureproperties.encoder.EncodedProperties;
import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.encoder.PropertiesEncoder;
import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.io.DefaultLinePropertyEntryParser;
import com.javacreed.api.secureproperties.parser.io.LinePropertyEntryParser;
import com.javacreed.api.secureproperties.parser.io.ReaderPropertyParser;
import com.javacreed.api.secureproperties.writer.io.LinePropertyEntryWriter;

public class PropertiesFile extends AbstractProperties {

  private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesFile.class);

  /** */
  private LinePropertyEntryParser lineParser = new DefaultLinePropertyEntryParser();

  public PropertiesFile() {}

  /**
   *
   * @param encoder
   * @throws NullPointerException
   */
  public PropertiesFile(final PropertiesEncoder encoder) throws NullPointerException {
    setEncoder(Objects.requireNonNull(encoder));
  }

  public String convertEncodedValue(final String value) {
    return lineParser.convertEncodedValue(value);
  }

  public String convertPlainTextValue(final String value) {
    return lineParser.convertPlainTextValue(value);
  }

  public String convertProperty(final String propertyName, final String propertyValue) {
    if (propertyName != null && propertyValue != null) {
      if (isEncodedValue(propertyValue)) {
        try {
          PropertiesFile.LOGGER.debug("Decoding property value: '{}'='{}'", propertyName, propertyValue);
          final PlainTextNameValuePropertyEntry decoded = decode(propertyName, propertyValue);
          return decoded.getValue();
        } catch (final Exception e) {
          throw EncoderException.launder("Failed to decode value", e);
        }
      }

      if (isPlainTextValue(propertyValue)) {
        return convertPlainTextValue(propertyValue);
      }
    }

    return propertyValue;
  }

  public PlainTextNameValuePropertyEntry decode(final String propertyName, final String propertyValue) {
    if (lineParser.isEncodedValue(propertyValue)) {
      return decode(new EncodedNameValuePropertyEntry(propertyName, lineParser.convertEncodedValue(propertyValue)));
    }

    return decode(new EncodedNameValuePropertyEntry(propertyName, propertyValue));
  }

  public EncodedProperties encodeProperties(final File file) throws Exception {
    final List<PropertyEntry> properties = ReaderPropertyParser.readAndClose(file, lineParser);
    if (PropertiesFile.LOGGER.isDebugEnabled()) {
      PropertiesFile.LOGGER.debug("File: {} has {} entries", file.getName(), properties.size());
      for (final PropertyEntry entry : properties) {
        PropertiesFile.LOGGER.debug("  >> {}", entry);
      }
    }

    PropertiesFile.LOGGER.debug("Encoding properties");
    final EncodedProperties encodedProperties = encoder.encode(properties);
    PropertiesFile.LOGGER.debug("Properties encoding complete");

    if (encodedProperties.wereEncoded()) {
      PropertiesFile.LOGGER.debug("Writing properties to file: {}", file);
      LinePropertyEntryWriter.writeAndClose(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),
          "UTF-8")), encodedProperties);
    } else {
      PropertiesFile.LOGGER.debug("No properties required encoding");
    }

    return encodedProperties;
  }

  public boolean isEncodedValue(final String value) {
    return lineParser.isEncodedValue(value);
  }

  public boolean isPlainTextValue(final String value) {
    return lineParser.isPlainTextValue(value);
  }

  /**
   *
   * @param file
   * @return
   * @throws Exception
   */
  public Properties loadProperties(final File file) throws Exception {
    final EncodedProperties encodedProperties = encodeProperties(file);
    return toProperties(encodedProperties);
  }

  public void setLineParser(final LinePropertyEntryParser lineParser) throws NullPointerException {
    this.lineParser = Objects.requireNonNull(lineParser);
  }

  public void setLineParserLabels(final String plainTextLabel, final String encodedLabel) throws NullPointerException,
      IllegalArgumentException {
    this.setLineParser(new DefaultLinePropertyEntryParser(plainTextLabel, encodedLabel));
  }

}
