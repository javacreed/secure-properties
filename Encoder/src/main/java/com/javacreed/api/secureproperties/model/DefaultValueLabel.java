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
  private final String plainTextPrefix;

  /** */
  private final String encodedPrefix;

  /**
   *
   */
  public DefaultValueLabel() {
    this("{pln}", "{enc}");
  }

  /**
   *
   * @param plainTextPrefix
   * @param encodedPrefix
   * @throws NullPointerException
   *           if any of the given properties is {@code null}
   */
  public DefaultValueLabel(final String plainTextPrefix, final String encodedPrefix) throws NullPointerException {
    this.plainTextPrefix = Objects.requireNonNull(plainTextPrefix);
    this.encodedPrefix = Objects.requireNonNull(encodedPrefix);
  }

  @Override
  public String getEncodedLabel() {
    return encodedPrefix;
  }

  @Override
  public String getPlainTextLabel() {
    return plainTextPrefix;
  }

  @Override
  public String toString() {
    return plainTextPrefix + "/" + encodedPrefix;
  }

}
