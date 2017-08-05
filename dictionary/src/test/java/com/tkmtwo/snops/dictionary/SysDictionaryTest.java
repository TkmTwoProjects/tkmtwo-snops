package com.tkmtwo.snops.dictionary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.fail;

import com.tkmtwo.snops.AbstractSnopsTest;
import com.tkmtwo.snops.client.TableParams;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;


/**
 *
 *
 */
public class SysDictionaryTest
  extends AbstractSnopsTest {
  
  SysDictionaryOperations tableOps;
  SysGlideObjectOperations sgoOps;
  
  @Before
  public void setUp() {
    super.setUp();
    
    SysDictionaryTemplate tableTemplate = new SysDictionaryTemplate(getRestClient());
    tableTemplate.afterPropertiesSet();
    tableOps = tableTemplate;
    
    SysGlideObjectTemplate sgoTemplate = new SysGlideObjectTemplate(getRestClient());
    sgoTemplate.afterPropertiesSet();
    sgoOps = sgoTemplate;
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
  
  
  
  @Test
  public void test0011Confess() {
    String tableName = "incident";
    
    //Set<String> internalTypes = new HashSet<>();
    //Set<String> scalarTypes = new HashSet<>();
    
    List<SysDictionary> sds = tableOps.findActive(tableName);
    assertTrue(sds.size() > 0);

    Map<String, SysGlideObject> sgoMap = sgoOps.mapByName();
    assertNotNull(sgoMap);
    assertFalse(sgoMap.isEmpty());
    
    for (SysDictionary sd : sds) {
      System.out.println();
      System.out.println("SD: " + sd);
      System.out.println("  SGO: " + sgoMap.get(sd.getInternalType()));
      
      //internalTypes.add(sd.getInternalType());
      //scalarTypes.add(sgo.get(sd.getInternalTypeScalarType());

    }
    
    /*
    for (String scalarType : scalarTypes) {
      System.out.println("SCALAR: " + scalarType);
    }
    */
    
  }

  @Test
  public void test0012ConfessScalars() {
    Set<String> sts = new HashSet<>();
    List<SysGlideObject> sgos = sgoOps.findAll();
    for (SysGlideObject sgo :sgos) {
      sts.add(sgo.getScalarType());
    }
    for (String st : sts) {
      System.out.println("SCALAR: " + st);
    }
  }
    

  
}
