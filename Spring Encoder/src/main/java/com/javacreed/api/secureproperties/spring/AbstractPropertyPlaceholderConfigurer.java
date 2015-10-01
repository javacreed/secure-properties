/*
 * #%L
 * JavaCreed Secure Properties Spring Encoder
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
package com.javacreed.api.secureproperties.spring;

import java.util.Objects;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.javacreed.api.secureproperties.cipher.CipherFactory;
import com.javacreed.api.secureproperties.decoder.DefaultPropertyDecoder;
import com.javacreed.api.secureproperties.decoder.PropertyDecoder;
import com.javacreed.api.secureproperties.encoder.DefaultPropertiesEncoder;
import com.javacreed.api.secureproperties.encoder.PropertiesEncoder;

public abstract class AbstractPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

  protected PropertyDecoder propertyDecoder = new DefaultPropertyDecoder();

  protected PropertiesEncoder encoder = new DefaultPropertiesEncoder();

  protected boolean isEncrypted(final String value) {
    // TODO: need to use the same value label as the rest
    if (value != null && value.startsWith("{enc}")) {
      return true;
    }

    return false;
  }

  public void setCipherFactory(final CipherFactory cipherFactory) {
    encoder = new DefaultPropertiesEncoder(Objects.requireNonNull(cipherFactory));
    propertyDecoder = new DefaultPropertyDecoder(Objects.requireNonNull(cipherFactory));
  }

  public void setEncoder(final PropertiesEncoder encoder) throws NullPointerException {
    this.encoder = Objects.requireNonNull(encoder);
  }

  public void setKey(final String key) {
    encoder = new DefaultPropertiesEncoder(key);
    propertyDecoder = new DefaultPropertyDecoder(key);
  }

  public void setPropertyDecoder(final PropertyDecoder propertyDecoder) {
    this.propertyDecoder = Objects.requireNonNull(propertyDecoder);
  }

}
