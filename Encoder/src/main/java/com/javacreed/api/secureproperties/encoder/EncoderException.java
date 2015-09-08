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
 *
 */
public class EncoderException extends RuntimeException {

  /** */
  private static final long serialVersionUID = 5457537806374311275L;

  /**
   *
   * @param message
   * @param e
   * @return
   */
  public static EncoderException launder(final String message, final Throwable e) {
    if (e instanceof EncoderException) {
      return (EncoderException) e;
    }

    return new EncoderException(message, e);
  }

  /**
   *
   * @param e
   * @return
   */
  public static EncoderException launder(final Throwable e) {
    return EncoderException.launder(null, e);
  }

  /**
   *
   * @param message
   */
  public EncoderException(final String message) {
    super(message);
  }

  /**
   *
   * @param message
   * @param cause
   */
  public EncoderException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   *
   * @param cause
   */
  public EncoderException(final Throwable cause) {
    super(cause);
  }
}
