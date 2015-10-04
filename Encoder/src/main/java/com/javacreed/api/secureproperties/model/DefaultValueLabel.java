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
package com.javacreed.api.secureproperties.model;

import java.util.Objects;

import net.jcip.annotations.Immutable;

/**
 *
 * @author Albert Attard
 */
@Immutable
public class DefaultValueLabel implements ValueLabel {

  /** */
  private final String plainTextLabel;

  /** */
  private final String encodedLabel;

  /**
   *
   */
  public DefaultValueLabel() {
    this("{pln}", "{enc}");
  }

  /**
   *
   * @param plainTextLabel
   * @param encodedLabel
   * @throws NullPointerException
   *           if any of the given properties is {@code null}
   */
  public DefaultValueLabel(final String plainTextLabel, final String encodedLabel) throws NullPointerException {
    this.plainTextLabel = Objects.requireNonNull(plainTextLabel);
    this.encodedLabel = Objects.requireNonNull(encodedLabel);

    if (plainTextLabel.equals(encodedLabel)) {
      throw new IllegalArgumentException("The plain test label cannot be the same as the encoded label");
    }
  }

  @Override
  public String getEncodedLabel() {
    return encodedLabel;
  }

  @Override
  public String getPlainTextLabel() {
    return plainTextLabel;
  }

  @Override
  public String toString() {
    return plainTextLabel + "/" + encodedLabel;
  }

}
