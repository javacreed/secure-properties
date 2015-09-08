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
package com.javacreed.api.secureproperties.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.model.PropertyEntry;

public abstract class AbstractPropertiesParser implements PropertiesParser {

  private boolean endOfProperties;
  private PropertyEntry lastPropertyEntry;

  private PropertyEntry consumeNext() {
    final PropertyEntry next = peekNext();
    lastPropertyEntry = null;
    return next;
  }

  @Override
  public List<PropertyEntry> getProperties() {
    final List<PropertyEntry> properties = new LinkedList<>();
    while (hasNext()) {
      properties.add(next());
    }
    return properties;
  }

  private boolean hasNext() {
    return peekNext() != null;
  }

  private PropertyEntry next() {
    final PropertyEntry next = consumeNext();
    if (next == null) {
      throw new NoSuchElementException("No more property entries found");
    }

    return next;
  }

  private PropertyEntry peekNext() {
    if (endOfProperties) {
      return null;
    }

    if (lastPropertyEntry == null) {
      try {
        lastPropertyEntry = readNext();
      } catch (final Exception e) {
        throw EncoderException.launder(e);
      }
    }

    if (lastPropertyEntry == null) {
      endOfProperties = true;
    }

    return lastPropertyEntry;
  }

  protected abstract PropertyEntry readNext() throws Exception;
}
