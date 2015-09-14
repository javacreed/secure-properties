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

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.decoder.DefaultPropertyDecoder;
import com.javacreed.api.secureproperties.decoder.PropertyDecoder;
import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;

/**
 *
 * @author Albert Attard
 */
public class PropertyDecoderTest {

  @Test
  public void test() {
    final String[] encoded = {
        "5a4a9bb633d7f624197a0bf6efeae502f9e32762c5598f1329f79ba41fac663b41e293d410ca5d0d20e91ffa8a3a3b3525bfe00fc672872125dcc833834151c2",
    "2a9beaced9f3f73503bf7e44e15abb915ee747f1ac772267d434f50034a6587f59f23b5737e567eff105f19184235eb7d836a17bc63bd6167b67a4b3182fbde4" };

    for (final String e : encoded) {
      final EncodedNameValuePropertyEntry envPropertyEntry = new EncodedNameValuePropertyEntry("password", e);
      final PropertyDecoder decoder = new DefaultPropertyDecoder();
      final PropertyEntry entry = decoder.decode(envPropertyEntry);
      Assert.assertNotNull(entry);
      Assert.assertTrue(entry instanceof NameValuePropertyEntry);

      final NameValuePropertyEntry nvpEntry = (NameValuePropertyEntry) entry;
      Assert.assertEquals("password", nvpEntry.getName());
      Assert.assertEquals("my long secret password", nvpEntry.getValue());
    }
  }
}
