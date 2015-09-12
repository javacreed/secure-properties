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

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.encoder.EncoderException;

public class EncoderExceptionTest {

  @Test
  public void test() {
    EncoderException e = EncoderException.launder(null);
    Assert.assertNotNull(e);
    Assert.assertNull(e.getCause());

    e = EncoderException.launder(new RuntimeException("Test"));
    Assert.assertNotNull(e);
    Assert.assertNotNull(e.getCause());
    Assert.assertEquals("Test", e.getCause().getMessage());

    e = EncoderException.launder(new EncoderException("Test"));
    Assert.assertNotNull(e);
    Assert.assertEquals("Test", e.getMessage());
    Assert.assertNull(e.getCause());
  }
}
