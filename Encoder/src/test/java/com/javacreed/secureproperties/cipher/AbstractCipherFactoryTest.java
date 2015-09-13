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
package com.javacreed.secureproperties.cipher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import com.javacreed.api.secureproperties.cipher.AbstractCipherFactory;
import com.javacreed.api.secureproperties.encoder.EncoderException;

/**
 */
public class AbstractCipherFactoryTest {

  private final AbstractCipherFactory factory = new AbstractCipherFactory() {
    @Override
    protected InputStream wrapInToCipheredInputStream(final InputStream in) throws Exception {
      throw new Exception();
    }

    @Override
    protected OutputStream wrapInToCipheredOutputStream(final OutputStream out) throws Exception {
      throw new Exception();
    }
  };

  @Test(expected = EncoderException.class)
  public void testDecoder() {
    factory.createDecoder(new ByteArrayInputStream(new byte[0]));
  }

  @Test(expected = EncoderException.class)
  public void testEncoder() {
    factory.createEncoder(new ByteArrayOutputStream());
  }
}
