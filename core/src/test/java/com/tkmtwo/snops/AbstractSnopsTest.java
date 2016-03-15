package com.tkmtwo.snops;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.tkmtwo.hc.uri.UserInfo;
import com.tkmtwo.snops.client.RestClient;
import java.time.LocalDateTime;
import java.util.Properties;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class AbstractSnopsTest {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass()); 
  
  private String startAt = null;
  private RestClient restClient;
  
  protected String getStartAt() { return startAt; }
  protected RestClient getRestClient() { return restClient; }
  
  
  public void setUp() {
    startAt = LocalDateTime.now().toString();
    
    Properties props = new Properties();
    try {
      props.load(ClassLoader.getSystemResourceAsStream("snops-test.properties"));
    } catch (Exception ex) {
      fail("Error reading properties: " + ex.getMessage());
    }
    
    restClient = new RestClient(new Instance(props.getProperty("instance")),
                                UserInfo.of(props.getProperty("uname"),
                                            props.getProperty("passwd")));
    restClient.afterPropertiesSet();
    
  }  
  
  
  
}
