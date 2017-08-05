package com.tkmtwo.snops.dictionary;

import static com.google.common.base.Strings.emptyToNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Stopwatch;
import com.tkmtwo.snops.AbstractSnopsTest;
import com.tkmtwo.snops.client.TableParams;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;


/**
 *
 *
 */
public class CachingDictionaryTest
  extends AbstractSnopsTest {
  
  DictionaryOperations dictionaryOps;
  CachingDictionary cachingDictionary;
  
  
  @Before
  public void setUp() {
    super.setUp();
    
    DictionaryTemplate dictionaryTemplate = new DictionaryTemplate(getRestClient());
    dictionaryTemplate.afterPropertiesSet();
    dictionaryOps = dictionaryTemplate;
    
    cachingDictionary = new CachingDictionary(dictionaryOps);
    cachingDictionary.afterPropertiesSet();
    
  }
  
  @Test
  public void test0010Sanity() {
    String tableName = "incident";
    
    TableDefinition td = cachingDictionary.tableDefinition(tableName);
    
    assertNotNull(td);
    assertEquals(tableName, td.getTableName());
    
    //Number of fields assumes incident table in fresh ServiceNow Helsinki.
    assertTrue(td.getFields().size() > 83);
    assertEquals(tableName, td.getField("sys_id").getTableName());
    
    assertEquals("sys_updated_by", td.getField("sys_updated_by").getColumnName());
    assertEquals(InternalType.STRING, td.getField("sys_updated_by").getInternalType());
    assertNull(emptyToNull(td.getField("sys_updated_by").getReference()));
    
    assertEquals("incident", td.getField("caller_id").getTableName());
    assertEquals("caller_id", td.getField("caller_id").getColumnName());
    assertEquals(InternalType.REFERENCE, td.getField("caller_id").getInternalType());
    assertEquals("sys_user", td.getField("caller_id").getReference());
    
  }
  
  //@Test
  public void test0010ObjectMapper()
  throws Exception {
    String tableName = "incident";
    
    TableDefinition td = cachingDictionary.tableDefinition(tableName);
    
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
  
  
  //@Test
  public void test0020CachingBehavior() {
    
    long httpFetchFloor = 3000L;
    long cacheFetchCeiling = 100L;
    
    String tableName = "incident";
    
    //Slow to populate, fast to cache
    cachingDictionary.invalidateAll();
    fetchTableTimed(tableName, 1000L, 60000L); //slow first time
    fetchTableTimed(tableName, 0L, 100L); //fast from cache
    
    //Back to slow after invalidateAll()
    cachingDictionary.invalidateAll();
    fetchTableTimed(tableName, 1000L, 60000L); //back toslow first time
  }
  
  private void fetchTableTimed(String tableName, long millisFloor, long millisCeiling) {
    Stopwatch sw = Stopwatch.createStarted();
    TableDefinition td = cachingDictionary.tableDefinition(tableName);
    assertNotNull(td);
    long millisActual = sw.elapsed(TimeUnit.MILLISECONDS);
    assertTrue(millisActual >= millisFloor);
    assertTrue(millisActual <= millisCeiling);
  }
  
  
}
