package com.tkmtwo.snops.dictionary;

import static com.google.common.base.TkmTwoStrings.isBlank;

import java.util.EnumSet;

/**
 *
 *
 */
public enum GlideType {
  BOOLEAN,
  DATE,
  DATE_TIME,
  DECIMAL,
  FLOATING_POINT_NUMBER,
  SYS_ID,
  INTEGER,
  LONGINT,
  STRING,
  TIME;
  
  
  public static GlideType from(String s) {
    if (isBlank(s)) { return null; }
    for (GlideType gt : EnumSet.allOf(GlideType.class)) {
      if (gt.name().equalsIgnoreCase(s)) {
        return gt;
      }
    }
    return null;
  }
  

  
  
}
