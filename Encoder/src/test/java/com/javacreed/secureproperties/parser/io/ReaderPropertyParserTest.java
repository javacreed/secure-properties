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
package com.javacreed.secureproperties.parser.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.Test;

import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.io.ReaderPropertyParser;

public class ReaderPropertyParserTest {

  @Test
  public void test() throws Exception {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(
        "/samples/properties/file.001.properties")))) {
      final ReaderPropertyParser parser = new ReaderPropertyParser(reader);
      for (final PropertyEntry entry : parser.getProperties()) {
        System.out.println(entry);
      }
    }
  }

}
