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
package com.javacreed.api.secureproperties.cipher;

import java.io.InputStream;
import java.io.OutputStream;

import com.javacreed.api.secureproperties.encoder.EncoderException;

/**
 * Used to wrap a stream into a ciphered stream
 *
 * @author Albert Attard
 */
public interface CipherFactory {

  /**
   * Creates a ciphered stream wrapping the given {@code in} (input stream)
   *
   * @param in
   *          the input stream to be wrapped into a ciphered stream (which cannot be {@code null})
   * @return a ciphered stream wrapping the given {@code in} (input stream)
   * @throws NullPointerException
   *           if the given {@code in} (input stream) is {@code null}
   * @throws EncoderException
   *           if an error occurs while creating the ciphered stream
   *
   */
  InputStream createDecoder(InputStream in) throws EncoderException;

  /**
   * Creates a ciphered stream wrapping the given {@code out} (output stream)
   *
   * @param out
   *          the output stream to be wrapped into a ciphered stream (which cannot be {@code null})
   * @return a ciphered stream wrapping the given {@code out} (output stream)
   * @throws NullPointerException
   *           if the given {@code out} (output stream) is {@code null}
   * @throws EncoderException
   *           if an error occurs while creating the ciphered stream
   */
  OutputStream createEncoder(OutputStream out) throws EncoderException;
}
