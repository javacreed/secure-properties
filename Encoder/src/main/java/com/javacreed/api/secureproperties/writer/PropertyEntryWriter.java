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

import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.model.PropertyEntry;

/**
 * Defines the flow used by the property writer. Before a property is written, the {@link #begin()} method needs to be
 * invoked. If this is not invoked, then we cannot write the properties. One or more properties can be encoded and
 * written by using the {@link #write(PropertyEntry)} method. Once done, the {@link #commit()} method needs to be
 * invoked to finalise the process. If an error occurs, the {@link #failed(Exception)} method can be invoked which will
 * prevent the properties from being persisted. The properties are only persisted when the {@link #commit()} method is
 * invoked.
 *
 * @author Albert Attard
 */
public interface PropertyEntryWriter {

  /**
   * Starts the writing process. This is the first method to be invoked. If this method is invoked before the previous
   * session is closed ({@link #commit()} or {@link #failed(Exception)}), an {@link EncoderException} is thrown.
   *
   * @throws EncoderException
   *           if an error occurs while starting the process
   */
  void begin() throws EncoderException;

  /**
   * Finalise the current session and persists the changes. This method also release any resources and no further writes
   * are permitted.
   *
   * @throws EncoderException
   *           if it fails to persist the changes
   */
  void commit() throws EncoderException;

  /**
   * Indicates that an error occurred elsewhere and the session needs to be stopped before persisting the data.
   *
   * @param e
   *          the error that caused the session to terminates prematurely
   */
  void failed(Exception e);

  /**
   * Writes a property entry. This method requires a session and the {@link #begin()} method needs to be invoked before
   * this method.
   *
   * @param propertyEntry
   *          the property entry (which cannot be {@code null})
   * @throws EncoderException
   *           if the property entry cannot be written
   */
  void write(PropertyEntry propertyEntry) throws EncoderException;
}
