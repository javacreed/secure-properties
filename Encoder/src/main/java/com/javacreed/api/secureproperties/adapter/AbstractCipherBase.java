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
package com.javacreed.api.secureproperties.adapter;

import java.util.Objects;

import net.jcip.annotations.Immutable;

import com.javacreed.api.secureproperties.cipher.CipherFactory;
import com.javacreed.api.secureproperties.cipher.pbe.AesCipherFactory;

/**
 * A password based cipher string encoder/decoder.
 *
 * @author Albert Attard
 */
@Immutable
public class AbstractCipherBase {

  /**
   * The cipher factory which is responsible from creating new ciphers
   */
  protected final CipherFactory cipherFactory;

  /**
   * Creates an instance of this class using the {@link AesCipherFactory} default cipher factory with the default
   * configuration.
   *
   * @see AesCipherFactory
   */
  public AbstractCipherBase() {
    this(new AesCipherFactory());
  }

  /**
   * Creates an instance of this class using the given cipher factory.
   *
   * @param cipherFactory
   *          the cipher factory to be used (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given cipher is {@code null}
   */
  public AbstractCipherBase(final CipherFactory cipherFactory) throws NullPointerException {
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
  public AbstractCipherBase(final String key) throws NullPointerException {
    this(new AesCipherFactory(key));
  }
}
