package com.tkmtwo.snops.jackson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.snops.AbstractSnopsTest;
import com.tkmtwo.snops.client.TableParams;
import org.junit.Before;
import org.junit.Test;

/**
 *
 *
 */
public class SimpleObjectNodeTemplateTest
  extends AbstractSnopsTest {
  
  SimpleObjectNodeTemplate onTemplate = null;

  @Before
  public void setUp() {
    super.setUp();
    onTemplate = new SimpleObjectNodeTemplate(getRestClient());
    onTemplate.afterPropertiesSet();
  }
  
  @Test
  public void test00010FindOneAndGet() {
    String tableName = "incident";
    String incNumber = "INC0000003";
    
    Params params = new TableParams.Builder()
      .queryTemplate("number=${0}")
      .queryValues(incNumber)
      .build();

    ObjectNode incOne = onTemplate.findOne(tableName, params);
    assertNotNull(incOne);
    assertEquals(incNumber, incOne.get("number").asText());

    ObjectNode incTwo = onTemplate.get(tableName, incOne.get("sys_id").asText());
    assertNotNull(incTwo);
    assertEquals(incNumber, incTwo.get("number").asText());
    

    assertEquals(incOne, incTwo);
    
    //System.out.println(incOne);
    //System.out.println(incTwo);
  }


}
