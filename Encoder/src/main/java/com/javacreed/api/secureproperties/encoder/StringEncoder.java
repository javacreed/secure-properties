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

/**
 * An encoder is able of taking plain text and transform it into encoded (encrypted) hex-text.
 */
public interface StringEncoder {

  /**
   * Encodes the plain text into cipher text using a given configuration.
   *
   * @param plainText
   *          the text to be encoded (which cannot be {@code null})
   * @return the encoded test (which will not be {@code null})
   * @throws EncoderException
   *           if an error occurs while encoding the given text. The error may be caused by bad configuration
   * @throws NullPointerException
   *           if the given {@code plainText} is {@code null}
   */
  String encode(String plainText) throws EncoderException, NullPointerException;

}
