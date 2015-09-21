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

import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;

/**
 * Encodes a plain text property into an encoded property entry
 *
 * @author Albert Attard
 */
public interface PropertyEncoder {

  /**
   * Encodes a plain text property into an encoded property entry
   * 
   * @param entry
   *          the property to be encoded (which cannot be {@code null})
   * @return the encoded property
   * @throws EncoderException
   *           if an error occurs while encoding the property
   * @throws NullPointerException
   *           if the given {@code entry} is {@code null}
   */
  public EncodedNameValuePropertyEntry encode(PlainTextNameValuePropertyEntry entry) throws EncoderException,
      NullPointerException;
}
