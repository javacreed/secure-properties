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
package com.javacreed.secureproperties.writer.db;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.writer.db.DbPropertyEntryWriter;
import com.javacreed.secureproperties.utils.DbHelper;

/**
 */
public class DbPropertyEntryWriterTest {

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
  public void test() throws Exception {
    dbHelper.execute("INSERT INTO `test_properties` VALUES ('name1', 'value1')");
    dbHelper.execute("INSERT INTO `test_properties` VALUES ('name2', 'value2')");

    // TODO: test within a transaction
    final DbPropertyEntryWriter writer = new DbPropertyEntryWriter(dbHelper.getConnection(), "test_properties");
    writer.write(new NameValuePropertyEntry("name1", "valueA"));
    writer.write(new NameValuePropertyEntry("name2", "valueB"));

    Assert.assertEquals("valueA",
        dbHelper.queryForSingleValue("SELECT `value` FROM `test_properties` WHERE `name`='name1'"));
    Assert.assertEquals("valueB",
        dbHelper.queryForSingleValue("SELECT `value` FROM `test_properties` WHERE `name`='name2'"));
  }

}
