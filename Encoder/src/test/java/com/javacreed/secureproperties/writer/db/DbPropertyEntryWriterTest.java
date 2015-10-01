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

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;
import com.javacreed.api.secureproperties.writer.db.DbPropertyEntryWriter;

/**
 *
 * @author Albert Attard
 */
public class DbPropertyEntryWriterTest extends AbstractDbTest {

  /**
   *
   * @throws Exception
   */
  @Test
  public void test() throws Exception {
    dbHelper.execute("INSERT INTO `" + defaultTableName + "` VALUES ('name1', 'value1')");
    dbHelper.execute("INSERT INTO `" + defaultTableName + "` VALUES ('name2', 'value2')");
    dbHelper.execute("INSERT INTO `" + defaultTableName + "` VALUES ('name3', 'value3')");

    // TODO: test within a transaction
    final DbPropertyEntryWriter writer = new DbPropertyEntryWriter(dbHelper.getDataSource(), defaultTableName);
    writer.begin();
    writer.write(new NameValuePropertyEntry("name1", "valueA"));
    writer.write(new PlainTextNameValuePropertyEntry("name2", "valueB"));
    writer.write(new EncodedNameValuePropertyEntry("name3", "valueC"));
    writer.commit();

    Assert.assertEquals("valueA",
        dbHelper.queryForSingleValue("SELECT `value` FROM `" + defaultTableName + "` WHERE `name`='name1'"));
    Assert.assertEquals("{pln}valueB",
        dbHelper.queryForSingleValue("SELECT `value` FROM `" + defaultTableName + "` WHERE `name`='name2'"));
    Assert.assertEquals("{enc}valueC",
        dbHelper.queryForSingleValue("SELECT `value` FROM `" + defaultTableName + "` WHERE `name`='name3'"));
  }

}
