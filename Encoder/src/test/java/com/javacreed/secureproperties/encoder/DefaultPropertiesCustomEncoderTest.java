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
package com.javacreed.secureproperties.encoder;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.cipher.pbe.TripleDesCipherFactory;
import com.javacreed.api.secureproperties.encoder.DefaultPropertiesEncoder;
import com.javacreed.api.secureproperties.encoder.EncodedProperties;
import com.javacreed.api.secureproperties.encoder.PropertiesEncoder;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.io.ReaderPropertyParser;
import com.javacreed.api.secureproperties.writer.io.LinePropertyEntryWriter;

/**
 * Tests the default properties using a custom encoder, based on Triple DES
 *
 * @author Albert Attard
 *
 * @see TripleDesCipherFactory
 * @see DefaultPropertiesEncoder
 */
public class DefaultPropertiesCustomEncoderTest {

  @Test
  public void test() throws Exception {
    final String path = "target/test.samples.properties";
    Files.copy(getClass().getResourceAsStream("/samples/properties/file.001.properties"), Paths.get(path),
        StandardCopyOption.REPLACE_EXISTING);

    /* Parse the Properties */
    final List<PropertyEntry> properties = ReaderPropertyParser.readAndClose(path);

    /* The custom encoder based on triple DES */
    final TripleDesCipherFactory cipherFactory = new TripleDesCipherFactory("THIS is MY new Password!");

    final PropertiesEncoder propertiesEncoder = new DefaultPropertiesEncoder(cipherFactory);
    final EncodedProperties encodedProperties = propertiesEncoder.encode(properties);

    Assert.assertNotNull(encodedProperties);
    Assert.assertTrue(encodedProperties.wereEncoded());
    Assert.assertEquals(1, encodedProperties.getNumberOfEncodedProperties());

    LinePropertyEntryWriter.writeAndClose(new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(path), "UTF-8")), encodedProperties);

    final List<String> lines = Files.readAllLines(Paths.get(path), Charset.forName("UTF-8"));
    Assert.assertNotNull(lines);
    Assert.assertEquals(4, lines.size());
    Assert.assertEquals("# This is a simple properties file", lines.get(0));
    Assert.assertEquals("name=Albert", lines.get(1));
    // TODO: We should preserve the multi line here as well
    Assert.assertEquals("address=Somewhere in Malta", lines.get(2));
    Assert.assertNotNull(lines.get(3));
    Assert.assertTrue(lines.get(3).startsWith("password={enc}"));
  }
}
