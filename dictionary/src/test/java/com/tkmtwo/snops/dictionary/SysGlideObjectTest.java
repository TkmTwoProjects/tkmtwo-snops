package com.tkmtwo.snops.dictionary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;

import com.tkmtwo.snops.AbstractSnopsTest;
import com.tkmtwo.snops.client.TableParams;
import java.util.List;
import org.junit.Before;
import org.junit.Test;


/**
 *
 *
 */
public class SysGlideObjectTest
  extends AbstractSnopsTest {
  
  SysGlideObjectOperations tableOps;
  

  @Before
  public void setUp() {
    super.setUp();
    
    SysGlideObjectTemplate tableTemplate = new SysGlideObjectTemplate(getRestClient());
    tableTemplate.afterPropertiesSet();
    tableOps = tableTemplate;
  }
  
  @Test
  public void test00010FindAll() {
    List<SysGlideObject> sgos = tableOps.findAll();
    for (SysGlideObject sgo : sgos) {
      System.out.println("SGO: " + sgo);
    }
  }
  
  
}
