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
package com.javacreed.secureproperties.cipher.pbe;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.cipher.pbe.TripleDesCipherFactory;
import com.javacreed.api.secureproperties.encoder.CipherStringEncoder;

/**
 *
 */
public class TripleDesCipherStringEncoderTest {

  /**
   *
   */
  @Test
  public void test() {
    final CipherStringEncoder encoder = new CipherStringEncoder(new TripleDesCipherFactory());
    Assert.assertEquals("afba0dbd18e6a33d", encoder.encode("hello"));
    Assert.assertEquals("hello", encoder.decode("afba0dbd18e6a33d"));
  }
}
