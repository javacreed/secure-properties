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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.db.ResultSetPropertyParser;
import com.javacreed.secureproperties.utils.DbHelper;

/**
 */
public class ResultSetPropertyParserTest {

  private DbHelper dbHelper;

  @After
  public void destroy() {
    dbHelper.close();
  }

  @Before
  public void init() throws SQLException {
    dbHelper = DbHelper.create();
    dbHelper.execute("DROP TABLE IF EXISTS `test_properties`");
    dbHelper
    .execute("CREATE TABLE `test_properties` (`name` VARCHAR(64) NOT NULL, `value` VARCHAR(128) NOT NULL, PRIMARY KEY(`name`))");
  }

  @Test
  public void test() throws SQLException {
    dbHelper.execute("INSERT INTO `test_properties` VALUES ('name1', 'value1')");
    dbHelper.execute("INSERT INTO `test_properties` VALUES ('name2', 'value2')");

    try (Statement statement = dbHelper.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM `test_properties`")) {
      final ResultSetPropertyParser parser = new ResultSetPropertyParser(resultSet);

      final List<PropertyEntry> properties = parser.getProperties();
      Assert.assertEquals(2, properties.size());

      PropertyEntry entry = properties.get(0);
      Assert.assertTrue(entry instanceof NameValuePropertyEntry);
      NameValuePropertyEntry nameValuePropertyEntry = (NameValuePropertyEntry) entry;
      Assert.assertEquals("name1", nameValuePropertyEntry.getName());
      Assert.assertEquals("value1", nameValuePropertyEntry.getValue());

      entry = properties.get(1);
      Assert.assertTrue(entry instanceof NameValuePropertyEntry);
      nameValuePropertyEntry = (NameValuePropertyEntry) entry;
      Assert.assertEquals("name2", nameValuePropertyEntry.getName());
      Assert.assertEquals("value2", nameValuePropertyEntry.getValue());
    }
  }

}
