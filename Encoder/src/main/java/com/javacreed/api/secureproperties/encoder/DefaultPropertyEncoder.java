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
package com.javacreed.api.secureproperties.encoder;

import java.util.Objects;

import com.javacreed.api.secureproperties.cipher.CipherFactory;
import com.javacreed.api.secureproperties.cipher.pbe.AesCipherFactory;
import com.javacreed.api.secureproperties.model.EncodedNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PlainNameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;

/**
 */
public class DefaultPropertyEncoder implements PropertyEncoder {

  private Formatter formatter = new DefaultFormatter();

  private final StringEncoder stringEncoder;

  public DefaultPropertyEncoder() {
    this("javacreed");
  }

  public DefaultPropertyEncoder(final CipherFactory factory) {
    this(new DefaultFormatter(), new CipherStringEncoder(factory));
  }

  public DefaultPropertyEncoder(final Formatter formatter, final StringEncoder stringEncoder) {
    this.formatter = Objects.requireNonNull(formatter);
    this.stringEncoder = Objects.requireNonNull(stringEncoder);
  }

  public DefaultPropertyEncoder(final String key) {
    stringEncoder = new CipherStringEncoder(new AesCipherFactory(key));
  }

  @Override
  public PropertyEntry encode(final PropertyEntry entry) throws EncoderException {
    if (entry instanceof PlainNameValuePropertyEntry) {
      final PlainNameValuePropertyEntry pnvpEntry = (PlainNameValuePropertyEntry) entry;
      final String formatted = formatter.format(pnvpEntry.getName(), pnvpEntry.getValue());
      final String encoded = "{enc}" + stringEncoder.encode(formatted);
      return new EncodedNameValuePropertyEntry(pnvpEntry.getName(), encoded);
    }

    return entry;
  }

  public void setFormatter(final Formatter formatter) throws NullPointerException {
    this.formatter = Objects.requireNonNull(formatter);
  }
}
