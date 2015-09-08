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
package com.javacreed.api.secureproperties.decoder;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.model.PropertyEntry;

public class DefaultPropertiesDecoder implements PropertiesDecoder {

  private PropertyDecoder decoder = new DefaultPropertyDecoder("javacreed");

  @Override
  public void decode(final Iterable<PropertyEntry> propertiesEntries) throws EncoderException {
    decode(propertiesEntries.iterator());
  }

  @Override
  public List<PropertyEntry> decode(final Iterator<PropertyEntry> propertiesEntries) throws EncoderException {
    if (decoder == null) {
      throw new IllegalStateException("Decoder is not set");
    }

    final List<PropertyEntry> decodedProperties = new LinkedList<>();

    try {
      while (propertiesEntries.hasNext()) {
        final PropertyEntry entry = propertiesEntries.next();
        final PropertyEntry encodedEntry = decoder.decode(entry);
        decodedProperties.add(encodedEntry);
      }
    } catch (final RuntimeException e) {
      throw EncoderException.launder(e);
    }

    return decodedProperties;
  }

  public void setDecoder(final PropertyDecoder decoder) throws NullPointerException {
    this.decoder = Objects.requireNonNull(decoder);
  }

  public DefaultPropertiesDecoder use(final PropertyDecoder decoder) throws NullPointerException {
    setDecoder(decoder);
    return this;
  }
}
