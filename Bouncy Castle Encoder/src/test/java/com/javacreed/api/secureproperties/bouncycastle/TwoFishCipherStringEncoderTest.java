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
package com.javacreed.api.secureproperties.bouncycastle;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.encoder.CipherStringEncoder;

public class TwoFishCipherStringEncoderTest {

  @Test
  public void test() {
    final CipherStringEncoder encoder = new CipherStringEncoder(new TwoFishCipherFactory());
    Assert.assertEquals("8e1ceac71eae495d91015fc588f42eeb", encoder.encode("hello"));
  }
}
