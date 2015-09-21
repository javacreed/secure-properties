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
package com.javacreed.secureproperties.parser.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.db.ResultSetPropertyParser;
import com.javacreed.secureproperties.writer.db.AbstractDbTest;

/**
 *
 * @author Albert Attard
 */
public class ResultSetPropertyParserTest extends AbstractDbTest {

  @Test
  public void test() throws SQLException {
    insertData("name1", "value1");
    insertData("name2", "{pln}value2");
    insertData(
        "name3",
        "{enc}5746a9afdf5724dfc344f875a5dc17bd67b261906896f6da09721578e96c22e11bd9fc2a25ab85a3c286359056a02230b949fa40cd0f68a4499f4220ada3304b");

    try (Connection connection = dbHelper.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + defaultTableName + "`")) {
      final ResultSetPropertyParser parser = new ResultSetPropertyParser(resultSet);

      final List<PropertyEntry> properties = parser.getProperties();
      Assert.assertEquals(3, properties.size());

      PropertyEntry entry = properties.get(0);
      Assert.assertTrue(entry instanceof NameValuePropertyEntry);
      NameValuePropertyEntry nameValuePropertyEntry = (NameValuePropertyEntry) entry;
      Assert.assertEquals("name1", nameValuePropertyEntry.getName());
      Assert.assertEquals("value1", nameValuePropertyEntry.getValue());

      entry = properties.get(1);
      Assert.assertTrue(entry instanceof PlainTextNameValuePropertyEntry);
      nameValuePropertyEntry = (PlainTextNameValuePropertyEntry) entry;
      Assert.assertEquals("name2", nameValuePropertyEntry.getName());
      Assert.assertEquals("value2", nameValuePropertyEntry.getValue());

      entry = properties.get(2);
      Assert.assertTrue(entry instanceof EncodedNameValuePropertyEntry);
      nameValuePropertyEntry = (EncodedNameValuePropertyEntry) entry;
      Assert.assertEquals("name3", nameValuePropertyEntry.getName());
      Assert
          .assertEquals(
              "5746a9afdf5724dfc344f875a5dc17bd67b261906896f6da09721578e96c22e11bd9fc2a25ab85a3c286359056a02230b949fa40cd0f68a4499f4220ada3304b",
              nameValuePropertyEntry.getValue());
    }
  }

}
