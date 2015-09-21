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

import java.util.Iterator;

import com.javacreed.api.secureproperties.model.PropertyEntry;

/**
 * Encodes a list of properties
 * 
 * @author Albert Attard
 */
public interface PropertiesEncoder {

  /**
   * Encodes the given properties, if encoding is necessary, and returns the encoded properties
   * 
   * @param propertiesEntries
   *          the properties to be encoded
   * @return the encoded properties
   * @throws NullPointerException
   *           if the given {@code propertiesEntries} is {@code null}
   * @throws EncoderException
   *           if an error occurs while encoding the properties
   */
  EncodedProperties encode(Iterable<PropertyEntry> propertiesEntries) throws NullPointerException, EncoderException;

  /**
   * Encodes the given properties, if encoding is necessary, and returns the encoded properties
   * 
   * @param propertiesEntries
   *          the properties to be encoded
   * @return the encoded properties
   * @throws NullPointerException
   *           if the given {@code propertiesEntries} is {@code null}
   * @throws EncoderException
   *           if an error occurs while encoding the properties
   */
  EncodedProperties encode(Iterator<PropertyEntry> propertiesEntries) throws NullPointerException, EncoderException;
}
