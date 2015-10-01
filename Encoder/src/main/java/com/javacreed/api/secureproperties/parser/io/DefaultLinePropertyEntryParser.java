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

import com.javacreed.api.secureproperties.model.BlankPropertyEntry;
import com.javacreed.api.secureproperties.model.CommentPropertyEntry;
import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.PropertyEntryParseException;

/**
 *
 * @author Albert Attard
 */
public class DefaultLinePropertyEntryParser implements LinePropertyEntryParser {

  /**
   *
   */
  @Override
  public PropertyEntry parse(final String line) throws PropertyEntryParseException {
    if (line == null) {
      throw new PropertyEntryParseException("Line cannot be null");
    }

    if (line.matches("\\s*")) {
      return new BlankPropertyEntry(line);
    }

    if (line.matches("#.*")) {
      return new CommentPropertyEntry(line);
    }

    if (line.matches("(?s).+=.*")) {
      final String parts[] = line.split("=", 2);

      if (parts[1].startsWith("{enc}")) {
        return new EncodedNameValuePropertyEntry(parts[0], parts[1].substring(5));
      }
      if (parts[1].startsWith("{pln}")) {
        return new PlainTextNameValuePropertyEntry(parts[0], parts[1].substring(5));
      }

      return new NameValuePropertyEntry(parts[0], parts[1]);
    }

    throw new PropertyEntryParseException("Invalid property line");
  }
}
