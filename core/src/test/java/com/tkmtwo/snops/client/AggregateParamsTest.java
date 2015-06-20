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
public final class AggregateParamsTest {
  
  
  @Test
  public void test0010Plain() {
    Params params =
      new AggregateParams.Builder()
      .query("fname=joe")
      .having("lname=doe")
      .min("minField0")
      .max("maxField0", "maxField1")
      .avg("avgField0", "avgField1", "avgField2")
      .sum("sumField0", "sumField1", "sumField2", "sumField3")
      .count(true)
      .displayValue(true)
      .groupBy("groupAlpha", "groupBravo")
      .orderBy("orderAlpha", "orderBravo")
      .build();
    
    assertEquals("fname=joe",
                 params.getValues(AggregateParams.SYSPARM_QUERY).get(0));
    assertEquals("lname=doe",
                 params.getValues(AggregateParams.SYSPARM_HAVING).get(0));
    assertEquals("minField0",
                 params.getValues(AggregateParams.SYSPARM_MIN_FIELDS).get(0));
    assertEquals("maxField0,maxField1",
                 params.getValues(AggregateParams.SYSPARM_MAX_FIELDS).get(0));
    assertEquals("avgField0,avgField1,avgField2",
                 params.getValues(AggregateParams.SYSPARM_AVG_FIELDS).get(0));
    assertEquals("sumField0,sumField1,sumField2,sumField3",
                 params.getValues(AggregateParams.SYSPARM_SUM_FIELDS).get(0));
    assertEquals("true",
                 params.getValues(AggregateParams.SYSPARM_COUNT).get(0));
    assertEquals("true",
                 params.getValues(AggregateParams.SYSPARM_DISPLAY_VALUE).get(0));
    assertEquals("orderAlpha,orderBravo",
                 params.getValues(AggregateParams.SYSPARM_ORDER_BY).get(0));
    
  }


  
  @Test
  public void test0020Templates() {
    Params params =
      new AggregateParams.Builder()
      .queryTemplate("fname=${someFirstName}")
      .queryValues("john")
      .havingTemplate("lname=${someLastName}")
      .havingValues("doh")
      .build();
    
    assertEquals("fname=john", params.getValues(AggregateParams.SYSPARM_QUERY).get(0));
    assertEquals("lname=doh", params.getValues(AggregateParams.SYSPARM_HAVING).get(0));
    
  }
  
  
}
