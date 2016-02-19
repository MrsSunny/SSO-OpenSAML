package org.sms.project.encrypt.rsa;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.codec.binary.Base64;

/**
 * @author zhenxing.Liu
 */
public enum RSACoder {

  INSTANCE;

  public static final String CIPHER_TYPE = "RSA";

  /**
   * 获取公钥
   */
  public RSAPublicKey getPublicKey(String publicKey) throws Exception {

    if (null == publicKey)
      throw new RuntimeException("公钥数据为空");
    try {
      byte[] buffer = Base64.decodeBase64(publicKey);
      KeyFactory keyFactory = KeyFactory.getInstance(CIPHER_TYPE);
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
      return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    } catch (NoSuchAlgorithmException e) {
      throw new Exception("算法名称错误");
    } catch (InvalidKeySpecException e) {
      throw new Exception("非法公钥数据");
    }
  }

  /**
   * 获取私钥
   */
  public RSAPrivateKey getPrivateKey(String privateKeyStr) throws Exception {
    try {
      byte[] buffer = Base64.decodeBase64(privateKeyStr);
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
      KeyFactory keyFactory = KeyFactory.getInstance(CIPHER_TYPE);
      return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    } catch (NoSuchAlgorithmException e) {
      throw new Exception("算法名称错误");
    } catch (InvalidKeySpecException e) {
      throw new Exception("非法私钥数据");
    }
  }

  /**
   * 公钥加密
   */
  public byte[] encryptByPublicKey(RSAPublicKey publicKey, byte[] plainData) throws Exception {
    if (publicKey == null) {
      throw new Exception("加密公钥为空, 请设置");
    }
    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance(CIPHER_TYPE);
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] output = cipher.doFinal(plainData);
      return output;
    } catch (NoSuchAlgorithmException e) {
      throw new Exception("无此加密算法");
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
      return null;
    } catch (InvalidKeyException e) {
      throw new Exception("加密公钥非法,请检查");
    } catch (IllegalBlockSizeException e) {
      throw new Exception("明文长度非法");
    } catch (BadPaddingException e) {
      throw new Exception("明文数据已损坏");
    }
  }

  /**
   * 公钥加密
   */
  public byte[] encryptByPublicKey(String publicKey, byte[] plainData) throws Exception {
    RSAPublicKey key = this.getPublicKey(publicKey);
    return this.encryptByPublicKey(key, plainData);
  }

  /**
   * 私钥加密
   */
  public byte[] encrypt(RSAPrivateKey privateKey, byte[] plainData) throws Exception {
    if (privateKey == null) {
      throw new Exception("加密私钥为空, 请设置");
    }
    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance(CIPHER_TYPE);
      cipher.init(Cipher.ENCRYPT_MODE, privateKey);
      byte[] output = cipher.doFinal(plainData);
      return output;
    } catch (NoSuchAlgorithmException e) {
      throw new Exception("无此加密算法");
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
      return null;
    } catch (InvalidKeyException e) {
      throw new Exception("加密私钥非法,请检查");
    } catch (IllegalBlockSizeException e) {
      throw new Exception("明文长度非法");
    } catch (BadPaddingException e) {
      throw new Exception("明文数据已损坏");
    }
  }

  /**
   * 私钥加密
   */
  public byte[] encryptByPrivateKey(String privateKey, byte[] plainData) throws Exception {
    RSAPrivateKey key = this.getPrivateKey(privateKey);
    return this.encrypt(key, plainData);
  }

  /**
   * 私钥解密
   */
  public byte[] decryptByPrivateKey(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
    if (privateKey == null) {
      throw new Exception("解密私钥为空, 请设置");
    }
    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance(CIPHER_TYPE);
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      byte[] output = cipher.doFinal(cipherData);
      return output;
    } catch (NoSuchAlgorithmException e) {
      throw new Exception("无此解密算法");
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
      return null;
    } catch (InvalidKeyException e) {
      throw new Exception("解密私钥非法,请检查");
    } catch (IllegalBlockSizeException e) {
      throw new Exception("密文长度非法");
    } catch (BadPaddingException e) {
      throw new Exception("密文数据已损坏");
    }
  }

  /**
   * 私钥解密
   */
  public byte[] decryptByPrivateKey(String privateKey, byte[] cipherData) throws Exception {
    RSAPrivateKey key = this.getPrivateKey(privateKey);
    return this.decryptByPrivateKey(key, cipherData);
  }

  /**
   * 公钥解密
   */
  public byte[] decryptByPublicKey(String publicKey, byte[] cipherData) throws Exception {
    RSAPublicKey key = this.getPublicKey(publicKey);
    return this.decryptByPublicKey(key, cipherData);
  }

  /**
   * 公钥解密过程
   */
  public byte[] decryptByPublicKey(RSAPublicKey publicKey, byte[] cipherData) throws Exception {
    if (publicKey == null) {
      throw new Exception("解密公钥为空, 请设置");
    }
    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance(CIPHER_TYPE);
      cipher.init(Cipher.DECRYPT_MODE, publicKey);
      byte[] output = cipher.doFinal(cipherData);
      return output;
    } catch (NoSuchAlgorithmException e) {
      throw new Exception("无此解密算法");
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
      return null;
    } catch (InvalidKeyException e) {
      throw new Exception("解密公钥非法,请检查");
    } catch (IllegalBlockSizeException e) {
      throw new Exception("密文长度非法");
    } catch (BadPaddingException e) {
      throw new Exception("密文数据已损坏");
    }
  }
}