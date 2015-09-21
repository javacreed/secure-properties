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

import java.util.Objects;

import net.jcip.annotations.Immutable;

import com.javacreed.api.secureproperties.cipher.CipherFactory;
import com.javacreed.api.secureproperties.cipher.pbe.AesCipherFactory;
import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;

/**
 * The default implementation of the {@link PropertyEncoder}. This version of the encoder makes use of a
 * {@link Formatter} and a {@link StringEncoder} to encodes the property. The formatter is used to format the property
 * into a single string while the encoder encodes the formatted value into an encoded, hex-decimal string.
 *
 * @author Albert Attard
 */
@Immutable
public class DefaultPropertyEncoder implements PropertyEncoder {

  /** Formatter used to format the property before encoding it */
  private final Formatter formatter;

  /** The encoder that will be used to encode the property */
  private final StringEncoder stringEncoder;

  /**
   * Creates an instance of this class using the default configuration
   *
   * @see DefaultFormatter
   * @see CipherStringEncoder
   */
  public DefaultPropertyEncoder() {
    this("javacreed");
  }

  /**
   * Creates an instance of this class with the given cipher factory using the default formatted.
   *
   * @param factory
   *          the cipher factory to be used (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given {@code factory} is {@code null}
   * @see DefaultFormatter
   */
  public DefaultPropertyEncoder(final CipherFactory factory) throws NullPointerException {
    this(new CipherStringEncoder(factory));
  }

  /**
   * Creates an instance of this class with the given formatter and the default cipher.
   *
   * @param formatter
   *          the formatter to be used (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given {@code formatter} is {@code null}
   * @see CipherStringEncoder
   */
  public DefaultPropertyEncoder(final Formatter formatter) throws NullPointerException {
    this(formatter, new CipherStringEncoder());
  }

  /**
   * Creates an instance of this class
   *
   * @param formatter
   *          the formatter to be used (which cannot be {@code null})
   * @param stringEncoder
   *          the cipher factory to be used (which cannot be {@code null})
   * @throws NullPointerException
   */
  public DefaultPropertyEncoder(final Formatter formatter, final StringEncoder stringEncoder)
      throws NullPointerException {
    this.formatter = Objects.requireNonNull(formatter);
    this.stringEncoder = Objects.requireNonNull(stringEncoder);
  }

  /**
   * Creates an instance of this class using the default formatter and the default cipher with the given key.
   *
   * @param key
   *          the key used by the encoder (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given {@code key} is {@code null}
   */
  public DefaultPropertyEncoder(final String key) throws NullPointerException {
    this(new AesCipherFactory(key));
  }

  /**
   * Creates an instance of this class with the given encoder using the default formatted.
   *
   * @param stringEncoder
   *          the string encoder to be used (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given {@code stringEncoder} is {@code null}
   */
  public DefaultPropertyEncoder(final StringEncoder stringEncoder) throws NullPointerException {
    this(new DefaultFormatter(), stringEncoder);
  }

  @Override
  public EncodedNameValuePropertyEntry encode(final PlainTextNameValuePropertyEntry entry) throws NullPointerException,
      EncoderException {
    final String formatted = formatter.format(entry);
    final String encoded = stringEncoder.encode(formatted);
    return new EncodedNameValuePropertyEntry(entry.getName(), encoded);
  }
}
