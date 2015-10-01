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

import org.junit.Test;

import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.writer.db.DbPropertyEntryWriter;

/**
 * We try to update a field which does not exists. This will throw an {@link EncoderException}
 *
 * @author Albert Attard
 */
public class DbPropertyEntryWriterFailTest extends AbstractDbTest {

  /**
   * We try to update a field which does not exists. This will throw an {@link EncoderException}
   *
   * @throws SQLException
   *           if an SQL error occurs while testing
   */
  @Test(expected = EncoderException.class)
  public void test() throws SQLException {
    dbHelper.execute("INSERT INTO `test_properties` VALUES ('name1', 'value1')");

    final DbPropertyEntryWriter writer = new DbPropertyEntryWriter(dbHelper.getDataSource(), "test_properties");
    try {
      writer.begin();
      writer.write(new NameValuePropertyEntry("name1", "valueA"));
      writer.write(new NameValuePropertyEntry("name2", "valueB"));
      writer.commit();
    } catch (final EncoderException e) {
      writer.failed(e);
      throw e;
    }
  }
}
