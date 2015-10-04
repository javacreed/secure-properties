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

import com.javacreed.api.secureproperties.model.PropertyEntry;

/**
 */
public interface DecodedProperties {

  /**
   * The list of all properties in the same order these were observed
   *
   * @return list of all properties in the same order these were observed
   */
  Iterable<PropertyEntry> getEntries();

  /**
   * Returns the number of properties that were decoded
   *
   * @return the number of properties that were decoded
   */
  int getNumberOfDecodedProperties();

  /**
   * Returns {@code true} if at least one property was decoded, {@code false} otherwise
   *
   * @return {@code true} if at least one property was decoded, {@code false} otherwise
   */
  boolean wereDecoded();
}
