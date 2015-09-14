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
 * Tests the {@link NumbersUtils#inRange(int, int, int, String)} method for boht valid and invalid methods
 *
 * @author Albert Attard
 */
public class NumbersUtilsInRangeTest {

  /**
   * Test invalid values which are higher than the upper bound
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidHigher() {
    NumbersUtils.inRange(11, 1, 10, "test");
  }

  /**
   * Test invalid values which are lower than the lower bound
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidLower() {
    NumbersUtils.inRange(0, 1, 10, "test");
  }

  /**
   * Test valid values
   */
  @Test
  public void testValid() {
    Assert.assertEquals(0, NumbersUtils.inRange(0, 0, 0, "test"));
    Assert.assertEquals(1, NumbersUtils.inRange(1, 0, 10, "test"));
  }
}
