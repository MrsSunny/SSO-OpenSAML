package org.sms.core.id;

import org.junit.Test;
import base.BaseTest;

/**
 * @author Sunny
 */
public class IDFactoryTest extends BaseTest {


  @Test
  public void testGetIdString() {
    IDFactory idFactory = IDFactory.INSTANCE;
    long id = idFactory.getId("user_sequence");
    System.out.println(id);
  }
}
