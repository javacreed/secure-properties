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
package com.javacreed.api.secureproperties.parser.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.AbstractPropertiesParser;

/**
 * @author Albert Attard
 */
public class ReaderPropertyParser extends AbstractPropertiesParser {

  /**
   *
   * @param file
   * @return
   * @throws IOException
   */
  public static List<PropertyEntry> readAndClose(final File file) throws IOException {
    return ReaderPropertyParser.readAndClose(new BufferedInputStream(new FileInputStream(file)));
  }

  /**
   *
   * @param input
   * @return
   * @throws IOException
   */
  public static List<PropertyEntry> readAndClose(final InputStream input) throws IOException {
    try (InputStream i = input) {
      return ReaderPropertyParser.readAndClose(i, "UTF-8");
    }
  }

  /**
   *
   * @param input
   * @param encoding
   * @return
   * @throws IOException
   */
  public static List<PropertyEntry> readAndClose(final InputStream input, final String encoding) throws IOException {
    try (InputStreamReader isr = new InputStreamReader(input, encoding);
        BufferedReader reader = new BufferedReader(isr)) {
      return new ReaderPropertyParser(reader).getProperties();
    }
  }

  /**
   *
   * @param path
   * @return
   * @throws IOException
   */
  public static List<PropertyEntry> readAndClose(final String path) throws IOException {
    return ReaderPropertyParser.readAndClose(new File(path));
  }

  /** */
  private final BufferedReader reader;

  /** */
  private LinePropertyEntryParser parser = new DefaultLinePropertyEntryParser();

  /**
   *
   * @param reader
   */
  public ReaderPropertyParser(final BufferedReader reader) {
    this.reader = reader;
  }

  @Override
  protected PropertyEntry readNext() throws Exception {
    StringBuilder builder = null;
    for (String line; (line = reader.readLine()) != null;) {
      if (builder == null) {
        builder = new StringBuilder();
      }

      if (line.endsWith("\\") == false) {
        builder.append(line);
        break;
      }

      // TODO: we need to test the feasibility of this
      // Remove the final backslash
      builder.append(line.substring(0, line.length() - 1));
      // builder.append("\n");
    }

    if (builder == null) {
      return null;
    }

    return parser.parse(builder.toString());
  }

  /**
   *
   * @param parser
   */
  public void setParser(final LinePropertyEntryParser parser) {
    this.parser = Objects.requireNonNull(parser);
  }
}
