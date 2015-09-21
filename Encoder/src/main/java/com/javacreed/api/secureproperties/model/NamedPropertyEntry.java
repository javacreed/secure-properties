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
 * A property entry which contain only a name
 *
 * @author Albert Attard
 */
@Immutable
public class NamedPropertyEntry extends AbstractPropertyEntry {

  /** The property name */
  protected final String name;

  /**
   * Creates an instance of this class
   *
   * @param name
   *          the property name (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given property name is {@code null}
   */
  public NamedPropertyEntry(final String name) throws NullPointerException {
    this.name = Objects.requireNonNull(name);
  }

  /**
   * Returns the property name
   *
   * @return the property name (which will never be {@code null})
   */
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }

}
