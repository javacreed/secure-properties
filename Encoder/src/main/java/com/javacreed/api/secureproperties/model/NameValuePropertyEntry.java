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

import net.jcip.annotations.Immutable;

/**
 * Represents a basic name value property
 *
 * @author Albert Attard
 */
@Immutable
public class NameValuePropertyEntry extends NamedPropertyEntry {

  /** The property's value (which cannot be null) */
  protected final String value;

  /**
   * Creates an instance of this class
   *
   * @param name
   *          the property name (which cannot be {@code null})
   * @param value
   *          the property name (which cannot be {@code null})
   * @throws NullPointerException
   *           if any of the parameters are {@code null}
   */
  public NameValuePropertyEntry(final String name, final String value) throws NullPointerException {
    super(name);
    this.value = Objects.requireNonNull(value);
  }

  /**
   * Returns the property value
   *
   * @return the property value (which will not )
   */
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.format("%s=%s", name, value);
  }
}
