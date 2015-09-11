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

import org.junit.Test;

import com.javacreed.api.secureproperties.cipher.pbe.AesCipherFactory;

/**
 * The JRE limits the key length for AES to 128 bits unless special libraries are installed. The AES cipher factory
 * checks the JRE limit before accepts the new key length and fails if this is exceeded. For more information about the
 * standard key lengths please refer to the <em>Keysize Restrictions<em/> section at:
 * <a href="http://docs.oracle.com/javase/7/docs/technotes/guides/security/SunProviders.html">http://docs.oracle.com/javase/7/docs/technotes/guides/security/SunProviders.html</a>
 */
public class AesCipherFactoryKeyLengthTest {

  /**
   * The JRE limits the key length for AES to 128 bits unless special libraries are installed. The AES cipher factory
   * checks the JRE limit before accepts the new key length and fails if this is exceeded. For more information about
   * the standard key lengths please refer to the <em>Keysize Restrictions<em/> section at:
   * <a href="http://docs.oracle.com/javase/7/docs/technotes/guides/security/SunProviders.html">http://docs.oracle.com/javase/7/docs/technotes/guides/security/SunProviders.html</a>
   */
  @Test(expected = IllegalArgumentException.class)
  public void test() throws Exception {
    final AesCipherFactory cipherFactory = new AesCipherFactory();
    cipherFactory.setKeyLength(256);
  }
}
