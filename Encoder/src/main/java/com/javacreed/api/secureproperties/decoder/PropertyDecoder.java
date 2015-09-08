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

import com.javacreed.api.secureproperties.encoder.EncoderException;
import com.javacreed.api.secureproperties.model.NameValuePropertyEntry;
import com.javacreed.api.secureproperties.model.PropertyEntry;

/**
 */
public interface PropertyDecoder {

  /**
   * TODO: should we assume that the decoder will always accept and return a {@link NameValuePropertyEntry}? The other
   * options are comments which I do not think they will need encoding. We are casting the return value at the moment
   * which is not a good idea.
   *
   * @param entry
   * @return
   * @throws EncoderException
   * @throws NullPointerException
   */
  public PropertyEntry decode(PropertyEntry entry) throws EncoderException, NullPointerException;

}
