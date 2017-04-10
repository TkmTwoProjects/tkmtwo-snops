package com.tkmtwo.snops.dictionary;

import static com.google.common.base.Strings.emptyToNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tkmtwo.snops.AbstractSnopsTest;
import com.tkmtwo.snops.client.TableParams;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;


/**
 *
 *
 */
public class DictionaryOperationsTest
  extends AbstractSnopsTest {
  
  DictionaryOperations dictionaryOps;
  

  @Before
  public void setUp() {
    super.setUp();
    
    DictionaryTemplate dictionaryTemplate = new DictionaryTemplate(getRestClient());
    dictionaryTemplate.afterPropertiesSet();
    dictionaryOps = dictionaryTemplate;
  }
  
  @Test
  public void test00010FindIncident() {
    String tableName = "incident";
    SysDbObject sdo = dictionaryOps.sysDbObjectOps().findByName(tableName);
    assertNotNull(sdo);
    assertEquals("incident", sdo.getName());
    assertEquals("sys_db_object", sdo.getSysClassName());
    assertEquals(false, sdo.getExtendable());
  }
  
  @Test
  public void test00011NotFindIncident() {
    String tableName = "nosuchtable";
    SysDbObject sdo = dictionaryOps.sysDbObjectOps().findByName(tableName);
    assertNull(sdo);
  }
  
  @Test
  public void test00012TableDefinition() {
    
    String tableName = "incident";
    
    TableDefinition td = dictionaryOps.tableDefinition(tableName);
    
    assertNotNull(td);
    assertEquals(tableName, td.getName());
    
    //Number of fields assumes incident table in fresh ServiceNow Helsinki.
    assertEquals(83, td.getFields().size());
    assertEquals(tableName, td.getField("sys_id").getTableName());
  }
  
  @Test
  public void test0010ObjectMapper()
  throws Exception {
    String tableName = "incident";
    
    TableDefinition td = dictionaryOps.tableDefinition(tableName);
    
    assertNotNull(td);
    
    ObjectMapper om = new ObjectMapper();
    System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(td));
    
    ObjectNode on = om.valueToTree(td);
    assertEquals("incident", on.get("name").asText());

    ObjectNode fieldsOn = (ObjectNode) on.get("fields");
    //Number of fields assumes incident table in fresh ServiceNow Helsinki.
    //assertTrue(fieldsOn.size() > 0);
    
    ObjectNode sysUpdatedByOn = (ObjectNode) fieldsOn.get("sys_updated_by");
    assertNotNull(sysUpdatedByOn);
    assertEquals("task", sysUpdatedByOn.get("table_name").asText());
    assertEquals("sys_updated_by", sysUpdatedByOn.get("column_name").asText());
    assertEquals("STRING", sysUpdatedByOn.get("internal_type").asText());
    assertNull(emptyToNull(sysUpdatedByOn.get("reference").asText()));
    assertEquals(40, sysUpdatedByOn.get("max_length").asInt());
    
    ObjectNode callerIdOn = (ObjectNode) fieldsOn.get("caller_id");
    assertNotNull(callerIdOn);
    assertEquals("incident", callerIdOn.get("table_name").asText());
    assertEquals("caller_id", callerIdOn.get("column_name").asText());
    assertEquals("REFERENCE", callerIdOn.get("internal_type").asText());
    assertEquals("sys_user", callerIdOn.get("reference").asText());
    assertEquals(32, callerIdOn.get("max_length").asInt());

  }

  
  
  
}
