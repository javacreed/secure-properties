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
package com.javacreed.api.secureproperties.writer.io;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

import com.javacreed.api.secureproperties.encoder.EncodedProperties;
import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.model.BasicPropertyEntry;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.writer.AbstractPropertyEntryWriter;

/**
 */
public class LinePropertyEntryWriter extends AbstractPropertyEntryWriter {

  private class BasicHandler extends AbstractHandler<BasicPropertyEntry> {

    private BasicHandler() {
      super(BasicPropertyEntry.class);
    }

    @Override
    public void handle(final BasicPropertyEntry entry) throws Exception {
      buffer.append(entry.getLine());
    }
  }

  private class NameValuePairHandler extends AbstractHandler<NameValuePropertyEntry> {

    private NameValuePairHandler() {
      super(NameValuePropertyEntry.class);
    }

    @Override
    public void handle(final NameValuePropertyEntry entry) throws Exception {
      buffer.append(entry.getName());
      buffer.append("=");
      buffer.append(entry.getValue());
    }
  }

  public static void writeAndClose(final Writer writer, final EncodedProperties properties) throws Exception {
    final LinePropertyEntryWriter lpew = new LinePropertyEntryWriter(writer);
    try {
      lpew.begin();
      for (final PropertyEntry entry : properties.getEntries()) {
        lpew.write(entry);
      }
      lpew.commit();
    } catch (final Exception e) {
      lpew.failed(e);
    } finally {
      writer.close();
    }
  }

  // public boolean accepts(PropertyEntry entry){
  // // TODO: need tro change this as it's not good OOP
  // return (entry instanceof CommentPropertyEntry) || (entry instanceof BlankPropertyEntry) || (entry instanceof
  // NameValuePropertyEntry);
  // }

  // /**
  // *
  // * @param writer
  // * @throws NullPointerException
  // */
  // public void setWriter(Writer writer) throws NullPointerException {
  // this.writer = Objects.requireNonNull(writer);
  // }

  private final Writer writer;

  private StringBuilder buffer;

  public LinePropertyEntryWriter(final Writer writer) {
    this.writer = Objects.requireNonNull(writer);

    // Register the handler. TODO: This should be done post construct
    registerHandler(new NameValuePairHandler());
    registerHandler(new BasicHandler());
  }

  @Override
  public void begin() {
    buffer = new StringBuilder();
  }

  @Override
  public void commit() {
    try {
      writer.write(buffer.toString());
    } catch (final IOException e) {
      throw EncoderException.launder(e);
    } finally {
      buffer = null;
    }
  }

  @Override
  protected void doPostSingleWrite() {
    // Use the current OS line separator
    buffer.append(System.lineSeparator());
  }

  @Override
  public void failed(final Exception e) {
    buffer = null;
  }
}
