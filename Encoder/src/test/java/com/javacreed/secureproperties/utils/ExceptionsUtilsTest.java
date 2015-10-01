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

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.utils.ExceptionsUtils;

/**
 * Tests the {@link ExceptionsUtils} class
 *
 * @author Albert Attard
 * @see ExceptionsUtils
 */
public class ExceptionsUtilsTest {

  /**
   * Tests the {@link ExceptionsUtils#isCausedBy(Throwable, Class)} method
   */
  @Test
  public void testIsCausedBy() {
    final Exception e = new Exception("Test Exception", new RuntimeException(new InterruptedException()));

    Assert.assertTrue(ExceptionsUtils.isCausedBy(e, RuntimeException.class));
    Assert.assertTrue(ExceptionsUtils.isCausedBy(e, InterruptedException.class));
    Assert.assertFalse(ExceptionsUtils.isCausedBy(e, IOException.class));
  }
}
