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
import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.writer.AbstractPropertyEntryWriter;

/**
 *
 * @author Albert Attard
 */
public class LinePropertyEntryWriter extends AbstractPropertyEntryWriter {

  /**
   *
   * @author Albert Attard
   *
   */
  private class BasicHandler extends AbstractHandler<BasicPropertyEntry> {

    private BasicHandler() {
      super(BasicPropertyEntry.class);
    }

    @Override
    public void handle(final BasicPropertyEntry entry) throws Exception {
      buffer.append(entry.getLine());
    }
  }

  /**
   *
   * @author Albert Attard
   *
   */
  private class EncodedNameValuePairHandler extends AbstractHandler<EncodedNameValuePropertyEntry> {

    private EncodedNameValuePairHandler() {
      super(EncodedNameValuePropertyEntry.class);
    }

    @Override
    public void handle(final EncodedNameValuePropertyEntry entry) throws Exception {
      buffer.append(entry.getName());
      buffer.append("={enc}");
      buffer.append(entry.getValue());
    }
  }

  /**
   *
   * @author Albert Attard
   *
   */
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

  /**
   *
   * @author Albert Attard
   *
   */
  private class PlainTextNameValuePairHandler extends AbstractHandler<PlainTextNameValuePropertyEntry> {

    private PlainTextNameValuePairHandler() {
      super(PlainTextNameValuePropertyEntry.class);
    }

    @Override
    public void handle(final PlainTextNameValuePropertyEntry entry) throws Exception {
      buffer.append(entry.getName());
      buffer.append("={pln}");
      buffer.append(entry.getValue());
    }
  }

  /**
   *
   * @param writer
   * @param properties
   * @throws Exception
   */
  public static void writeAndClose(final Writer writer, final EncodedProperties properties) throws Exception {
    // TODO: this should be moved elsewhere
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

  /** */
  private final Writer writer;

  /** */
  private StringBuilder buffer;

  /**
   *
   * @param writer
   * @throws NullPointerException
   *           if the given {@code writer} is {@code null}
   */
  public LinePropertyEntryWriter(final Writer writer) throws NullPointerException {
    this.writer = Objects.requireNonNull(writer);

    /*
     * Register the handler. Ideally this is done in a post construct method but the classes created below do not access
     * the hosting class.
     */
    registerHandler(new PlainTextNameValuePairHandler());
    registerHandler(new EncodedNameValuePairHandler());
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
