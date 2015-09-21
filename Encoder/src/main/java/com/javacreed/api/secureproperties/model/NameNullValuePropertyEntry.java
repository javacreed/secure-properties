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

import net.jcip.annotations.Immutable;

/**
 * Represents a property which value is {@code null} (it has no value).
 *
 * @author Albert Attard
 */
@Immutable
public class NameNullValuePropertyEntry extends NamedPropertyEntry {

  /**
   * Creates an instance of this class
   *
   * @param name
   *          the property name (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given {@code name} is {@code null}
   */
  public NameNullValuePropertyEntry(final String name) throws NullPointerException {
    super(name);
  }

  /**
   * Creates a new instance of {@link NameValuePropertyEntry} using this objects name ({@link #getName()}) and the given
   * {@code value}.
   *
   * @param value
   *          the property value (which cannot be {@code null})
   * @return a new instance of {@link NameValuePropertyEntry} using this objects name ({@link #getName()}) and the given
   *         {@code value}
   * @throws NullPointerException
   *           if the given {@code value} is {@code null}
   */
  public NameValuePropertyEntry setValue(final String value) throws NullPointerException {
    return new NameValuePropertyEntry(name, value);
  }

  @Override
  public String toString() {
    return String.format("%s=NULL", name);
  }

}
