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
package com.javacreed.api.secureproperties.cipher.pbe;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import net.jcip.annotations.NotThreadSafe;

/**
 * Triple DES cipher factory.
 *
 * This class is not thread safe as its state is not guarded by any locks.
 *
 * @author Albert Attard
 */
@NotThreadSafe
public class TripleDesCipherFactory extends AbstractPbeCipherFactory {

  /** The algorithm name */
  private static final String ALGORITHM = "DESede";

  /** The password-based encryption algorithm */
  private String factoryName = "PBEWithMD5AndDES";

  /** The setup configuration */
  private String configuration = "DESede/CBC/PKCS5Padding";

  /** The initial value */
  private byte[] iv = new byte[] { 48, -54, -23, -75, 110, 14, 7, 33 };

  /**
   * Creates and instance of this class with the default key
   */
  public TripleDesCipherFactory() {
    this("javacreedjavacreedjavacr");
  }

  /**
   * Creates an instance of this class with the given key
   *
   * @param key
   *          the cipher key (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given {@code key} is {@code null}
   */
  public TripleDesCipherFactory(final String key) throws NullPointerException {
    super(key);
  }

  /**
   * Creates an new cipher using the current configuration
   *
   * @return a new instance of the Cipher using the current configuration
   * @throws NoSuchPaddingException
   *           if configuration contains a padding scheme that is not available.
   * @throws NoSuchAlgorithmException
   *           if configuration is {@code null}, empty, in an invalid format, or if no Provider supports a CipherSpi
   *           implementation for the specified algorithm.
   */
  private Cipher createCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
    final Cipher cipher = Cipher.getInstance(configuration);
    return cipher;
  }

  /**
   * Creates the secret key specification for this algorithm
   *
   * @return
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeySpecException
   */
  private SecretKey createKeySpec() throws NoSuchAlgorithmException, InvalidKeySpecException {
    final SecretKeyFactory factory = SecretKeyFactory.getInstance(factoryName);
    final KeySpec keySpec = new PBEKeySpec(key.toCharArray());
    final SecretKey tempSecretKey = factory.generateSecret(keySpec);
    final SecretKeySpec secretKeySpec = new SecretKeySpec(tempSecretKey.getEncoded(), TripleDesCipherFactory.ALGORITHM);
    return secretKeySpec;
  }

  /**
   * Change the cipher configuration
   *
   * @param configuration
   *          the new configuration to be used (which cannot be {@code null})
   * @throws NullPointerException
   *           if the given configuration is {@code null}
   */
  public void setConfiguration(final String configuration) throws NullPointerException {
    this.configuration = Objects.requireNonNull(configuration);
  }

  /**
   *
   * @param factoryName
   * @throws NullPointerException
   */
  public void setFactoryName(final String factoryName) throws NullPointerException {
    this.factoryName = Objects.requireNonNull(factoryName);
  }

  /**
   *
   * @param iv
   * @throws NullPointerException
   */
  public void setIv(final byte[] iv) throws NullPointerException {
    this.iv = Arrays.copyOf(iv, iv.length);
  }

  @Override
  protected InputStream wrapInToCipheredInputStream(final InputStream in) throws Exception {
    final Cipher cipher = createCipher();
    cipher.init(Cipher.DECRYPT_MODE, createKeySpec(), new IvParameterSpec(iv));
    return new CipherInputStream(in, cipher);
  }

  @Override
  protected OutputStream wrapInToCipheredOutputStream(final OutputStream out) throws Exception {
    final Cipher cipher = createCipher();
    cipher.init(Cipher.ENCRYPT_MODE, createKeySpec(), new IvParameterSpec(iv));
    return new CipherOutputStream(out, cipher);
  }
}
