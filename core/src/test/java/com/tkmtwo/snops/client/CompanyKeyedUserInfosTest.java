package com.tkmtwo.snops.client;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;


import com.tkmtwo.hc.uri.UserInfo;
import com.tkmtwo.snops.CompanyInstance;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


/**
 *
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class CompanyKeyedUserInfosTest {

  private static final String cid0 = "00000000000000000000000000000000";
  private static final String cid1 = "11111111111111111111111111111111";

  
  @Test
  public void test0010Plain() {
    CompanyKeyedUserInfos ics = new CompanyKeyedUserInfos();

    ics.add(new CompanyInstance("instance0", cid0), UserInfo.of("uname0", "passwd0"));
    assertEquals(1, ics.size());

    ics.add(new CompanyInstance("instance1", cid1), UserInfo.of("uname1", "passwd1"));
    assertEquals(2, ics.size());
    
    
    UserInfo bcZero = ics.get("instance0", cid0);
    assertNotNull(bcZero);
    assertEquals("uname0", bcZero.getUserName());
    assertEquals("passwd0", bcZero.getPassword());

    
    UserInfo bcOne = ics.get("instance1", cid1);
    assertNotNull(bcOne);
    assertEquals("uname1", bcOne.getUserName());
    assertEquals("passwd1", bcOne.getPassword());
    
    
    
    //
    //Now replace credentials for instance1 and confirm...
    //
    ics.add(new CompanyInstance("instance1", cid1), UserInfo.of("unameX", "passwdX"));
    assertEquals(2, ics.size());
    
    bcOne = ics.get("instance1", cid1);
    assertNotNull(bcOne);
    assertEquals("unameX", bcOne.getUserName());
    assertEquals("passwdX", bcOne.getPassword());
    
    
    //
    // Get values and verify
    //
    List<UserInfo> userInfos = ics.getValues();
    assertEquals(2, userInfos.size());

    
  }
  
  
  
  

  @Test
  public void test0020Uri() {
    CompanyKeyedUserInfos ics = new CompanyKeyedUserInfos();
    
    ics.add("servicenow://uname0:passwd0@instance0/" + cid0);
    assertEquals(1, ics.size());
    
    ics.add("servicenow://uname0:passwd0@instance0/" + cid0);
    assertEquals(1, ics.size());
    
    ics.add("servicenow://uname1:passwd1@instance1/" + cid1);
    assertEquals(2, ics.size());
    
    
    UserInfo bcZero = ics.get("instance0", cid0);
    assertNotNull(bcZero);
    assertEquals("uname0", bcZero.getUserName());
    assertEquals("passwd0", bcZero.getPassword());
    
    UserInfo bcOne = ics.get("instance1", cid1);
    assertNotNull(bcOne);
    assertEquals("uname1", bcOne.getUserName());
    assertEquals("passwd1", bcOne.getPassword());
    
    
    
    //
    //Now replace credentials for instance1 and confirm...
    //
    ics.add("servicenow://unameX:passwdX@instance1/" + cid1);
    assertEquals(2, ics.size());
    
    bcOne = ics.get("instance1", cid1);
    assertNotNull(bcOne);
    assertEquals("unameX", bcOne.getUserName());
    assertEquals("passwdX", bcOne.getPassword());
    
    
    //
    // Get values and verify
    //
    List<UserInfo> userInfos = ics.getValues();
    assertEquals(2, userInfos.size());

    
  }

  
}
