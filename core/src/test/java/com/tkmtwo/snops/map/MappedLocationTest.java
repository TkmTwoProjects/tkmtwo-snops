package com.tkmtwo.snops.map;

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
  public void test00010BlankParams() {
    List<MappedLocation> l =
      tableOps.getMany(new TableParams.Builder().build());
    assertTrue(l.size() > 400);
  }


  @Test
  public void test00011FindByCompanyName() {
    //List<MappedLocation> l = tableOps.getMany("nameSTARTSWITH${0}", ImmutableList.of("Parts"));
    List<MappedLocation> l =
      tableOps.getMany(new TableParams.Builder()
                       .queryTemplate("company.name=${0}")
                       .queryValues("ACME North America")
                       .build());
    assertEquals(372, l.size());
  }
  
  
  @Test
  public void test00020FindAndCreate() {
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
    assertEquals(0, loc.getSysModCount());

    //
    // Update the street and save
    //
    loc.setStreet(locStreet);
    loc.setSysCreatedBy("noone");
    loc.setSysModCount(73);

    loc = tableOps.save(loc);
    confess("After second save", loc);
    assertEquals(1, loc.getSysModCount());
    
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
