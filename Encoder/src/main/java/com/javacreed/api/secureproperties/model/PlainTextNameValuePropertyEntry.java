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
 * Represents a property entry which needs to be encoded.
 *
 * The {@link #toString()} method only shows the property name
 *
 * @author Albert Attard
 */
@Immutable
public class PlainTextNameValuePropertyEntry extends NameValuePropertyEntry {

  /**
   * Creates an instance this class
   *
   * @param name
   *          the property name (which cannot be {@code null})
   * @param value
   *          the property value (which cannot be {@code null})
   * @throws NullPointerException
   *           if any of the parameters are {@code null}
   */
  public PlainTextNameValuePropertyEntry(final String name, final String value) throws NullPointerException {
    super(name, value);
  }

  @Override
  public String toString() {
    return String.format("%s=??????", name);
  }
}
