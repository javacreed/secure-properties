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
package com.javacreed.secureproperties.utils;

import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.utils.HexUtils;

/**
 */
public class HexUtilsTest {

  /**
   *
   * @throws UnsupportedEncodingException
   */
  @Test
  public void test() throws UnsupportedEncodingException {
    final String s = "hello";
    final String hex = HexUtils.toHexString(s.getBytes("UTF-8"));
    Assert.assertEquals("68656c6c6f", hex);
    final byte[] b = HexUtils.toByteArray(hex);
    Assert.assertEquals(s, new String(b, "UTF-8"));
  }
}
