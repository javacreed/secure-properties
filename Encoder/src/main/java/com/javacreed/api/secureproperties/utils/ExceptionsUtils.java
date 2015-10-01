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
 * Provides common/utilities methods related to exception processing.
 *
 * @author Albert Attard
 */
public class ExceptionsUtils {

  /**
   * The interrupt exception may be swallowed by some other class and lost. This method checks whether the interrupted
   * exception is part of the cause chain and if so, interrupts the current thread and returns true. Otherwise this
   * method simply returns false.
   *
   * @param e
   *          the exception to be checked
   * @return {@code true} if the exception was caused by {@link InterruptedException} and the current thread is
   *         interrupted, {@code false} otherwise
   */
  public static boolean interruptIfItWasCausedByInterruption(final Throwable e) {
    if (ExceptionsUtils.isCausedBy(e, InterruptedException.class)) {
      Thread.currentThread().interrupt();
      return true;
    }

    return false;

  }

  /**
   * Checks whether the given exception is caused by the given type. Note that this method does not check the suppressed
   * exception ({@link Throwable#getSuppressed()}) but only the caused by ({@link Throwable#getCause()}).
   *
   * @param e
   *          the exception to be checked
   * @param causedBy
   *          the type
   * @return {@code true} of the given exception is caused by the expetced class, {@code false}
   */
  public static boolean isCausedBy(final Throwable e, final Class<? extends Throwable> causedBy) {
    for (Throwable t = e; t != null; t = t.getCause()) {
      if (t.getClass().equals(causedBy)) {
        return true;
      }
    }

    return false;
  }

  /**
   *
   */
  private ExceptionsUtils() {}
}
