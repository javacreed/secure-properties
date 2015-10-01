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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.javacreed.api.secureproperties.cipher.CipherFactory;
import com.javacreed.api.secureproperties.encoder.EncoderException;

/**
 * Provides common/utilities methods related to ciphers encoding and decoding.
 *
 * @author Albert Attard
 */
public class CipherUtils {

  /**
   * Decodes the given Hex text ({@code encodedText}) using the given {@code cipherFactory}.
   *
   * @param encodedText
   *          the encoded Hex text (which cannot be {@code null})
   * @param cipherFactory
   *          the cipher factory which will be used to create the required input stream (which cannot be {@code null})
   * @return the decoded/plain text (which will never be {@code null})
   * @throws EncoderException
   *           if an error occurs during the decoding
   */
  public static String decode(final String encodedText, final CipherFactory cipherFactory) throws EncoderException {
    try (ByteArrayInputStream in = new ByteArrayInputStream(HexUtils.toByteArray(encodedText));
        InputStream encoded = cipherFactory.createDecoder(in);) {
      final byte[] buffer = new byte[1024];
      final StringBuilder stringBuffer = new StringBuilder();
      for (int length; (length = encoded.read(buffer)) != -1;) {
        stringBuffer.append(new String(buffer, 0, length, Charset.forName("UTF-8")));
      }

      return stringBuffer.toString();
    } catch (final Exception e) {
      throw EncoderException.launder(e);
    }
  }

  /**
   * Encodes the given ({@code plainText}) using the given {@code cipherFactory}.
   *
   * @param plainText
   *          the plain text (which will never be {@code null})
   * @param cipherFactory
   *          the cipher factory which will be used to create the required input stream (which cannot be {@code null})
   * @return the encoded hex text (which will never be {@code null})
   * @throws EncoderException
   *           if an error occurs during the encoding
   */
  public static String encode(final String plainText, final CipherFactory cipherFactory) throws EncoderException {
    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputStream encoded = cipherFactory.createEncoder(out);) {
      encoded.write(plainText.getBytes("UTF-8"));
      encoded.close();
      return HexUtils.toHexString(out.toByteArray());
    } catch (final Exception e) {
      throw EncoderException.launder(e);
    }
  }

  /**
   * This is a utilities class and does not need to be initialised
   */
  private CipherUtils() {}
}
