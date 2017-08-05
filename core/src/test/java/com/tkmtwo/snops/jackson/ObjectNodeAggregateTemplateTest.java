package com.tkmtwo.snops.jackson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.snops.AbstractSnopsTest;
import com.tkmtwo.snops.AggregateOperations;
import com.tkmtwo.snops.client.AggregateParams;
import java.util.List;
import org.junit.Before;
import org.junit.Test;


/**
 *
 *
 */
public class ObjectNodeAggregateTemplateTest
  extends AbstractSnopsTest {
  
  AggregateOperations<ObjectNode> onAggOps = null;

  @Before
  public void setUp() {
    super.setUp();
    ObjectNodeAggregateTemplate onAggTemplate = new ObjectNodeAggregateTemplate(getRestClient(), "incident");
    onAggTemplate.afterPropertiesSet();
    onAggOps = onAggTemplate;
  }
  
  @Test
  public void test00010GetOne() {
    Params params =
      new AggregateParams.Builder()
      .min("number")
      .count(true)
      .build();

    ObjectNode on = onAggOps.getOne(params);

    JsonNode minNode = on.get("min");
    assertNotNull(minNode);
    
    /*
    assertEquals("INC0000001",
                 minNode.get("number").asText());
    assertEquals("INC0000001",
                 on.get("min").get("number").asText());
    */
    
    System.out.println("AGG is: " + on.toString());
  }

  
  @Test
  public void test00020GetMany() {
    Params params =
      new AggregateParams.Builder()
      .min("number")
      .groupBy("assignment_group")
      .count(true)
      .build();

    List<ObjectNode> ons = onAggOps.getMany(params);
    for (ObjectNode on : ons) {
      System.out.println("ON is: " + on.toString());
    }
    
    
  }
  
  
  
  
  @Test
  public void test00030WithQuery() {
    Params params =
      new AggregateParams.Builder()
      .query("assignment_groupISNOTEMPTY")
      .min("number")
      .groupBy("assignment_group")
      .count(true)
      .build();

    List<ObjectNode> ons = onAggOps.getMany(params);
    for (ObjectNode on : ons) {
      System.out.println("ONQ is: " + on.toString());
    }
    
  }

  
}
