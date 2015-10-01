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
package com.javacreed.api.secureproperties.utils;

/**
 * Provides common/utilities methods related to numbers.
 *
 * @author Albert Attard
 */
public class NumbersUtils {

  /**
   * Verifies the value and fails if the given value is outside the given range.
   *
   * @param value
   *          the value to be verified
   * @param min
   *          the minimum value (inclusive)
   * @param max
   *          the maximum value (inclusive)
   * @param fieldName
   *          the fields name (which is used a part of the exception message)
   * @return the given value
   * @throws IllegalArgumentException
   *           if the given value is lower than min or greater than max
   */
  public static int inRange(final int value, final int min, final int max, final String fieldName)
      throws IllegalArgumentException {
    if (value < min || value > max) {
      throw new IllegalArgumentException("The " + fieldName + " value of " + value + " is out of range: [" + min + ","
          + max + "].");
    }

    return value;
  }

  /**
   * Verifies that the given number is one of the given set of values
   *
   * @param value
   *          the value to be verified
   * @param fieldName
   *          the fields name (which is used a part of the exception message)
   * @param values
   *          the set of values
   * @return the given value
   * @throws IllegalArgumentException
   *           if the given value is not one of the given set of values
   */
  public static int oneOf(final int value, final String fieldName, final int... values) throws IllegalArgumentException {
    for (final int v : values) {
      if (v == value) {
        return value;
      }
    }

    throw new IllegalArgumentException("Invalid " + fieldName + " value of " + value + ".");
  }

  /**
   * This class should not be instantiated
   */
  private NumbersUtils() {}
}
