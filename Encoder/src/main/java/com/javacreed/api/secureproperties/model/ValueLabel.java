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

/**
 * Defines the labels used to distinguish the plain text values that needs to be encoded and the properties that are
 * already encoded
 *
 * @author Albert Attard
 */
public interface ValueLabel {

  /**
   * Returns the label that indicates that the value is encoded
   *
   * @return the label that indicates that the value is encoded (which will not be {@code null})
   */
  String getEncodedLabel();

  /**
   * Returns the label that indicates that the value is in plain text and needs encoding
   *
   * @return the label that indicates that the value is in plain text and needs encoding (which will not be {@code null}
   *         )
   */
  String getPlainTextLabel();

}
