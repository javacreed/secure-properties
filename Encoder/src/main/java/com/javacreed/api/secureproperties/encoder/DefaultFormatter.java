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

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import com.javacreed.api.secureproperties.utils.NumbersUtils;

/**
 * @author Albert Attard
 */
@ThreadSafe
@Immutable
public class DefaultFormatter implements Formatter {

  /** */
  private final Random random;

  /** */
  private final int paddingLength;

  /** */
  private final int minimumLength;

  /**
   *
   */
  public DefaultFormatter() {
    this(new SecureRandom(), 3, 16);
  }

  /**
   *
   * @param paddingLength
   * @param minimumLength
   * @throws IllegalArgumentException
   */
  public DefaultFormatter(final int paddingLength, final int minimumLength) throws IllegalArgumentException {
    this(new SecureRandom(), paddingLength, minimumLength);
  }

  /**
   *
   * @param random
   * @param paddingLength
   * @param minimumLength
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  public DefaultFormatter(final Random random, final int paddingLength, final int minimumLength)
      throws NullPointerException, IllegalArgumentException {
    this.random = Objects.requireNonNull(random);
    this.paddingLength = NumbersUtils.inRange(paddingLength, 1, 16, "padding length");
    this.minimumLength = NumbersUtils.inRange(minimumLength, 1, 128, "minimum length");
  }

  @Override
  public String format(final String name, final String value) throws NullPointerException {

    final StringBuilder formatted = new StringBuilder();

    // Add padding random numbers to always produce a different string
    for (int i = 0; i < paddingLength; i++) {
      formatted.append(random.nextInt(10));
    }

    // Obfuscate the value's length
    for (int i = minimumLength, size = value.length(); i < size; i++) {
      formatted.append(random.nextInt(10));
    }
    formatted.append(",");

    // Name
    formatted.append(name.length());
    formatted.append(",");
    formatted.append(name);
    formatted.append(",");

    // Value
    formatted.append(value.length());
    formatted.append(",");
    formatted.append(value);

    return formatted.toString();
  }
}
