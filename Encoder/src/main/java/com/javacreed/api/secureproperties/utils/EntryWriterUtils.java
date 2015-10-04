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

import java.util.Objects;

import com.javacreed.api.secureproperties.encoder.EncodedProperties;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.writer.PropertyEntryWriter;

/**
 *
 * @author Albert Attard
 */
public class EntryWriterUtils {

  /**
   *
   * @param entryWriter
   * @param properties
   * @throws NullPointerException
   */
  public static void write(final PropertyEntryWriter entryWriter, final EncodedProperties properties)
      throws NullPointerException {
    Objects.requireNonNull(entryWriter);
    Objects.requireNonNull(properties);
    try {
      entryWriter.begin();
      for (final PropertyEntry entry : properties.getEntries()) {
        entryWriter.write(entry);
      }
      entryWriter.commit();
    } catch (final Exception e) {
      entryWriter.failed(e);
    }
  }

  /** */
  private EntryWriterUtils() {}

}
