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

import com.javacreed.api.secureproperties.cipher.pbe.AesCipherFactory;
import com.javacreed.api.secureproperties.utils.CipherUtils;

/**
 * Test the AES cipher factory with different configuration values. The key length is kept at 128 as the other sizes,
 * 192 and 256 requires additional libraries which may be absent in certain runtimes.
 */
public class AesCipherFactoryTest {

  /**
   * Test the AES cipher factory with different configuration values. The key length is kept at 128 as the other sizes,
   * 192 and 256 requires additional libraries which may be absent in certain runtimes.
   */
  @Test
  public void test() {
    final AesCipherFactory cipherFactory = new AesCipherFactory();
    cipherFactory.setConfiguration("AES/CFB128/PKCS5Padding");
    cipherFactory.setFactoryName("PBKDF2WithHmacSHA1");
    cipherFactory.setIterationCount(1);
    cipherFactory.setIv(new byte[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
    cipherFactory.setKeyLength(128);
    cipherFactory.setSalt(new byte[] { 1, 1, 1, 1, 1, 1, 1, 1 });

    final String s = "hello";
    final String x = CipherUtils.encode(s, cipherFactory);
    Assert.assertNotNull(x);
    Assert.assertNotEquals(s, x);
    Assert.assertEquals(s, CipherUtils.decode(x, cipherFactory));
  }
}
