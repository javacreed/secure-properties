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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.javacreed.api.secureproperties.encoder.EncodedProperties;
import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.io.ReaderPropertyParser;
import com.javacreed.api.secureproperties.writer.io.LinePropertyEntryWriter;

/**
 * TODO: we need to make this configurable
 */
public class PropertiesFilePlaceholderConfigurer extends AbstractPropertyPlaceholderConfigurer {

  private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesFilePlaceholderConfigurer.class);

  @Override
  protected String convertProperty(final String propertyName, final String propertyValue) {
    if (isEncrypted(propertyValue)) {
      try {
        PropertiesFilePlaceholderConfigurer.LOGGER.debug("Decoding property value: '{}'", propertyValue);
        final NameValuePropertyEntry decoded = (NameValuePropertyEntry) propertyDecoder
            .decode(new EncodedNameValuePropertyEntry(propertyName, propertyValue.substring(5)));
        return super.convertPropertyValue(decoded.getValue());
      } catch (final Exception e) {
        throw EncoderException.launder("Failed to decode value", e);
      }
    }

    return super.convertPropertyValue(propertyValue);
  }

  private Resource[] encodeProperties(final Resource[] modifiableLocations) throws Exception {
    for (final Resource resource : modifiableLocations) {
      final File file = resource.getFile().getAbsoluteFile();
      PropertiesFilePlaceholderConfigurer.LOGGER.debug("Processing properties file: {}", file);

      final List<PropertyEntry> properties = ReaderPropertyParser.readAndClose(file);
      if (PropertiesFilePlaceholderConfigurer.LOGGER.isDebugEnabled()) {
        PropertiesFilePlaceholderConfigurer.LOGGER.debug("File: {} has {} entries", file.getName(), properties.size());
        for (final PropertyEntry entry : properties) {
          PropertiesFilePlaceholderConfigurer.LOGGER.debug("  >> {}", entry);
        }
      }

      PropertiesFilePlaceholderConfigurer.LOGGER.debug("Encoding properties");
      final EncodedProperties encodedProperties = encoder.encode(properties);
      PropertiesFilePlaceholderConfigurer.LOGGER.debug("Properties encoding complete");

      if (encodedProperties.wereEncoded()) {
        PropertiesFilePlaceholderConfigurer.LOGGER.debug("Writing properties to file: {}", file);
        LinePropertyEntryWriter.writeAndClose(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),
            "UTF-8")), encodedProperties);
      } else {
        PropertiesFilePlaceholderConfigurer.LOGGER.debug("No properties required encoding");
      }
    }

    return modifiableLocations;
  }

  public void setModifiableLocations(final Resource[] modifiableLocations) throws Exception {
    encodeProperties(modifiableLocations);
    setLocations(modifiableLocations);
  }
}
