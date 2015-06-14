package com.google.common.base;


//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceNowConditionsTest {
  
  private static final String[] sysIdStrings = new String[] {
    " aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ",
    "\taaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\t",
    "\naaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n",

    "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
    "cccccccccccccccccccccccccccccccc",
    "dddddddddddddddddddddddddddddddd",
    "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
    "ffffffffffffffffffffffffffffffff",
    "00000000000000000000000000000000",
    "11111111111111111111111111111111",
    "22222222222222222222222222222222",
    "33333333333333333333333333333333",
    "44444444444444444444444444444444",
    "55555555555555555555555555555555",
    "66666666666666666666666666666666",
    "77777777777777777777777777777777",
    "88888888888888888888888888888888",
    "99999999999999999999999999999999"
  };
  
  private static final String[] nonSysIdStrings = new String[] {
    "one two",
    "one two ",
    " one two",
    " one two ",
    "\t one two \t",
    (String) null
  };
  
  
  
  
  @Test
  public void test00010CheckSysId() {
    for (String s : sysIdStrings) {
      String sysId = ServiceNowConditions.checkSysId(s);
      assertTrue("Should have accepted " + s, ServiceNowStrings.isaSysId(sysId));
    }
  }

  
  @Test
  public void test00020CheckNonSysId() {
    for (String s : nonSysIdStrings) {
      try {
        String sysId = ServiceNowConditions.checkSysId(s);
        assertTrue(sysId != null);
        fail("Should not have accepted " + s);
      } catch (IllegalArgumentException iae) {
        ;
      }
    }
  }
  
  
}
