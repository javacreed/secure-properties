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
package com.javacreed.secureproperties.utils;

import java.io.IOException;
import java.io.OutputStream;

import org.easymock.EasyMock;
import org.junit.Test;

import com.javacreed.api.secureproperties.cipher.CipherFactory;
import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.utils.CipherUtils;

/**
 * Tests the error handling of the {@link CipherUtils} class
 *
 * @author Albert Attard
 */
public class CipherUtilsTest {

  /**
   * Tests the decode method with {@code null}s
   */
  @Test(expected = EncoderException.class)
  public void testDecodeWithNulls() {
    CipherUtils.decode(null, null);
  }

  /**
   *
   * @throws IOException
   */
  @Test(expected = EncoderException.class)
  public void testEncoder() throws IOException {
    final CipherFactory cipherFactory = EasyMock.createMock(CipherFactory.class);
    final OutputStream outputStream = EasyMock.createMock(OutputStream.class);

    EasyMock.expect(cipherFactory.createEncoder(EasyMock.anyObject(OutputStream.class))).andReturn(outputStream);
    outputStream.write(EasyMock.anyObject(byte[].class));
    EasyMock.expectLastCall().andThrow(new IOException("Test"));
    EasyMock.replay(cipherFactory, outputStream);

    CipherUtils.encode("", cipherFactory);

    EasyMock.verify(cipherFactory, outputStream);
  }

  /**
   * Tests the encode method with {@code null}s
   */
  @Test(expected = EncoderException.class)
  public void testEncodeWithNulls() {
    CipherUtils.encode(null, null);
  }
}
