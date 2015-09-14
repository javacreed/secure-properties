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

import net.jcip.annotations.NotThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.api.secureproperties.cipher.CipherFactory;
import com.javacreed.api.secureproperties.cipher.pbe.AesCipherFactory;
import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;

/**
 */
@NotThreadSafe
public class DefaultPropertyDecoder implements PropertyDecoder {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPropertyDecoder.class);

  /** */
  private Parser parser = new DefaultParser();

  /** */
  private StringDecoder decoder;

  /**
   *
   */
  public DefaultPropertyDecoder() {
    this("javacreed");
  }

  /**
   *
   * @param factory
   */
  public DefaultPropertyDecoder(final CipherFactory factory) {
    this(new DefaultParser(), new CipherStringDecoder(factory));
  }

  /**
   *
   * @param parser
   * @param encoder
   */
  public DefaultPropertyDecoder(final Parser parser, final StringDecoder encoder) {
    this.parser = Objects.requireNonNull(parser);
    this.decoder = Objects.requireNonNull(encoder);
  }

  /**
   *
   * @param key
   */
  public DefaultPropertyDecoder(final String key) {
    decoder = new CipherStringDecoder(new AesCipherFactory(key));
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

  /**
   *
   * @param decoder
   * @throws NullPointerException
   */
  public void setDecoder(final StringDecoder decoder) throws NullPointerException {
    this.decoder = Objects.requireNonNull(decoder);
  }

  /**
   *
   * @param parser
   * @throws NullPointerException
   */
  public void setParser(final Parser parser) throws NullPointerException {
    this.parser = Objects.requireNonNull(parser);
  }
}
