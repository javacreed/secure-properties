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

import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;

/**
 * The default parser implementation
 */
public class DefaultParser implements Parser {

  @Override
  public PlainTextNameValuePropertyEntry parse(final String formatted) throws EncoderException, NullPointerException {
    final String[] parts = formatted.split(",", 3);

    if (parts[1] == null || parts[1].matches("\\d+") == false) {
      throw new EncoderException("");
    }

    final int nameLength = Integer.parseInt(parts[1]);
    final String name = parts[2].substring(0, nameLength);

    final int valueIndex = parts[2].indexOf(",", nameLength + 1);
    final String value = parts[2].substring(valueIndex + 1);

    return new PlainTextNameValuePropertyEntry(name, value);
  }

}
