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
package com.javacreed.secureproperties.adapter;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.adapter.CipherStringAdapter;

/**
 */
public class CipherAdapterDefaultTest {

  /**
   *
   */
  @Test
  public void test() {
    final CipherStringAdapter adapter = new CipherStringAdapter();
    final String encoded = adapter.encode("hello");
    Assert.assertNotNull(encoded);
    Assert.assertNotEquals("hello", encoded);
    Assert.assertEquals("hello", adapter.decode(encoded));
  }
}
