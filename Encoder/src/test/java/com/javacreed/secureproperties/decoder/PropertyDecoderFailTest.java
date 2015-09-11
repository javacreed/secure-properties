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
package com.javacreed.secureproperties.decoder;

import org.junit.Test;

import com.javacreed.api.secureproperties.decoder.DefaultPropertyDecoder;
import com.javacreed.api.secureproperties.decoder.InvalidEncodedValueException;
import com.javacreed.api.secureproperties.decoder.PropertyDecoder;
import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;

/**
 * Tests the decoding of a property where its name is changed following the encoding. The encoded value is bound with
 * the property name so that these are not easily swapped and to prevent side channels attacks by learning the password
 * just by observing the encoded value.
 */
public class PropertyDecoderFailTest {

  /**
   * Tests the decoding of a property where its name is changed following the encoding. The encoded value is bound with
   * the property name so that these are not easily swapped and to prevent side channels attacks by learning the
   * password just by observing the encoded value.
   */
  @Test(expected = InvalidEncodedValueException.class)
  public void test() {
    final EncodedNameValuePropertyEntry envPropertyEntry = new EncodedNameValuePropertyEntry(
        "incorrect property name",
        "5a4a9bb633d7f624197a0bf6efeae502f9e32762c5598f1329f79ba41fac663b41e293d410ca5d0d20e91ffa8a3a3b3525bfe00fc672872125dcc833834151c2");

    final PropertyDecoder decoder = new DefaultPropertyDecoder();
    decoder.decode(envPropertyEntry);
  }
}
