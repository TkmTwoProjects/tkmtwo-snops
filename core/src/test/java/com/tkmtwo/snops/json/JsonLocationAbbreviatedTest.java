package com.tkmtwo.snops.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;

import com.tkmtwo.snops.AbstractSnopsTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;


/**
 *
 *
 */
public class JsonLocationAbbreviatedTest
  extends AbstractSnopsTest {
  
  JsonLocationOperations tableOps;

  @Before
  public void setUp() {
    super.setUp();
    
    JsonLocationTemplateAbbreviated tableTemplate =
      new JsonLocationTemplateAbbreviated(getRestClient());
    tableTemplate.afterPropertiesSet();
    tableOps = tableTemplate;
  }
  
  
  
  
  @Test
  public void test00010FindAndCreate() {
    String locName = "Parts Unknown Abbreviated";
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
    assertEquals("Number of fields not equal.",
                 JsonLocationTemplateAbbreviated.FIELD_NAMES.size(), loc.getObjectNode().size());
    confess("After first save", loc);

    assertEquals(locName, loc.getString("name"));
    assertEquals("", loc.getString("street"));

    //
    // Update the street and save
    //
    loc.put("street", locStreet);
    loc = tableOps.save(loc);
    assertNotNull(loc);
    assertEquals("Number of fields not equal.",
                 JsonLocationTemplateAbbreviated.FIELD_NAMES.size(), loc.getObjectNode().size());
    confess("After second save", loc);
    
    //
    // Verify change in street
    JsonLocation locFound = tableOps.findByName(locName);
    assertNotNull(locFound);
    assertEquals("Number of fields not equal.",
                 JsonLocationTemplateAbbreviated.FIELD_NAMES.size(), locFound.getObjectNode().size());
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
  
  
  @Test
  public void test00010Entities() {
    List<JsonLocation> rootLocs = tableOps.findRoots();
    JsonEntities rootEntities = tableOps.findRootEntities();

    assertTrue(rootLocs.size() > 0);
    assertEquals(rootLocs.size(), rootEntities.getObjectNodes().size());
    
  }
  
  private void confess(String msg, JsonLocation loc) {
    System.out.println(String.format("%-20s -> %-5s -> %s%n", msg, loc.getObjectNode().size(), loc.toString()));
  }
  
}
