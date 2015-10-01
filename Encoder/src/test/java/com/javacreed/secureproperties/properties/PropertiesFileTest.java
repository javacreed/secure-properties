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
package com.javacreed.secureproperties.properties;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.properties.PropertiesFile;

/**
 * @author Albert Attard
 */
public class PropertiesFileTest {

  @Test
  public void test() throws Exception {
    final File file = new File("target/samples/properties/file.001.properties").getAbsoluteFile();
    file.getParentFile().mkdirs();
    try (InputStream in = getClass().getResourceAsStream("/samples/properties/file.001.properties")) {
      Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    final PropertiesFile propertiesFile = new PropertiesFile();
    final Properties properties = propertiesFile.loadProperties(file);
    Assert.assertNotNull(properties);
    Assert.assertEquals("Albert", properties.getProperty("name"));
    Assert.assertEquals("Somewhere in Malta", properties.getProperty("address"));
    Assert.assertEquals("my long secret password", properties.getProperty("password"));

    final List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
    Assert.assertEquals(4, lines.size());
    Assert.assertEquals("# This is a simple properties file", lines.get(0));
    Assert.assertEquals("name=Albert", lines.get(1));
    Assert.assertEquals("address=Somewhere in Malta", lines.get(2));
    Assert.assertNotNull(lines.get(3));
    Assert.assertTrue(lines.get(3).startsWith("password={enc}"));
    Assert.assertFalse(lines.get(3).contains("my long secret password"));
  }
}
