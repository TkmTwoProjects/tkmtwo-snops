package com.tkmtwo.snops.jackson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tkmtwo.snops.AbstractSnopsTest;
import com.tkmtwo.snops.TableOperations;
import org.junit.Before;
import org.junit.Test;


/**
 *
 *
 */
public class ObjectNodeTemplateTest
  extends AbstractSnopsTest {
  
  TableOperations<ObjectNode> onOps = null;

  @Before
  public void setUp() {
    super.setUp();
    ObjectNodeTemplate onTemplate = new ObjectNodeTemplate(getRestClient(), "incident");
    onTemplate.afterPropertiesSet();
    onOps = onTemplate;
  }
  
  @Test
  public void test00010FindOneAndGet() {
    String qs = "number=INC0000003";
    ObjectNode incOne = onOps.findOne(qs);
    assertNotNull(incOne);

    ObjectNode incTwo = onOps.get(incOne.get("sys_id").asText());
    assertNotNull(incTwo);

    assertEquals(incOne, incTwo);
    
    //System.out.println(incOne);
    //System.out.println(incTwo);
  }


}
