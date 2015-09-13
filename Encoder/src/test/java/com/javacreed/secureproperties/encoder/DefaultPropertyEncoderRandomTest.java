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
package com.javacreed.secureproperties.encoder;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.encoder.CipherStringEncoder;
import com.javacreed.api.secureproperties.encoder.DefaultFormatter;
import com.javacreed.api.secureproperties.encoder.DefaultPropertyEncoder;
import com.javacreed.api.secureproperties.encoder.PropertyEncoder;
import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;

/**
 * Tests the encoded values randomness. Note that when generating a large number of encoded values for the same
 * credentials, we may have duplicates. The randomness is determined by the padding size of the {@link DefaultFormatter}
 * class, which default value is 3. This is good for normal use, but will definitely produce duplicates if iterated more
 * than 50 times (some times even less). In order to improve this and mitigate the generation of duplicate encoded
 * values, we can increase the padding. Larger padding will also increase the size of the encoded data, which may be an
 * issue when properties are saved in a database.
 *
 * @author Albert Attard
 */
public class DefaultPropertyEncoderRandomTest {

  /**
   * Generate a number of encoded value for the same credentials and make sure that all encoded values are different
   *
   * @throws Exception
   *           if an error occurs
   */
  @Test
  public void test() throws Exception {
    // The plain text to be encoded several times
    final PropertyEntry plain = new PlainTextNameValuePropertyEntry("albertattard", "simple");

    // Use the largest padding value to reduce the chances of duplicate values
    final DefaultFormatter formtter = new DefaultFormatter(16, 16);

    final PropertyEncoder propertyEncoder = new DefaultPropertyEncoder(formtter, new CipherStringEncoder());

    // The set of duplicate values
    final Set<String> encodedValues = new HashSet<>();

    // Generate a number of encoded value for the same credentials and make sure that all encoded values are different
    for (int i = 0; i < 2000; i++) {
      final PropertyEntry encoded = propertyEncoder.encode(plain);
      Assert.assertNotNull(encoded);
      Assert.assertTrue(encoded instanceof EncodedNameValuePropertyEntry);

      final String value = ((EncodedNameValuePropertyEntry) encoded).getValue();
      Assert.assertTrue(encodedValues.add(value));
    }
  }
}
