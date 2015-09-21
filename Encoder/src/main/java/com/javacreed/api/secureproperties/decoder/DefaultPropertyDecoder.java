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

import java.util.Objects;

import net.jcip.annotations.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.api.secureproperties.cipher.CipherFactory;
import com.javacreed.api.secureproperties.cipher.pbe.AesCipherFactory;
import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;

/**
 * The default implementation of the property decoder
 *
 * @author Albert Attard
 */
@Immutable
public class DefaultPropertyDecoder implements PropertyDecoder {

  /** The class logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPropertyDecoder.class);

  /** The parser that will be used to parse the decoded text to produce {@link PlainTextNameValuePropertyEntry} */
  private final Parser parser;

  /** the string decoder that will be used to decode the encoded text */
  private final StringDecoder decoder;

  /**
   * Creates the default instance of this class using the default configuration
   *
   * @see DefaultParser
   * @see AesCipherFactory
   */
  public DefaultPropertyDecoder() {
    this("javacreed");
  }

  /**
   * Creates an instance of this class using the default parser and the given cipher factory
   *
   * @param factory
   *          the cipher factory (which cannot be {@code null})
   * @see DefaultParser
   */
  public DefaultPropertyDecoder(final CipherFactory factory) {
    this(new DefaultParser(), new CipherStringDecoder(factory));
  }

  /**
   * Creates an instance of this class
   *
   * @param parser
   *          the parser (which cannot be {@code null})
   * @param encoder
   *          the encoder (which cannot be {@code null})
   * @throws NullPointerException
   *           if any of the parameters are {@code null}
   */
  public DefaultPropertyDecoder(final Parser parser, final StringDecoder encoder) throws NullPointerException {
    this.parser = Objects.requireNonNull(parser);
    this.decoder = Objects.requireNonNull(encoder);
  }

  /**
   * Creates an instance of this class using the default parser and the default cipher factory
   *
   * @param key
   *          the algorithm's key
   * @throws NullPointerException
   *           if the given {@code key} is {@code null}
   * @see DefaultParser
   * @see AesCipherFactory
   */
  public DefaultPropertyDecoder(final String key) throws NullPointerException {
    this(new DefaultParser(), new CipherStringDecoder(new AesCipherFactory(key)));
  }

  @Override
  public PropertyEntry decode(final PropertyEntry entry) throws EncoderException, NullPointerException {
    if (entry instanceof EncodedNameValuePropertyEntry) {
      DefaultPropertyDecoder.LOGGER.debug("Decoding property: {}", entry);
      final EncodedNameValuePropertyEntry envpEntry = (EncodedNameValuePropertyEntry) entry;
      final String decoded = decoder.decode(envpEntry.getValue());
      final NameValuePropertyEntry nvpEntry = parser.parse(decoded);

      if (nvpEntry.getName().equals(envpEntry.getName()) == false) {
        throw new InvalidEncodedValueException();
      }

      return nvpEntry;
    }

    return entry;
  }
}
