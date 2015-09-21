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
package com.javacreed.api.secureproperties.model;

import java.util.Objects;

/**
 * Represents a single line property entry such as comments
 *
 * @author Albert Attard
 */
public class BasicPropertyEntry extends AbstractPropertyEntry {

  /** The single line property entry */
  protected final String line;

  /**
   * Creates an instance of this class
   *
   * @param line
   *          the line (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given {@code line} is {@code null}
   */
  public BasicPropertyEntry(final String line) throws NullPointerException {
    this.line = Objects.requireNonNull(line);
  }

  /**
   * Returns the property line
   *
   * @return the line
   */
  public String getLine() {
    return line;
  }

  @Override
  public String toString() {
    return line;
  }
}
