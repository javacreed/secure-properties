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
 */
public class PropertyDecoderTest {

  @Test
  public void test() {
    final EncodedNameValuePropertyEntry envPropertyEntry = new EncodedNameValuePropertyEntry(
        "password",
        "5a4a9bb633d7f624197a0bf6efeae502f9e32762c5598f1329f79ba41fac663b41e293d410ca5d0d20e91ffa8a3a3b3525bfe00fc672872125dcc833834151c2");

    final PropertyDecoder decoder = new DefaultPropertyDecoder();
    final PropertyEntry entry = decoder.decode(envPropertyEntry);
    Assert.assertNotNull(entry);
    Assert.assertTrue(entry instanceof NameValuePropertyEntry);

    final NameValuePropertyEntry nvpEntry = (NameValuePropertyEntry) entry;
    Assert.assertEquals("password", nvpEntry.getName());
    Assert.assertEquals("my long secret password", nvpEntry.getValue());
  }
}
