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

import net.jcip.annotations.Immutable;

/**
 * The top level encoder exception
 *
 * @author Albert Attard
 */
@Immutable
public class EncoderException extends RuntimeException {

  /** The serial version id */
  private static final long serialVersionUID = 5457537806374311275L;

  /**
   * A generic method which returns the given throwable if this is an instance of this class, otherwise it returns this
   * with the given message and cause as its parameters.
   *
   * @param message
   *          the exception message
   * @param e
   *          the cause of the problem
   * @return the given throwable if this is an instance of this class, otherwise it returns this with the given message
   *         and cause as its parameters
   */
  public static EncoderException launder(final String message, final Throwable e) {
    if (e instanceof EncoderException) {
      return (EncoderException) e;
    }

    return new EncoderException(message, e);
  }

  /**
   * A generic method which returns the given throwable if this is an instance of this class, otherwise it returns this
   * with the given cause as its parameters.
   *
   * This is equivalent to {@code EncoderException.launder(null, e)}
   *
   * @param e
   *          the cause of the problem
   * @return the given throwable if this is an instance of this class, otherwise it returns this with the given cause as
   *         its parameters
   *
   * @see #launder(String, Throwable)
   */
  public static EncoderException launder(final Throwable e) {
    return EncoderException.launder(null, e);
  }

  /**
   * Creates an instance of this class
   *
   * @param message
   *          the exception message (which can be {@code null})
   */
  public EncoderException(final String message) {
    super(message);
  }

  /**
   * Creates an instance of this class
   *
   * @param message
   *          the exception message (which can be {@code null})
   * @param cause
   *          the cause (which can be {@code null})
   */
  public EncoderException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates an instance of this class
   *
   * @param cause
   *          the cause (which can be {@code null})
   */
  public EncoderException(final Throwable cause) {
    super(cause);
  }
}
