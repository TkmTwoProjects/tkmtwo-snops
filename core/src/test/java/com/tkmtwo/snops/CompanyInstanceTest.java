package com.tkmtwo.snops;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


/**
 *
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class CompanyInstanceTest {
  
  private static final String cid0 = "00000000000000000000000000000000";

  
  @Test
  public void test0000Expected() {
    CompanyInstance eyeOne = new CompanyInstance("myinstance", cid0);
    assertEquals("myinstance", eyeOne.getName());
    assertEquals("service-now.com", eyeOne.getDomain());
    assertEquals("myinstance.service-now.com", eyeOne.getFqdn());
    assertEquals(cid0, eyeOne.getCompanySysId());

    CompanyInstance eyeTwo = new CompanyInstance("myinstance", "service-now.com", cid0);
    assertEquals("myinstance", eyeTwo.getName());
    assertEquals("service-now.com", eyeTwo.getDomain());
    assertEquals("myinstance.service-now.com", eyeTwo.getFqdn());
    assertEquals(cid0, eyeOne.getCompanySysId());

    assertEquals(eyeOne, eyeTwo);
    
  }
  
  
  
  @Test
  public void test0010Equality() {
    CompanyInstance eyeOne = new CompanyInstance("instance", cid0);
    CompanyInstance eyeTwo = new CompanyInstance("instance", cid0);

    CompanyInstance eyeThree = new CompanyInstance("three", cid0);

    assertTrue(eyeOne != eyeTwo);
    assertTrue(eyeOne.equals(eyeTwo));
    assertTrue(eyeTwo.equals(eyeOne));
    assertEquals(eyeOne, eyeTwo);

    assertTrue(eyeOne != eyeThree);
    assertFalse(eyeOne.equals(eyeThree));
    assertFalse(eyeThree.equals(eyeOne));
  }

  @Test
  public void test0020Splits() {
    CompanyInstance eyeOne = new CompanyInstance("host.domain.com", cid0);
    CompanyInstance eyeTwo = new CompanyInstance("host", "domain.com", cid0);

    assertEquals("host", eyeOne.getName());
    assertEquals("domain.com", eyeOne.getDomain());
    assertEquals("host.domain.com", eyeOne.getFqdn());
    
    assertEquals("host", eyeTwo.getName());
    assertEquals("domain.com", eyeTwo.getDomain());
    assertEquals("host.domain.com", eyeTwo.getFqdn());
    
    assertEquals(eyeOne, eyeTwo);
  }

  @Test
  public void test0020SplitsWithExtraDots() {
    CompanyInstance eyeOne = new CompanyInstance("host.domain.com", cid0);
    CompanyInstance eyeTwo = new CompanyInstance("..host...domain...com...", cid0);
    
    assertEquals("host", eyeOne.getName());
    assertEquals("domain.com", eyeOne.getDomain());
    assertEquals("host.domain.com", eyeOne.getFqdn());
    
    assertEquals("host", eyeTwo.getName());
    assertEquals("domain.com", eyeTwo.getDomain());
    assertEquals("host.domain.com", eyeTwo.getFqdn());
    
    assertEquals(eyeOne, eyeTwo);
  }

  @Test
  public void test0020SplitsWithExtraDotsAndSpaces() {
    CompanyInstance eyeOne = new CompanyInstance("host.domain.com", cid0);
    CompanyInstance eyeTwo = new CompanyInstance(" . . host . . . domain. . .com. . . ", cid0);
    
    assertEquals("host", eyeOne.getName());
    assertEquals("domain.com", eyeOne.getDomain());
    assertEquals("host.domain.com", eyeOne.getFqdn());
    
    assertEquals("host", eyeTwo.getName());
    assertEquals("domain.com", eyeTwo.getDomain());
    assertEquals("host.domain.com", eyeTwo.getFqdn());
    
    assertEquals(eyeOne, eyeTwo);
  }
  
  
  
}
