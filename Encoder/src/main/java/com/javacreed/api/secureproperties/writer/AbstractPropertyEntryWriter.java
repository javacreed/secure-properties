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
import java.util.Objects;

import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.writer.io.LinePropertyEntryWriter;

/**
 * Different property types may be treated differently.
 * <p>
 * The properties may be read from properties files and written to the database. This class provides the mechanism,
 * through the use of {@link Handler}, to treat each type as required. Each handler is bound with a type of
 * {@link PropertyEntry} and needs to implements the {@link Handler#handle(PropertyEntry)} method.The
 * {@link Handler#handle(PropertyEntry)} method is the method responsible from the processing of the
 * {@link PropertyEntry}.
 *
 * @author Albert Attard
 */
public abstract class AbstractPropertyEntryWriter implements PropertyEntryWriter {

  /**
   * A skeleton implementation of the {@link Handler} interface.
   *
   * @author Albert Attard
   *
   * @param <T>
   *          the property type
   */
  public static abstract class AbstractHandler<T extends PropertyEntry> implements Handler<T> {

    /** The type supported by this handler */
    private final Class<T> type;

    /**
     *
     * @param type
     *          The type supported by this handler
     * @throws NullPointerException
     *           if the given type is {@code null}
     */
    protected AbstractHandler(final Class<T> type) throws NullPointerException {
      this.type = Objects.requireNonNull(type);
    }

    @Override
    public Class<T> getHandlerType() {
      return type;
    }
  }

  /**
   * Handles the processing of a given property entry. Different types may be handled differently. This interface
   * provides the ability to treat each supported type as required.
   *
   * @author Albert Attard
   *
   * @param <T>
   *          the property type
   */
  public static interface Handler<T extends PropertyEntry> {
    /**
     * Returns the {@link PropertyEntry} type that this handler can handle.
     *
     * @return the {@link PropertyEntry} type that this handler can handle.
     */
    Class<T> getHandlerType();

    /**
     * Handles the {@link PropertyEntry}
     *
     * @param t
     *          the {@link PropertyEntry} to be handled (which cannot be {@code null})
     * @throws Exception
     *           if an error occurs while handling the given proeprty entry
     */
    void handle(T t) throws Exception;
  }

  /** */
  private final List<LinePropertyEntryWriter.Handler<? extends PropertyEntry>> handlers = new LinkedList<>();

  /**
   *
   * @throws Exception
   */
  protected void doPostSingleWrite() throws Exception {}

  /**
   *
   * @param type
   * @return
   */
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

  /**
   *
   * @param handler
   */
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
