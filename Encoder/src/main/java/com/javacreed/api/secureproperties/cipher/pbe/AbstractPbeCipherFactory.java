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
package com.javacreed.api.secureproperties.cipher.pbe;

import java.util.Objects;

import net.jcip.annotations.NotThreadSafe;

import com.javacreed.api.secureproperties.cipher.AbstractCipherFactory;

/**
 * Represents the password based encryption cipher factory
 */
@NotThreadSafe
public abstract class AbstractPbeCipherFactory extends AbstractCipherFactory {

  /**
   * The key (password) used for encoding and decoding
   */
  protected String key;

  /**
   * Creates an instance of this class without setting the key which is required by the encoding and decoding process.
   */
  public AbstractPbeCipherFactory() {}

  /**
   *
   * @param key
   *          the algorithm's key
   * @throws NullPointerException
   *           if the given {@code key} is {@code null}
   */
  public AbstractPbeCipherFactory(final String key) throws NullPointerException {
    setKey(key);
  }

  /**
   * Returns the key if this is not {@code null} nor empty. An {@link IllegalStateException} is thrown if the key is
   * either {@code null} or empty ({@link String#isEmpty()}).
   *
   * @return the key (which will not be {@code null} nor empty)
   *
   * @throws IllegalStateException
   *           if the give {@code key} is {@code null} or empty
   */
  protected String getKeyFailIfEmpty() throws IllegalStateException {
    if (key == null || key.isEmpty()) {
      throw new IllegalStateException("The key is not set");
    }

    return key;
  }

  /**
   * Sets the algorithm key
   *
   * @param key
   *          the key (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given {@code key} is {@code null}
   */
  public void setKey(final String key) throws NullPointerException {
    this.key = Objects.requireNonNull(key);
  }

}
