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
 * Parses an encoded string into a {@link PlainTextNameValuePropertyEntry}.
 *
 * @author Albert Attard
 */
public interface Parser {

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
