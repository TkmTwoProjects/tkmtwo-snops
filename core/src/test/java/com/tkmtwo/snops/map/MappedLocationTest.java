package com.tkmtwo.snops.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;

import com.google.common.collect.ImmutableList;
import com.tkmtwo.snops.AbstractSnopsTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;


/**
 *
 *
 */
public class MappedLocationTest
  extends AbstractSnopsTest {
  
  MappedLocationOperations tableOps;

  @Before
  public void setUp() {
    super.setUp();
    
    MappedLocationTemplate tableTemplate = new MappedLocationTemplate(getRestClient());
    tableTemplate.afterPropertiesSet();
    tableOps = tableTemplate;
  }

  @Test
  public void test00020FindParts() {
    List<MappedLocation> l = tableOps.getMany("nameSTARTSWITH${0}", ImmutableList.of("Parts"));
    assertEquals(2, l.size());
  }
  
  
  @Test
  public void test00010FindAndCreate() {
    String locName = "Parts Unknown Mapped";
    String locStreet = "1313 Mockingbird Lane";


    
    //
    // Make sure we don't have one already
    //
    MappedLocation loc = tableOps.findByName(locName);
    assertNull(loc);

    //
    // Create a location with a name and no street
    //
    loc = new MappedLocation();
    loc.setName(locName);
    loc = tableOps.save(loc);
    assertNotNull(loc);
    confess("After first save", loc);
    
    assertEquals(locName, loc.getName());
    assertEquals("", loc.getStreet());

    //
    // Update the street and save
    //
    loc.setStreet(locStreet);
    loc = tableOps.save(loc);
    confess("After second save", loc);
    
    //
    // Verify change in street
    MappedLocation locFound = tableOps.findByName(locName);
    assertNotNull(locFound);
    confess("locFound", locFound);
    assertEquals(locName, locFound.getName());
    assertEquals(locStreet, locFound.getStreet());
    assertEquals(loc.getSysId(), locFound.getSysId());

    //
    // Delete and verify gone
    //
    tableOps.delete(loc.getSysId());
    MappedLocation locLost = tableOps.findByName(locName);
    assertNull(locLost);
    
  }
  
  
  private void confess(String msg, MappedLocation loc) {
    System.out.println(String.format("%-20s -> %s%n", msg, loc.toString()));
  }
  
}
