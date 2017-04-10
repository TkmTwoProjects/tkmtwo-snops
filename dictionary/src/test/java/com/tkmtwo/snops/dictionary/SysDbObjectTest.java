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
public class SysDbObjectTest
  extends AbstractSnopsTest {
  
  SysDbObjectOperations tableOps;
  

  @Before
  public void setUp() {
    super.setUp();
    
    SysDbObjectTemplate tableTemplate = new SysDbObjectTemplate(getRestClient());
    tableTemplate.afterPropertiesSet();
    tableOps = tableTemplate;
  }
  
  @Test
  public void test00010FindIncident() {
    String tableName = "incident";
    SysDbObject sdo = tableOps.findByName(tableName);
    assertNotNull(sdo);
    assertEquals("incident", sdo.getName());
    assertEquals("sys_db_object", sdo.getSysClassName());
    assertEquals(false, sdo.getExtendable());
  }
  
  @Test
  public void test00011NotFindIncident() {
    String tableName = "nosuchtable";
    SysDbObject sdo = tableOps.findByName(tableName);
    assertNull(sdo);
  }
  
  @Test
  public void test00020All() {
    List<SysDbObject> sdos = tableOps.findAll();
    
    //Number of SysDbObjects assumes fresh ServiceNow Helsinki
    assertEquals(2377, sdos.size());
  }
  
}
