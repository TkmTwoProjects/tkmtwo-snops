package com.tkmtwo.snops.client;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;



import com.tkmtwo.hc.uri.Params;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


/**
 *
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TableParamsTest {
  
  
  @Test
  public void test0010Plain() {
    Params params =
      new TableParams.Builder()
      .query("fname=joe")
      .displayValue(true)
      .fields("field0", "field1", "field2")
      .view("view0")
      .limit("limit0")
      .offset("offset0")
      .excludeReferenceLink(true)
      .readReplicaCategory("readReplicaCategory0")
      .build();
    
    assertEquals("fname=joe", params.getValues(TableParams.SYSPARM_QUERY).get(0));
    assertEquals("true", params.getValues(TableParams.SYSPARM_DISPLAY_VALUE).get(0));
    assertEquals("field0,field1,field2", params.getValues(TableParams.SYSPARM_FIELDS).get(0));
    assertEquals("view0", params.getValues(TableParams.SYSPARM_VIEW).get(0));
    assertEquals("limit0", params.getValues(TableParams.SYSPARM_LIMIT).get(0));
    assertEquals("offset0", params.getValues(TableParams.SYSPARM_OFFSET).get(0));
    assertEquals("true", params.getValues(TableParams.SYSPARM_EXCLUDE_REFERENCE_LINK).get(0));
    assertEquals("readReplicaCategory0", params.getValues(TableParams.SYSPARM_READ_REPLICA_CATEGORY).get(0));
    
  }


  
  @Test
  public void test0020Templates() {
    Params params =
      new TableParams.Builder()
      .queryTemplate("fname=${someFirstName}")
      .queryValues("john")
      .build();
    
    assertEquals("fname=john", params.getValues(TableParams.SYSPARM_QUERY).get(0));
    
  }
  
  
}
