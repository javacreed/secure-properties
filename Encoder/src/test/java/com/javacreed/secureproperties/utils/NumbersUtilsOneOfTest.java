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

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.utils.NumbersUtils;

/**
 *
 * @author Albert Attard
 */
public class NumbersUtilsOneOfTest {

  /**
   * Test valid value
   */
  @Test
  public void test() {
    Assert.assertEquals(1, NumbersUtils.oneOf(1, "test", 1, 2, 3));
    Assert.assertEquals(2, NumbersUtils.oneOf(2, "test", 1, 2, 3));
    Assert.assertEquals(3, NumbersUtils.oneOf(3, "test", 1, 2, 3));
  }

  /**
   * Tests an invalid value
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalid() {
    NumbersUtils.oneOf(1, "test", 0, 2, 3, 4);
  }
}
