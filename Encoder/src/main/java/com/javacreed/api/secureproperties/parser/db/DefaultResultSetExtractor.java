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
import java.sql.SQLException;
import java.util.Objects;

import net.jcip.annotations.Immutable;

import com.javacreed.api.secureproperties.model.DefaultValueLabel;
import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.NameNullValuePropertyEntry;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PlainTextNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;
import com.javacreed.api.secureproperties.model.ValueLabel;

/**
 * The default result set extractor implementation
 *
 * @author Albert Attard
 */
@Immutable
public class DefaultResultSetExtractor implements ResultSetExtractor {

  /** The name of the column where the property name is */
  private final String nameColumnName;

  /** The name of the column where the property value is */
  private final String valueColumnName;

  /** The values labels */
  private final ValueLabel valueLabel;

  /**
   * Creates and instance of this class using the defaults.
   *
   * <table>
   * <tr>
   * <td>Property Name Column</td>
   * <td>name</td>
   * </tr>
   * <tr>
   * <td>Property Value Column</td>
   * <td>value</td>
   * </tr>
   * <tr>
   * <td>Plain Text Label</td>
   * <td>{pln}</td>
   * </tr>
   * <tr>
   * <td>Encoded Label</td>
   * <td>{enc}</td>
   * </tr>
   * </table>
   */
  public DefaultResultSetExtractor() {
    this("name", "value");
  }

  /**
   * Creates an instance of this class using the default value label.
   *
   * @param nameColumnName
   *          the name of the column where the property name is (which cannot be {@code null})
   * @param valueColumnName
   *          the name of the column where the property value is (which cannot be {@code null})
   * @throws NullPointerException
   *           if any of the parameters are {@code null}
   * @see DefaultValueLabel
   */
  public DefaultResultSetExtractor(final String nameColumnName, final String valueColumnName)
      throws NullPointerException {
    this(nameColumnName, valueColumnName, new DefaultValueLabel());
  }

  /**
   * Creates an instance of this class using the default value label with the given labels.
   *
   * @param nameColumnName
   *          the name of the column where the property name is (which cannot be {@code null})
   * @param valueColumnName
   *          the name of the column where the property value is (which cannot be {@code null})
   * @param plainTextLabel
   *          the label used to indicate that value is in plain text form and needs to be encoded (which cannot be
   *          {@code null})
   * @param encodedLabel
   *          the label used to indicate that value is encoded (which cannot be {@code null})
   * @throws NullPointerException
   *           if any of the parameters are {@code null}
   * @see DefaultValueLabel
   */
  public DefaultResultSetExtractor(final String nameColumnName, final String valueColumnName,
      final String plainTextLabel, final String encodedLabel) throws NullPointerException {
    this(nameColumnName, valueColumnName, new DefaultValueLabel(plainTextLabel, encodedLabel));
  }

  /**
   *
   * @param nameColumnName
   *          the name of the column where the property name is (which cannot be {@code null})
   * @param valueColumnName
   *          the name of the column where the property value is (which cannot be {@code null})
   * @param valueLabel
   *          the value label (which cannot be {@code null})
   * @throws NullPointerException
   *           if any of the parameters are {@code null}
   */
  public DefaultResultSetExtractor(final String nameColumnName, final String valueColumnName,
      final ValueLabel valueLabel) throws NullPointerException {
    this.nameColumnName = Objects.requireNonNull(nameColumnName);
    this.valueColumnName = Objects.requireNonNull(valueColumnName);
    this.valueLabel = Objects.requireNonNull(valueLabel);
  }

  @Override
  public PropertyEntry extract(final ResultSet resultSet) throws SQLException {
    final String name = resultSet.getString(nameColumnName);
    final String value = resultSet.getString(valueColumnName);

    if (value == null) {
      return new NameNullValuePropertyEntry(name);
    }

    if (value.startsWith(valueLabel.getEncodedLabel())) {
      return new EncodedNameValuePropertyEntry(name, value.substring(valueLabel.getEncodedLabel().length()));
    }

    if (value.startsWith(valueLabel.getPlainTextLabel())) {
      return new PlainTextNameValuePropertyEntry(name, value.substring(valueLabel.getPlainTextLabel().length()));
    }

    return new NameValuePropertyEntry(name, value);
  }
}
