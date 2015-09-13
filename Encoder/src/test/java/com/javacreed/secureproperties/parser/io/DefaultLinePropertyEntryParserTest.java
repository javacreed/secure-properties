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
package com.javacreed.secureproperties.parser.io;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.io.DefaultLinePropertyEntryParser;

/**
 *
 * @author Albert Attard
 *
 */
public class DefaultLinePropertyEntryParserTest {

  /**
   *
   */
  @Test
  public void test() {
    final String line = "password={enc}2a9beaced9f3f73503bf7e44e15abb915ee747f1ac772267d434f50034a6587f59f23b5737e567eff105f19184235eb7d836a17bc63bd6167b67a4b3182fbde4";
    final DefaultLinePropertyEntryParser parser = new DefaultLinePropertyEntryParser();
    final PropertyEntry propertyEntry = parser.parse(line);
    Assert.assertNotNull(propertyEntry);
    Assert.assertTrue(propertyEntry instanceof EncodedNameValuePropertyEntry);

    final EncodedNameValuePropertyEntry envPropertyEntry = (EncodedNameValuePropertyEntry) propertyEntry;
    Assert.assertEquals("password", envPropertyEntry.getName());
    Assert
        .assertEquals(
            "2a9beaced9f3f73503bf7e44e15abb915ee747f1ac772267d434f50034a6587f59f23b5737e567eff105f19184235eb7d836a17bc63bd6167b67a4b3182fbde4",
            envPropertyEntry.getValue());
  }
}
