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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javacreed.api.secureproperties.utils.NumbersUtils;

/**
 * AES cipher factory. This class can be used to create AES based ciphers and allows for different configurations.
 *
 * The factory is not thread-safe as none of its properties as protected/guarded by any locks.
 */
@NotThreadSafe
public class AesCipherFactory extends AbstractPbeCipherFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(AesCipherFactory.class);

  /** */
  private static final String ALGORITHM = "AES";

  /** */
  private String factoryName = "PBKDF2WithHmacSHA1";

  /** */
  private byte[] salt = new byte[] { 28, 114, 71, 21, -14, -98, 7, 19 };

  /** */
  private int iterationCount = 1024;

  /** */
  private int keyLength = 128;

  /** */
  private String configuration = "AES/CBC/PKCS5Padding";

  /** */
  private byte[] iv = new byte[] { 48, -54, -23, -75, 110, 14, 7, 33, -44, -21, 17, 19, 23, 79, 1, 5 };

  /**
   *
   */
  public AesCipherFactory() {
    this("javacreed");
  }

  /**
   *
   * @param key
   * @throws NullPointerException
   */
  public AesCipherFactory(final String key) throws NullPointerException {
    super(key);
  }

  /**
   *
   * @return
   * @throws NoSuchPaddingException
   * @throws NoSuchAlgorithmException
   */
  private Cipher createCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
    final Cipher cipher = Cipher.getInstance(configuration);
    return cipher;
  }

  /**
   *
   * @return
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeySpecException
   */
  private SecretKeySpec createKeySpec() throws NoSuchAlgorithmException, InvalidKeySpecException {
    final SecretKeyFactory factory = SecretKeyFactory.getInstance(factoryName);
    final KeySpec keySpec = new PBEKeySpec(key.toCharArray(), salt, iterationCount, keyLength);
    final SecretKey tempSecretKey = factory.generateSecret(keySpec);
    final SecretKeySpec secretKeySpec = new SecretKeySpec(tempSecretKey.getEncoded(), AesCipherFactory.ALGORITHM);
    return secretKeySpec;
  }

  /**
   *
   * @param configuration
   * @throws NullPointerException
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
   * @param iterationCount
   */
  public void setIterationCount(final int iterationCount) {
    this.iterationCount = NumbersUtils.inRange(iterationCount, 1, Integer.MAX_VALUE, "iteration count");
  }

  /**
   *
   * @param iv
   * @throws NullPointerException
   */
  public void setIv(final byte[] iv) throws NullPointerException {
    this.iv = Arrays.copyOf(iv, iv.length);
  }

  /**
   *
   * @param keyLength
   * @throws IllegalArgumentException
   */
  public void setKeyLength(final int keyLength) throws IllegalArgumentException {
    // Check the maximum key length supported by this runtime
    try {
      final int supported = Cipher.getMaxAllowedKeyLength(AesCipherFactory.ALGORITHM);
      if (supported < keyLength) {
        AesCipherFactory.LOGGER.warn("This JRE does not support AES keys larger than {}", supported);
        AesCipherFactory.LOGGER
            .warn("Please download the Java Cryptography Extension (JCE) Unlimited Strength for your JRE");
        AesCipherFactory.LOGGER.warn("Copy all the jars files to '/jre/lib/security' folder of your JRE");
        throw new IllegalArgumentException("The given key length " + keyLength + " is larger than that supported "
            + supported);
      }
    } catch (final NoSuchAlgorithmException e) {
      AesCipherFactory.LOGGER.warn("Could not verify the maximum key length");
    }

    this.keyLength = NumbersUtils.oneOf(keyLength, "key length", 128, 192, 256);
  }

  /**
   *
   * @param salt
   * @throws NullPointerException
   */
  public void setSalt(final byte[] salt) throws NullPointerException {
    this.salt = Arrays.copyOf(salt, salt.length);
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
