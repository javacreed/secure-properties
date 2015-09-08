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
package com.javacreed.secureproperties.writer.io;

import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.model.BlankPropertyEntry;
import com.javacreed.api.secureproperties.model.CommentPropertyEntry;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.writer.io.LinePropertyEntryWriter;

/**
 */
public class LinePropertyEntryWriterTest {

  @Test
  public void test() {
    final StringWriter writer = new StringWriter();

    final LinePropertyEntryWriter lpeWriter = new LinePropertyEntryWriter(writer);
    lpeWriter.begin();
    lpeWriter.write(new CommentPropertyEntry("# Comment"));
    lpeWriter.write(new BlankPropertyEntry());
    lpeWriter.write(new NameValuePropertyEntry("name1", "value1"));
    lpeWriter.write(new NameValuePropertyEntry("name2", "value2"));
    lpeWriter.commit();

    final String[] lines = writer.toString().split(System.lineSeparator());
    Assert.assertEquals(4, lines.length);
    Assert.assertEquals("# Comment", lines[0]);
    Assert.assertEquals("", lines[1]);
    Assert.assertEquals("name1=value1", lines[2]);
    Assert.assertEquals("name2=value2", lines[3]);
  }

}
