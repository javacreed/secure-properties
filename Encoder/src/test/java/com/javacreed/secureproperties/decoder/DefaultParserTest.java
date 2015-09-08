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

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.decoder.DefaultParser;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;

/**
 */
public class DefaultParserTest {

  private static class Sample {

    private final String encoded;
    private final String name;
    private final String value;

    public Sample(final String encoded, final String name, final String value) {
      this.encoded = encoded;
      this.name = name;
      this.value = value;
    }
  }

  @Test
  public void test() {
    final List<Sample> samples = new LinkedList<>();
    samples.add(new Sample("123456,4,java,5,creed", "java", "creed"));
    samples.add(new Sample("123456,10,java,creed,10,java,creed", "java,creed", "java,creed"));

    final DefaultParser parser = new DefaultParser();
    for (final Sample sample : samples) {
      final NameValuePropertyEntry nvpEntry = parser.parse(sample.encoded);
      Assert.assertNotNull(nvpEntry);
      Assert.assertEquals(sample.name, nvpEntry.getName());
      Assert.assertEquals(sample.value, nvpEntry.getValue());
    }
  }
}
