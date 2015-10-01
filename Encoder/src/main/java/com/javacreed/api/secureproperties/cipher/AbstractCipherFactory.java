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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import com.javacreed.api.secureproperties.encoder.EncoderException;

/**
 * Streams usually throw {@link IOException} while the encoder works with the {@link EncoderException}
 *
 * @author Albert Attard
 */
public abstract class AbstractCipherFactory implements CipherFactory {

  @Override
  public InputStream createDecoder(final InputStream in) throws EncoderException {
    try {
      final InputStream wrapped = wrapInToCipheredInputStream(Objects.requireNonNull(in));
      if (wrapped == null) {
        throw new EncoderException("The wrapped input stream cannot be null");
      }
      return wrapped;
    } catch (final Exception e) {
      throw EncoderException.launder(e);
    }
  }

  @Override
  public OutputStream createEncoder(final OutputStream out) throws EncoderException {
    try {
      final OutputStream wrapped = wrapInToCipheredOutputStream(Objects.requireNonNull(out));
      if (wrapped == null) {
        throw new EncoderException("The wrapped output stream cannot be null");
      }
      return wrapped;
    } catch (final Exception e) {
      throw EncoderException.launder(e);
    }
  }

  /**
   * Returns a wrapper which decodes the given input stream ({@code in}) into an other stream
   *
   * @param in
   *          the input stream to be wrapped (which will never be {@code null})
   * @return returns the wrapped stream (which cannot be {@code null})
   * @throws Exception
   *           if an error occurs while creating the decoding input stream
   */
  protected abstract InputStream wrapInToCipheredInputStream(InputStream in) throws Exception;

  /**
   * Returns a wrapper which encodes the given input stream ({@code in}) into an other stream
   *
   * @param out
   *          the output stream to be wrapped (which will never be {@code null})
   * @return returns the wrapped stream (which cannot be {@code null})
   * @throws Exception
   *           if an error occurs while creating the encoding output stream
   */
  protected abstract OutputStream wrapInToCipheredOutputStream(OutputStream out) throws Exception;
}
