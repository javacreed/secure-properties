/*
 * #%L
 * JavaCreed Secure Properties Bouncy Castle Encoder
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
package com.javacreed.api.secureproperties.bouncycastle;

import java.io.InputStream;
import java.io.OutputStream;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.TwofishEngine;
import org.bouncycastle.crypto.io.CipherInputStream;
import org.bouncycastle.crypto.io.CipherOutputStream;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

import com.javacreed.api.secureproperties.cipher.pbe.AbstractPbeCipherFactory;

/**
 */
public class TwoFishCipherFactory extends AbstractPbeCipherFactory {

  public TwoFishCipherFactory() {
    this("javacreed");
  }

  public TwoFishCipherFactory(final String key) {
    super(key);
  }

  @Override
  protected InputStream wrapInToCipheredInputStream(final InputStream in) throws Exception {
    final BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new TwofishEngine()));
    cipher.init(false, new KeyParameter(key.getBytes("UTF-8")));
    final CipherInputStream stream = new CipherInputStream(in, cipher);
    return stream;
  }

  @Override
  protected OutputStream wrapInToCipheredOutputStream(final OutputStream out) throws Exception {
    final BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new TwofishEngine()));
    cipher.init(true, new KeyParameter(key.getBytes("UTF-8")));
    final CipherOutputStream stream = new CipherOutputStream(out, cipher);
    return stream;
  }
}
