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

import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.api.secureproperties.cipher.CipherFactory;
import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;

/**
 * The default implementation of the {@link PropertyEncoder}
 *
 * @author Albert Attard
 */
@Immutable
public class DefaultPropertiesEncoder implements PropertiesEncoder {

  /**
   *
   * @author Albert Attard
   */
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

  /**
   * The internal implementation of {@link EncodedProperties}. This class is marked as non-thread safe as its fields are
   * not protected/guarded by a lock. On the other hand this class is only instantiated and modified from within the
   * {@link DefaultPropertiesEncoder#encode(Iterator)} method. Therefore, once available, this object can be safely
   * shared by more than one thread.
   *
   * @author Albert Attard
   */
  @NotThreadSafe
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

  /** The encoder that will be used */
  private final PropertyEncoder encoder;

  /**
   * Creates an instance of this class using the default configuration
   *
   * @see DefaultPropertyEncoder
   */
  public DefaultPropertiesEncoder() {
    this("javacreed");
  }

  /**
   * Creates an instance of this class using the given cipher factory
   *
   * @param cipherFactory
   *          the cipher factory to be used (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given {@code cipherFactory} is {@code null}
   *
   * @see DefaultPropertyEncoder
   */
  public DefaultPropertiesEncoder(final CipherFactory cipherFactory) throws NullPointerException {
    this(new DefaultPropertyEncoder(cipherFactory));
  }

  /**
   * Creates an instance of this class using the given property encoder
   *
   * @param encoder
   *          the property encoder to be used (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given {@code encoder} is {@code null}
   */
  public DefaultPropertiesEncoder(final PropertyEncoder encoder) throws NullPointerException {
    this.encoder = Objects.requireNonNull(encoder);
  }

  /**
   * Creates an instance of this class using the default property encoder with the given key.
   *
   * @param key
   *          the key to be used with the default property encoder
   * @throws NullPointerException
   *           if the given {@code key} is {@code null}
   *
   * @see DefaultPropertyEncoder
   */
  public DefaultPropertiesEncoder(final String key) throws NullPointerException {
    this(new DefaultPropertyEncoder(key));
  }

  @Override
  public PlainTextNameValuePropertyEntry decode(final EncodedNameValuePropertyEntry entry) throws EncoderException,
  NullPointerException {
    return encoder.decode(entry);
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
        if (entry instanceof EncodedNameValuePropertyEntry) {
          decoded.entries.add(encoder.decode((EncodedNameValuePropertyEntry) entry));
          decoded.decoded++;
        } else {
          decoded.entries.add(entry);
        }
      }
    } catch (final RuntimeException e) {
      throw EncoderException.launder(e);
    }

    return decoded;
  }

  @Override
  public EncodedProperties encode(final Iterable<PropertyEntry> propertiesEntries) throws NullPointerException,
      EncoderException {
    return encode(propertiesEntries.iterator());
  }

  @Override
  public EncodedProperties encode(final Iterator<PropertyEntry> propertiesEntries) throws NullPointerException,
      EncoderException, IllegalStateException {
    final DefaultEncodedProperties encodedProperties = new DefaultEncodedProperties();

    while (propertiesEntries.hasNext()) {
      final PropertyEntry entry = propertiesEntries.next();
      try {
        /*
         * Encode all properties of type PlainTextNameValuePropertyEntry. The other properties do not need to be
         * encoded. Just add them to the list
         */
        if (entry instanceof PlainTextNameValuePropertyEntry) {
          final EncodedNameValuePropertyEntry encodedEntry = encoder.encode((PlainTextNameValuePropertyEntry) entry);
          encodedProperties.entries.add(encodedEntry);
          DefaultPropertiesEncoder.LOGGER.debug("The property {} was modified (encoded) thus we need to write them",
              encodedEntry);
          encodedProperties.encoded++;
        } else {
          encodedProperties.entries.add(entry);
        }
      } catch (final RuntimeException e) {
        DefaultPropertiesEncoder.LOGGER.error("Failed to encode property {}", entry, e);
        throw EncoderException.launder(e);
      }
    }

    return encodedProperties;
  }

  @Override
  public EncodedNameValuePropertyEntry encode(final PlainTextNameValuePropertyEntry entry) throws EncoderException,
  NullPointerException {
    return encoder.encode(entry);
  }

}
