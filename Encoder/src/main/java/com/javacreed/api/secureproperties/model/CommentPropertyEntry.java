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

/**
 * Represents a comment line within the properties file. This has not value from an application point of view, but was
 * added to preserve the file structure.
 *
 * @author Albert Attard
 */
public class CommentPropertyEntry extends BasicPropertyEntry {

  /**
   * Creates an instance of this class with given {@code line}. This constructor does not check whether the given
   * {@code line} is actually a comment or not. The given {@code line} cannot be {@code null}.
   *
   * @param line
   *          the comment line (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given {@code link} is {@code null}
   */
  public CommentPropertyEntry(final String line) throws NullPointerException {
    super(line);
  }

  @Override
  public String toString() {
    return "Comment: " + line;
  }
}
