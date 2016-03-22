package org.sms.core.hash.impl;

import java.nio.charset.Charset;

import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;

/**
 * @author Sunny
 */
public class HashFunctionImpl implements HashFunction {

  @Override
  public Hasher newHasher() {
    return null;
  }

  @Override
  public Hasher newHasher(int expectedInputSize) {
    return null;
  }

  @Override
  public HashCode hashInt(int input) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public HashCode hashLong(long input) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public HashCode hashBytes(byte[] input) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public HashCode hashBytes(byte[] input, int off, int len) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public HashCode hashUnencodedChars(CharSequence input) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public HashCode hashString(CharSequence input, Charset charset) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> HashCode hashObject(T instance, Funnel<? super T> funnel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int bits() {
    // TODO Auto-generated method stub
    return 0;
  }

}
