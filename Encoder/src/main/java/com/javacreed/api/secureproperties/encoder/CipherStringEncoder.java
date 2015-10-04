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

import com.javacreed.api.secureproperties.cipher.CipherFactory;
import com.javacreed.api.secureproperties.cipher.pbe.AesCipherFactory;
import com.javacreed.api.secureproperties.utils.CipherUtils;

/**
 * Cipher based string encoder and decoder and default implementation of the {@link StringEncoder} interface.
 *
 * The encoding process is delegated to the {@link CipherUtils#decode(String, CipherFactory)} and
 * {@link CipherUtils#encode(String, CipherFactory)} static methods
 *
 * @author Albert Attard
 *
 * @see CipherUtils
 */
public class CipherStringEncoder implements StringEncoder {

  /**
   * The cipher factory which is responsible from creating new ciphers
   */
  protected final CipherFactory cipherFactory;

  /**
   * Creates an instance of the this class using the default configuration.
   *
   * @see AesCipherFactory
   */
  public CipherStringEncoder() {
    this(new AesCipherFactory());
  }

  /**
   * Creates an instance of this class using the given cipher factory
   *
   * @param cipherFactory
   *          the cipher factory to be used (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given cipher factory is {@code null}
   */
  public CipherStringEncoder(final CipherFactory cipherFactory) throws NullPointerException {
    this.cipherFactory = Objects.requireNonNull(cipherFactory);
  }

  /**
   * Creates an instance of this class using the {@link AesCipherFactory} as its cipher factory with the given
   * {@code key}.
   *
   * @param key
   *          the password (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given {@code key} is {@code null}
   *
   * @see AesCipherFactory
   */
  public CipherStringEncoder(final String key) throws NullPointerException {
    this(new AesCipherFactory(key));
  }

  @Override
  public String decode(final String plainText) throws EncoderException {
    return CipherUtils.decode(plainText, cipherFactory);
  }

  @Override
  public String encode(final String plainText) throws EncoderException {
    return CipherUtils.encode(plainText, cipherFactory);
  }

}
