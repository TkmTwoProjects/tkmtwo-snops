package com.google.common.base;


//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceNowStringsTest {
  
  private static final String[] sysIdStrings = new String[] {
    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
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
    " aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ",
    "\taaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\t",
    "\naaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n",
    "one two",
    "one two ",
    " one two",
    " one two ",
    "\t one two \t",
    (String) null
  };
  
  
  
  
  @Test
  public void test00010SysId() {
    for (String s : sysIdStrings) {
      assertTrue("Should have accepted " + s, ServiceNowStrings.isaSysId(s));
    }
  }
  
  
  @Test
  public void test00020NonSysId() {
    for (String s: nonSysIdStrings) {
      assertTrue("Should not have accepted " + s, !ServiceNowStrings.isaSysId(s));
    }
  }

}
