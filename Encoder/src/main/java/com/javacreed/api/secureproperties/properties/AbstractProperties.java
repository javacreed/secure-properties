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

import java.util.Objects;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.api.secureproperties.cipher.CipherFactory;
import com.javacreed.api.secureproperties.encoder.DefaultPropertiesEncoder;
import com.javacreed.api.secureproperties.encoder.EncodedProperties;
import com.javacreed.api.secureproperties.encoder.PropertiesEncoder;
import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;

public class AbstractProperties {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProperties.class);

  /** */
  protected PropertiesEncoder encoder = new DefaultPropertiesEncoder();

  public Properties copyProperties(final EncodedProperties encodedProperties, final Properties properties) {
    for (final PropertyEntry property : encodedProperties.getEntries()) {
      final NameValuePropertyEntry e;
      if (property instanceof EncodedNameValuePropertyEntry) {
        e = decode((EncodedNameValuePropertyEntry) property);
      } else if (property instanceof NameValuePropertyEntry) {
        e = (NameValuePropertyEntry) property;
      } else {
        continue;
      }

      properties.setProperty(e.getName(), e.getValue());
    }

    return properties;
  }

  public PlainTextNameValuePropertyEntry decode(final EncodedNameValuePropertyEntry encoded)
      throws NullPointerException {
    return encoder.decode(encoded);
  }

  public void setCipherFactory(final CipherFactory cipherFactory) throws NullPointerException {
    this.encoder = new DefaultPropertiesEncoder(Objects.requireNonNull(cipherFactory));
  }

  public void setEncoder(final PropertiesEncoder encoder) throws NullPointerException {
    this.encoder = Objects.requireNonNull(encoder);
  }

  public void setKey(final String key) throws NullPointerException {
    this.encoder = new DefaultPropertiesEncoder(key);
  }

  /**
   *
   * @param encodedProperties
   * @return
   */
  public Properties toProperties(final EncodedProperties encodedProperties) {
    return copyProperties(encodedProperties, new Properties());
  }
}
