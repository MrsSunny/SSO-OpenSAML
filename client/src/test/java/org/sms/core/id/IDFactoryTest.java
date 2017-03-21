package org.sms.core.id;

import org.junit.Assert;
import org.junit.Test;
import base.BaseTest;

/**
 * @author Sunny
 */
public class IDFactoryTest extends BaseTest {

  @Test
  public void testGetIdString() {
    IDFactory idFactory = IDFactory.INSTANCE;
    Long id = idFactory.getId("user_sequence");
    Assert.assertNotNull(id);
  }
}
