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
 *
 */
@NotThreadSafe
public abstract class AbstractPbeCipherFactory extends AbstractCipherFactory {

  /**
   *
   */
  protected String key;

  /**
   *
   */
  public AbstractPbeCipherFactory() {}

  /**
   *
   * @param key
   */
  public AbstractPbeCipherFactory(final String key) {
    setKey(key);
  }

  /**
   *
   * @param key
   * @throws NullPointerException
   */
  public void setKey(final String key) throws NullPointerException {
    this.key = Objects.requireNonNull(key);
  }

}
