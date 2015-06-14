package com.google.common.base;





/**
 * Additional check conditions based on same style as Guava's <code>Precondition</code>s.
 *
 *
 */
public class ServiceNowConditions
  extends TkmTwoConditions {
  
  
  public static String checkSysId(String s) {
    String ts = ServiceNowStrings.blankToEmpty(s);
    if (ServiceNowStrings.isaSysId(ts)) { return ts; }
    throw new IllegalArgumentException(String.format("Value '%s' is not a valid sys_id.", s));
  }
  public static String checkSysId(String s, Object msg) {
    String ts = ServiceNowStrings.blankToEmpty(s);
    if (ServiceNowStrings.isaSysId(ts)) { return ts; }
    throw new IllegalArgumentException(String.valueOf(msg));
  }
  public static String checkSysId(String s, String msgTemplate, Object... msgArgs) {
    String ts = ServiceNowStrings.blankToEmpty(s);
    if (ServiceNowStrings.isaSysId(ts)) { return ts; }
    throw new IllegalArgumentException(Preconditions.format(msgTemplate, msgArgs));
  }
  
  
  
}
