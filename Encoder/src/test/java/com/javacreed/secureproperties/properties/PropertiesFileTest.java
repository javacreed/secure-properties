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
import java.util.Properties;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.javacreed.api.secureproperties.properties.PropertiesFile;

/**
 * This test fails and need to be fixed
 */
@Ignore
public class PropertiesFileTest {

  @Test
  public void test() throws Exception {
    final PropertiesFile propertiesFile = new PropertiesFile();
    final Properties properties = propertiesFile.loadProperties(new File(getClass().getResource(
        "/samples/properties/file.001.properties").toURI()));
    Assert.assertNotNull(properties);

  }

}
