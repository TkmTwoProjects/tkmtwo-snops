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
public final class InstanceTest {

  
  @Test
  public void test0000Expected() {
    Instance eyeOne = new Instance("myinstance");
    assertEquals("myinstance", eyeOne.getName());
    assertEquals("service-now.com", eyeOne.getDomain());
    assertEquals("myinstance.service-now.com", eyeOne.getFqdn());

    Instance eyeTwo = new Instance("myinstance", "service-now.com");
    assertEquals("myinstance", eyeTwo.getName());
    assertEquals("service-now.com", eyeTwo.getDomain());
    assertEquals("myinstance.service-now.com", eyeTwo.getFqdn());

    assertEquals(eyeOne, eyeTwo);
    
  }
  
  
  
  @Test
  public void test0010Equality() {
    Instance eyeOne = new Instance("instance");
    Instance eyeTwo = new Instance("instance");

    Instance eyeThree = new Instance("three");

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
    Instance eyeOne = new Instance("host.domain.com");
    Instance eyeTwo = new Instance("host", "domain.com");

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
    Instance eyeOne = new Instance("host.domain.com");
    Instance eyeTwo = new Instance("..host...domain...com...");

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
    Instance eyeOne = new Instance("host.domain.com");
    Instance eyeTwo = new Instance(" . . host . . . domain. . .com. . . ");

    assertEquals("host", eyeOne.getName());
    assertEquals("domain.com", eyeOne.getDomain());
    assertEquals("host.domain.com", eyeOne.getFqdn());

    assertEquals("host", eyeTwo.getName());
    assertEquals("domain.com", eyeTwo.getDomain());
    assertEquals("host.domain.com", eyeTwo.getFqdn());

    assertEquals(eyeOne, eyeTwo);
  }

  
  
}
