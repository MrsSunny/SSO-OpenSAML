package org.sms.project.encrypt.des;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @author Sunny
 */
public enum DESCoder {

  INSTANCE;

  public static final String CIPHER_TYPE = "DES";

  /**
   * 加密
   */
  public static byte[] encrypt(String key, String content) {
    try {
      SecureRandom random = new SecureRandom();
      DESKeySpec desKey = new DESKeySpec(key.getBytes());
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CIPHER_TYPE);
      SecretKey securekey = keyFactory.generateSecret(desKey);
      Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
      cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
      byte[] result = cipher.doFinal(content.getBytes());
      return result;
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 解密
   */
  public static String decrypt(String key, byte[] content) {
    SecureRandom random = new SecureRandom();
    try {
      DESKeySpec desKey = new DESKeySpec(key.getBytes());
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CIPHER_TYPE);
      SecretKey securekey = keyFactory.generateSecret(desKey);
      Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
      cipher.init(Cipher.DECRYPT_MODE, securekey, random);
      byte[] result = cipher.doFinal(content);
      return new String(result);
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (InvalidKeySpecException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    } catch (BadPaddingException e) {
      e.printStackTrace();
    }
    return null;
  }
}