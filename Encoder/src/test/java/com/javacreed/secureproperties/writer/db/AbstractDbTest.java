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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

import com.javacreed.secureproperties.utils.DbHelper;

/**
 */
public class AbstractDbTest {

  /** */
  protected DbHelper dbHelper;

  /** */
  protected final String defaultTableName = "test_properties";

  /**
   *
   */
  @After
  public void destroy() {
    dbHelper.close();
  }

  /**
   *
   * @throws SQLException
   */
  @Before
  public void init() throws SQLException {
    dbHelper = DbHelper.create();
    dbHelper.execute("DROP TABLE IF EXISTS `" + defaultTableName + "`");
    dbHelper.execute("CREATE TABLE `" + defaultTableName
        + "` (`name` VARCHAR(64) NOT NULL, `value` VARCHAR(255) NOT NULL, PRIMARY KEY(`name`))");
  }

  /**
   *
   * @param name
   * @param value
   * @throws SQLException
   */
  protected void insertData(final String name, final String value) throws SQLException {
    final String query = "INSERT INTO `" + defaultTableName + "` VALUES (?, ?)";
    try (Connection connection = dbHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, name);
      statement.setString(2, value);
      statement.execute();
    }
  }

}
