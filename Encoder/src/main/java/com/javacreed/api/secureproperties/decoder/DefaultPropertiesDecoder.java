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
package com.javacreed.api.secureproperties.decoder;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.model.PropertyEntry;

/**
 *
 */
public class DefaultPropertiesDecoder implements PropertiesDecoder {

  private static class DefaultDecodedProperties implements DecodedProperties {

    private int decoded;
    private final List<PropertyEntry> entries = new LinkedList<>();
    private final List<PropertyEntry> unmodifiable = Collections.unmodifiableList(entries);

    @Override
    public Iterable<PropertyEntry> getEntries() {
      return unmodifiable;
    }

    @Override
    public int getNumberOfDecodedProperties() {
      return decoded;
    }

    @Override
    public boolean wereDecoded() {
      return decoded > 0;
    }
  }

  /** The properties decoder (which will never be {@code null}) */
  private PropertyDecoder decoder;

  /**
   * Creates an instance of this class with the default configuration
   */
  public DefaultPropertiesDecoder() {
    this("javacreed");
  }

  /**
   *
   * @param decoder
   * @throws NullPointerException
   */
  public DefaultPropertiesDecoder(final PropertyDecoder decoder) throws NullPointerException {
    setDecoder(decoder);
  }

  /**
   *
   * @param key
   */
  public DefaultPropertiesDecoder(final String key) {
    this(new DefaultPropertyDecoder(key));
  }

  @Override
  public DecodedProperties decode(final Iterable<PropertyEntry> propertiesEntries) throws EncoderException {
    return decode(propertiesEntries.iterator());
  }

  @Override
  public DecodedProperties decode(final Iterator<PropertyEntry> propertiesEntries) throws EncoderException {
    final DefaultDecodedProperties decoded = new DefaultDecodedProperties();
    try {
      while (propertiesEntries.hasNext()) {
        final PropertyEntry entry = propertiesEntries.next();
        final PropertyEntry decodedEntry = decoder.decode(entry);
        decoded.entries.add(decodedEntry);
        if (entry != decodedEntry) {
          decoded.decoded++;
        }
      }
    } catch (final RuntimeException e) {
      throw EncoderException.launder(e);
    }

    return decoded;
  }

  /**
   *
   * @param decoder
   * @throws NullPointerException
   */
  public void setDecoder(final PropertyDecoder decoder) throws NullPointerException {
    this.decoder = Objects.requireNonNull(decoder);
  }

  /**
   *
   * @param decoder
   * @return
   * @throws NullPointerException
   */
  public DefaultPropertiesDecoder use(final PropertyDecoder decoder) throws NullPointerException {
    setDecoder(decoder);
    return this;
  }
}
