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
import java.util.Objects;

import com.javacreed.api.secureproperties.encoder.EncoderException;

/**
 *
 */
public abstract class AbstractCipherFactory implements CipherFactory {

  @Override
  public InputStream createDecoder(final InputStream in) throws EncoderException {
    try {
      return wrapInToCipheredInputStream(Objects.requireNonNull(in));
    } catch (final Exception e) {
      throw EncoderException.launder(e);
    }
  }

  @Override
  public OutputStream createEncoder(final OutputStream out) throws EncoderException {
    try {
      return wrapInToCipheredOutputStream(Objects.requireNonNull(out));
    } catch (final Exception e) {
      throw EncoderException.launder(e);
    }
  }

  /**
   *
   * @param in
   * @return
   * @throws Exception
   */
  protected abstract InputStream wrapInToCipheredInputStream(InputStream in) throws Exception;

  /**
   *
   * @param out
   * @return
   * @throws Exception
   */
  protected abstract OutputStream wrapInToCipheredOutputStream(OutputStream out) throws Exception;
}
