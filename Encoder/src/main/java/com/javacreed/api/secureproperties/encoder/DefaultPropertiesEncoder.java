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
package com.javacreed.api.secureproperties.encoder;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.api.secureproperties.cipher.CipherFactory;
import com.javacreed.api.secureproperties.model.PropertyEntry;

/**
 *
 * @author Albert Attard
 */
public class DefaultPropertiesEncoder implements PropertiesEncoder {

  /**
   *
   * @author Albert Attard
   */
  private static class DefaultEncodedProperties implements EncodedProperties {

    private int encoded;
    private final List<PropertyEntry> entries = new LinkedList<>();
    private final List<PropertyEntry> unmodifiable = Collections.unmodifiableList(entries);

    @Override
    public Iterable<PropertyEntry> getEntries() {
      return unmodifiable;
    }

    @Override
    public int getNumberOfEncodedProperties() {
      return encoded;
    }

    @Override
    public boolean wereEncoded() {
      return encoded > 0;
    }
  }

  /** Class logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPropertiesEncoder.class);

  /** */
  private PropertyEncoder encoder;

  public DefaultPropertiesEncoder() {
    this("javacreed");
  }

  public DefaultPropertiesEncoder(final CipherFactory cipherFactory) {
    this.encoder = new DefaultPropertyEncoder(cipherFactory);
  }

  public DefaultPropertiesEncoder(final PropertyEncoder encoder) throws NullPointerException {
    this.encoder = Objects.requireNonNull(encoder);
  }

  public DefaultPropertiesEncoder(final String key) {
    this(new DefaultPropertyEncoder(key));
  }

  @Override
  public EncodedProperties encode(final Iterable<PropertyEntry> propertiesEntries) throws EncoderException {
    return encode(propertiesEntries.iterator());
  }

  @Override
  public EncodedProperties encode(final Iterator<PropertyEntry> propertiesEntries) throws EncoderException,
      IllegalStateException {
    final DefaultEncodedProperties encodedProperties = new DefaultEncodedProperties();

    while (propertiesEntries.hasNext()) {
      final PropertyEntry entry = propertiesEntries.next();
      try {
        final PropertyEntry encodedEntry = encoder.encode(entry);
        encodedProperties.entries.add(encodedEntry);
        if (encodedEntry != entry) {
          DefaultPropertiesEncoder.LOGGER.debug("The property {} was modified (encoded) thus we need to write them",
              encodedEntry);
          encodedProperties.encoded++;
        }
      } catch (final RuntimeException e) {
        DefaultPropertiesEncoder.LOGGER.error("Failed to encode property {}", entry, e);
        throw EncoderException.launder(e);
      }
    }

    return encodedProperties;
  }

  public void setEncoder(final PropertyEncoder encoder) {
    this.encoder = Objects.requireNonNull(encoder);
  }

  public DefaultPropertiesEncoder use(final PropertyEncoder encoder) throws NullPointerException {
    setEncoder(encoder);
    return this;
  }
}
