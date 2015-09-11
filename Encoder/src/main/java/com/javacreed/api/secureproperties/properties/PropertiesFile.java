package com.javacreed.api.secureproperties.properties;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.api.secureproperties.decoder.DefaultPropertyDecoder;
import com.javacreed.api.secureproperties.decoder.PropertyDecoder;
import com.javacreed.api.secureproperties.encoder.DefaultPropertiesEncoder;
import com.javacreed.api.secureproperties.encoder.EncodedProperties;
import com.javacreed.api.secureproperties.encoder.PropertiesEncoder;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.io.ReaderPropertyParser;
import com.javacreed.api.secureproperties.writer.io.LinePropertyEntryWriter;

public class PropertiesFile {

  private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesFile.class);

  /** */
  private final PropertyDecoder propertyDecoder = new DefaultPropertyDecoder();

  /** */
  private final PropertiesEncoder encoder = new DefaultPropertiesEncoder();

  /**
   *
   * @param file
   * @return
   * @throws Exception
   */
  public Properties loadProperties(final File file) throws Exception {
    final List<PropertyEntry> properties = ReaderPropertyParser.readAndClose(file);
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

    return toProperties(encodedProperties);
  }

  /**
   *
   * @param encodedProperties
   * @return
   */
  public Properties toProperties(final EncodedProperties encodedProperties) {
    final Properties properties = new Properties();
    for (final PropertyEntry property : encodedProperties.getEntries()) {
      if (property instanceof NameValuePropertyEntry) {
        final NameValuePropertyEntry e = (NameValuePropertyEntry) propertyDecoder.decode(property);
        properties.setProperty(e.getName(), e.getValue());
      }
    }

    return properties;
  }
}
