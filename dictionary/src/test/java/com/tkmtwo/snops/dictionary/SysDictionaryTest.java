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
public class SysDictionaryTest
  extends AbstractSnopsTest {
  
  SysDictionaryOperations tableOps;
  
  
  @Before
  public void setUp() {
    super.setUp();
    
    SysDictionaryTemplate tableTemplate = new SysDictionaryTemplate(getRestClient());
    tableTemplate.afterPropertiesSet();
    tableOps = tableTemplate;
  }
  
  
  
  @Test
  public void test00010FindIncident() {
    String tableName = "incident";
    
    List<SysDictionary> sds = tableOps.findActive(tableName);
    assertTrue(sds.size() > 0);
    
    String[] expectedColumns = new String[] {
      "sys_id",
      "caller_id",
      "resolved_at",
      "resolved_by"
    };
    
    for (String expectedColumn : expectedColumns) {
      assertTrue(hasColumn(sds, expectedColumn));
    }
    
  }
  
  private boolean hasColumn(List<SysDictionary> sds, String columnName) {
    for (SysDictionary sd : sds) {
      if (sd.getElement().equals(columnName)) {
        return true;
      }
    }
    return false;
  }
  
  
}
