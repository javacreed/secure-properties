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
package com.javacreed.api.secureproperties.decoder;

import com.javacreed.api.secureproperties.encoder.EncoderException;

/**
 * Decodes a hex-string into a plain text string
 */
public interface StringDecoder {

  /**
   * Decodes the given {@code encodedText} (hex-string) into a plain text string using a given configuration
   *
   * @param encodedText
   *          the hex-string representing the encoded text (which cannot be {@code null})
   * @return the decoded, plain-text, string (which will not be {@code null})
   * @throws EncoderException
   *           if an error occurs during the encoding
   * @throws NullPointerException
   *           if the given {@code encodedText} is {@code null}
   */
  String decode(String encodedText) throws EncoderException, NullPointerException;
}
