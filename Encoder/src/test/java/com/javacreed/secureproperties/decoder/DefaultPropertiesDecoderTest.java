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

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.decoder.DecodedProperties;
import com.javacreed.api.secureproperties.decoder.DefaultPropertiesDecoder;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.io.ReaderPropertyParser;

/**
 */
public class DefaultPropertiesDecoderTest {

  @Test
  public void test() throws IOException {
    final List<PropertyEntry> properties = ReaderPropertyParser.readAndClose(getClass().getResourceAsStream(
        "/samples/properties/file.002.properties"));

    final DefaultPropertiesDecoder propertiesDecoder = new DefaultPropertiesDecoder();
    final DecodedProperties decoded = propertiesDecoder.decode(properties);
    Assert.assertNotNull(decoded);
    Assert.assertTrue(decoded.wereDecoded());
    Assert.assertEquals(1, decoded.getNumberOfDecodedProperties());
    Assert.assertNotNull(decoded.getEntries());
  }

}
