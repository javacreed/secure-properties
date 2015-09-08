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

import java.util.Formatter;

/**
 *
 */
public class HexUtils {

  /**
   *
   * @param hexString
   * @return
   * @throws NullPointerException
   */
  public static byte[] toByteArray(final String hexString) throws NullPointerException {
    final int length = hexString.length();
    final byte[] bytes = new byte[length / 2];
    for (int i = 0; i < length; i += 2) {
      bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1),
          16));
    }
    return bytes;
  }

  /**
   *
   * @param data
   * @return
   * @throws NullPointerException
   */
  public static String toHexString(final byte[] data) throws NullPointerException {
    try (Formatter formatter = new Formatter()) {
      for (final byte b : data) {
        formatter.format("%02x", b);
      }
      return formatter.toString();
    }
  }

  /**
   *
   */
  private HexUtils() {}

}
