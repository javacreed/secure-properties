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
package com.javacreed.api.secureproperties.decoder;

import net.jcip.annotations.NotThreadSafe;

import com.javacreed.api.secureproperties.adapter.AbstractCipherBase;
import com.javacreed.api.secureproperties.cipher.CipherFactory;
import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.utils.CipherUtils;

/**
 *
 */
@NotThreadSafe
public class CipherDecoder extends AbstractCipherBase implements StringDecoder {

  /**
   *
   */
  public CipherDecoder() {
    super();
  }

  /**
   * @param cipherFactory
   * @throws NullPointerException
   */
  public CipherDecoder(final CipherFactory cipherFactory) throws NullPointerException {
    super(cipherFactory);
  }

  /**
   *
   */
  public CipherDecoder(final String key) {
    super(key);
  }

  @Override
  public String decode(final String encodedText) throws EncoderException {
    return CipherUtils.decode(encodedText, cipherFactory);
  }

}
