/*
 * #%L
 * JavaCreed Secure Properties Spring Encoder
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
package com.javacreed.api.secureproperties.spring;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 */
public class SpringPropertiesTest {

  @Test
  public void test() throws IOException {

    final String[] configurations = { "test-configuration-1.xml", "test-configuration-2.xml",
        "test-configuration-3.xml", "test-configuration-4.xml", "test-configuration-5.xml",
    /* "test-configuration-6.xml" */};

    /*
     * The source properties file that contains the plain text password and the target properties file from where the
     * configuration reads the properties
     */
    final File source = new File("src/test/resources/samples/properties/file.001.properties");
    final File target = new File("target/samples/properties/file.001.properties");
    target.getParentFile().mkdirs();

    for (final String configuration : configurations) {

      // Copy the properties file before the test
      Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);

      for (int i = 0; i < 3; i++) {
        try (ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/"
            + configuration)) {
          // Check all three properties
          Assert.assertEquals("Albert", applicationContext.getBeanFactory().resolveEmbeddedValue("${name}"));
          Assert.assertEquals("Somewhere in Malta",
              applicationContext.getBeanFactory().resolveEmbeddedValue("${address}"));
          Assert.assertEquals("my long secret password",
              applicationContext.getBeanFactory().resolveEmbeddedValue("${password}"));

          // Make sure that the password was encoded
          final List<String> lines = Files.readAllLines(target.toPath(), Charset.forName("UTF-8"));
          Assert.assertEquals(4, lines.size());
          Assert.assertEquals("# This is a simple properties file", lines.get(0));
          Assert.assertEquals("name=Albert", lines.get(1));
          Assert.assertEquals("address=Somewhere in Malta", lines.get(2));
          Assert.assertTrue(lines.get(3).startsWith("password={enc}"));
          Assert.assertFalse(lines.get(3).contains("my long secret password"));
        }
      }
    }
  }
}
