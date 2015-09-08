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
package com.javacreed.api.secureproperties.writer;

import java.util.LinkedList;
import java.util.List;

import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.writer.io.LinePropertyEntryWriter;

/**
 */
public abstract class AbstractPropertyEntryWriter implements PropertyEntryWriter {

  public static abstract class AbstractHandler<T extends PropertyEntry> implements Handler<T> {

    private final Class<T> type;

    protected AbstractHandler(final Class<T> type) {
      this.type = type;
    }

    @Override
    public Class<T> getHandlerType() {
      return type;
    }
  }

  public static interface Handler<T extends PropertyEntry> {
    Class<T> getHandlerType();

    void handle(T t) throws Exception;
  }

  private final List<LinePropertyEntryWriter.Handler<? extends PropertyEntry>> handlers = new LinkedList<>();

  protected void doPostSingleWrite() throws Exception {}

  protected <T extends PropertyEntry> Handler<T> findHandler(final Class<T> type) {

    Handler<T> selected = null;

    for (final Handler<? extends PropertyEntry> handler : handlers) {
      if (handler.getHandlerType().isAssignableFrom(type)) {
        if (selected == null || selected.getHandlerType().isAssignableFrom(handler.getHandlerType())) {
          @SuppressWarnings("unchecked")
          final Handler<T> t = (Handler<T>) handler;
          selected = t;
        }
      }
    }

    if (selected == null) {
      throw new EncoderException("Unsupported type: " + type.getCanonicalName());
    }

    return selected;
  }

  protected <T extends PropertyEntry> void registerHandler(final Handler<T> handler) {
    handlers.add(handler);
  }

  @Override
  public void write(final PropertyEntry entry) {
    @SuppressWarnings("unchecked")
    final Handler<PropertyEntry> handler = (Handler<PropertyEntry>) findHandler(entry.getClass());
    try {
      handler.handle(entry);
      doPostSingleWrite();
    } catch (final Exception e) {
      throw EncoderException.launder(e);
    }
  }

}
