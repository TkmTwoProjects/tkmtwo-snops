package com.tkmtwo.snops.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;

import com.tkmtwo.snops.AbstractSnopsTest;
import org.junit.Before;
import org.junit.Test;


/**
 *
 *
 */
public class JsonLocationTest
  extends AbstractSnopsTest {
  
  JsonLocationOperations tableOps;

  @Before
  public void setUp() {
    super.setUp();
    
    JsonLocationTemplate tableTemplate = new JsonLocationTemplate(getRestClient());
    tableTemplate.afterPropertiesSet();
    tableOps = tableTemplate;
  }
  
  @Test
  public void test00010FindAndCreate() {
    String locName = "Parts Unknown";
    String locStreet = "1313 Mockingbird Lane";


    
    //
    // Make sure we don't have one already
    //
    JsonLocation loc = tableOps.findByName(locName);
    assertNull(loc);

    //
    // Create a location with a name and no street
    //
    loc = new JsonLocation();
    loc.put("name", locName);
    loc = tableOps.save(loc);
    assertNotNull(loc);
    confess("After first save", loc);
    
    assertEquals(locName, loc.getString("name"));
    assertEquals("", loc.getString("street"));

    //
    // Update the street and save
    //
    loc.put("street", locStreet);
    loc = tableOps.save(loc);
    confess("After second save", loc);
    
    //
    // Verify change in street
    JsonLocation locFound = tableOps.findByName(locName);
    assertNotNull(locFound);
    confess("locFound", locFound);
    assertEquals(locName, locFound.getString("name"));
    assertEquals(locStreet, locFound.getString("street"));
    assertEquals(loc.getSysId(), locFound.getSysId());

    //
    // Delete and verify gone
    //
    tableOps.delete(loc.getSysId());
    JsonLocation locLost = tableOps.findByName(locName);
    assertNull(locLost);
    
  }
  
  
  private void confess(String msg, JsonLocation loc) {
    System.out.println(String.format("%-20s -> %-5s -> %s%n", msg, loc.getObjectNode().size(), loc.toString()));
  }
  
}
