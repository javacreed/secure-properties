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
import org.junit.Ignore;
import org.junit.Test;

import com.javacreed.api.secureproperties.cipher.pbe.AesCipherFactory;
import com.javacreed.api.secureproperties.encoder.CipherStringEncoder;

/**
 *
 * This test is ignored as the Standard JRE does not support long keys for AES
 */
@Ignore
public class AesLongKeyCipherStringEncoderTest {

  @Test
  public void test() {
    final AesCipherFactory cipherFactory = new AesCipherFactory();
    cipherFactory.setKeyLength(192);
    final CipherStringEncoder encoder = new CipherStringEncoder(cipherFactory);
    Assert.assertEquals("a1c4054f59827c94cd1e1693c300a569", encoder.encode("hello"));
  }
}
