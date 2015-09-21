/*
 * #%L
 * JavaCreed Secure Properties Examples
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
package com.javacreed.api.secureproperties.examples.example1;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.NameNullValuePropertyEntry;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.db.ResultSetExtractor;

/**
 *
 * @author Albert Attard
 */
public class CustomResultSetExtractor implements ResultSetExtractor {

  @Override
  public PropertyEntry extract(final ResultSet resultSet) throws SQLException {
    final String name = resultSet.getString("name");
    final String value = resultSet.getString("value");

    if (value == null) {
      return new NameNullValuePropertyEntry(name);
    }

    final int encode = resultSet.getInt("encoded");
    switch (encode) {
    case 1:
      return new EncodedNameValuePropertyEntry(name, value);
    case 2:
      return new PlainTextNameValuePropertyEntry(name, value);
    }

    return new NameValuePropertyEntry(name, value);
  }

}
