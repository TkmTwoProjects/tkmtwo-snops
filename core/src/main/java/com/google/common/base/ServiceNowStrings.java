package com.google.common.base;





/**
 * Additional check conditions based on same style as Guava's <code>Precondition</code>s.
 *
 *
 */
public class ServiceNowStrings
  extends TkmTwoStrings {
  
  public static final CharMatcher SYSID_MATCHER = CharMatcher.anyOf("abcdef0123456789");


  public static boolean isaSysId(String s) {
    if (s == null) { return false; }
    return s.length() == 32 && SYSID_MATCHER.matchesAllOf(s);
  }
  
  
}
