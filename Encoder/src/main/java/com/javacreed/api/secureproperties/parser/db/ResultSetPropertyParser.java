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
package com.javacreed.api.secureproperties.parser.db;

import java.sql.ResultSet;
import java.util.Objects;

import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.parser.AbstractPropertiesParser;

public class ResultSetPropertyParser extends AbstractPropertiesParser {

  private final ResultSet resultSet;
  private ResultSetExtractor extractor = new DefaultResultSetExtractor();

  public ResultSetPropertyParser(final ResultSet resultSet) {
    this.resultSet = Objects.requireNonNull(resultSet);
  }

  @Override
  protected PropertyEntry readNext() throws Exception {
    if (resultSet.next()) {
      return extractor.extract(resultSet);
    }

    return null;
  }

  public void setExtractor(final ResultSetExtractor extractor) throws NullPointerException {
    this.extractor = Objects.requireNonNull(extractor);
  }

}
