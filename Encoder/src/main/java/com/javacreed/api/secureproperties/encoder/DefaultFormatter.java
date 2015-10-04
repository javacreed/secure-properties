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

import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;
import com.javacreed.api.secureproperties.utils.NumbersUtils;

/**
 * The default implementation of the {@link Formatter} interface. It takes an instance of
 * {@link PlainTextNameValuePropertyEntry} and returns a string using the following structure:
 * {@code padding+length-requirement,name-length,name,value-length,value}
 *
 * The padding is a set of random numbers which length is defined by the {@link #paddingLength}. The formatter takes a
 * minimum length in order to disguise short messages. This has no effect of long values. This ensures that the
 * formatted string is of at least some length. Each part of the formatted message is comma delimited and the property
 * name and its value's are prefixed with their length.
 *
 * @author Albert Attard
 */
@ThreadSafe
@Immutable
public class DefaultFormatter implements Formatter {

  /** The random number generator */
  private final Random random;

  /** The number of random numbers to prefix the message */
  private final int paddingLength;

  /**
   * The minimum length of the property value. If the property value's length is less than this value, then more random
   * numbers are added to the message prefix.
   */
  private final int minimumLength;

  /**
   * Creates an instance of this class with the default configuration.
   *
   * @see SecureRandom
   */
  public DefaultFormatter() {
    this(new SecureRandom(), 3, 16);
  }

  /**
   * Creates an instance of this class using an instance of {@link SecureRandom} as the random number generator.
   *
   * The padding is a set of random numbers which length is defined by the {@code paddingLength}. These random numbers
   * contribute to unique messages. The larger this value the higher the probability of unique messages it is.
   * Furthermore, larger padding length also increase the overall message length. The formatter takes a minimum length
   * in order to disguise short messages. This has no effect of long values. This ensures that the formatted string is
   * of at least some length.
   *
   * @param paddingLength
   *          the padding length (which should be between 1 and 16 both inclusive)
   * @param minimumLength
   *          the minimum length of the property value (which should be between 1 and 128 both inclusive)
   * @throws IllegalArgumentException
   *           if the padding is not between 1 and 16 both inclusive or if the minimum length is not between 1 and 128
   *           both inclusive
   * @see SecureRandom
   */
  public DefaultFormatter(final int paddingLength, final int minimumLength) throws IllegalArgumentException {
    this(new SecureRandom(), paddingLength, minimumLength);
  }

  /**
   * Creates an instance of this class.
   *
   * The padding is a set of random numbers which length is defined by the {@code paddingLength}. These random numbers
   * contribute to unique messages. The larger this value the higher the probability of unique messages it is.
   * Furthermore, larger padding length also increase the overall message length. The formatter takes a minimum length
   * in order to disguise short messages. This has no effect of long values. This ensures that the formatted string is
   * of at least some length.
   *
   * @param random
   *          the random number generator
   * @param paddingLength
   *          the padding length (which should be between 1 and 16 both inclusive)
   * @param minimumLength
   *          the minimum length of the property value (which should be between 1 and 128 both inclusive)
   * @throws NullPointerException
   *           if the random number generator is {@code null}
   * @throws IllegalArgumentException
   *           if the padding is not between 1 and 16 both inclusive or if the minimum length is not between 1 and 128
   *           both inclusive
   */
  public DefaultFormatter(final Random random, final int paddingLength, final int minimumLength)
      throws NullPointerException, IllegalArgumentException {
    this.random = Objects.requireNonNull(random);
    this.paddingLength = NumbersUtils.inRange(paddingLength, 1, 16, "padding length");
    this.minimumLength = NumbersUtils.inRange(minimumLength, 1, 128, "minimum length");
  }

  @Override
  public String format(final PlainTextNameValuePropertyEntry propertyEntry) throws NullPointerException {
    final String name = Objects.requireNonNull(propertyEntry.getName());
    final String value = Objects.requireNonNull(propertyEntry.getValue());

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

  @Override
  public PlainTextNameValuePropertyEntry parse(final String formatted) throws EncoderException, NullPointerException {
    final String[] parts = formatted.split(",", 3);

    if (parts[1] == null || parts[1].matches("\\d+") == false) {
      // TODO: throw a proper exception
      throw new EncoderException("");
    }

    final int nameLength = Integer.parseInt(parts[1]);
    final String name = parts[2].substring(0, nameLength);

    final int valueIndex = parts[2].indexOf(",", nameLength + 1);
    final String value = parts[2].substring(valueIndex + 1);

    return new PlainTextNameValuePropertyEntry(name, value);
  }

}
