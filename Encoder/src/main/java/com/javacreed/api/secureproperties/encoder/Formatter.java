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
package com.javacreed.api.secureproperties.encoder;

import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;

/**
 * Formats an instance of {@link PlainTextNameValuePropertyEntry} into a single string. The formatting does not
 * necessary obfuscate the original message. It basically transforms the given plain text property entry into one
 * string. The output of this class is usually used as an input by the {@link StringEncoder} class.
 *
 * The output of one method can be used as an input of the other method to produce an equivalent object.
 *
 * <pre>
 * PlainTextNameValuePropertyEntry propertyA = ...
 * String formatted = format(propertyA);
 * PlainTextNameValuePropertyEntry propertyB = parse(formatted);
 * Assert.assertEquals(propertyA, propertyB);
 * </pre>
 *
 * And same applies to the opposite order
 *
 * <pre>
 * String formattedA = ...
 * PlainTextNameValuePropertyEntry property = parse(formattedA);
 * String formattedB = format(property);
 * Assert.assertEquals(formattedA, formattedB);
 * </pre>
 *
 * @author Albert Attard
 *
 * @see PlainTextNameValuePropertyEntry
 * @see StringEncoder
 */
public interface Formatter {

  /**
   * Returns a formatted string representing the given {@code propertyEntry}
   *
   * @param propertyEntry
   *          the property entry to be formatted (which cannot be {@code null})
   * @return a formatted string (which will not be {@code null})
   * @throws NullPointerException
   *           if the given {@code propertyEntry} is {@code null}
   */
  String format(PlainTextNameValuePropertyEntry propertyEntry) throws NullPointerException;

  /**
   * Parses the given {@code formatted} into a {@link PlainTextNameValuePropertyEntry}.
   *
   * @param formatted
   *          the formatted property value (which cannot be {@code null})
   * @return an instance of the {@link PlainTextNameValuePropertyEntry}
   * @throws EncoderException
   *           if the given {@code formatted} property value cannot be parsed
   * @throws NullPointerException
   *           if the given {@code formatted} property value is {@code null}
   */
  PlainTextNameValuePropertyEntry parse(String formatted) throws EncoderException, NullPointerException;
}
