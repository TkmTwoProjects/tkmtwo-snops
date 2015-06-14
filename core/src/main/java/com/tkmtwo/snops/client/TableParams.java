/*
 *
 * Copyright 2015 Accenture
 *
 */


package com.tkmtwo.snops.client;

import com.google.common.base.TkmTwoJointers;
import com.tkmtwo.hc.uri.Param;
import com.tkmtwo.hc.uri.ParamType;


/**
 *
 *
 */
public class TableParams {
  
  public static final Param SYSPARM_QUERY =
    new Param("sysparm_query", ParamType.SCALAR);
  
  public static final Param SYSPARM_DISPLAY_VALUE =
    new Param("sysparm_display_value", ParamType.SCALAR);
  
  public static final Param SYSPARM_FIELDS =
    new Param("sysparm_fields", ParamType.SET, TkmTwoJointers.COMMA_JOINER);
  
  public static final Param SYSPARM_VIEW =
    new Param("sysparm_view", ParamType.SCALAR);
  
  public static final Param SYSPARM_LIMIT =
    new Param("sysparm_limit", ParamType.SCALAR);
  
  public static final Param SYSPARM_EXCLUDE_REFERENCE_LINK =
    new Param("sysparm_exclude_reference_link", ParamType.SCALAR);
  
  public static final Param SYSPARM_READ_REPLICA_CATEGORY =
    new Param("sysparm_read_replica_category", ParamType.SCALAR);
  
}
