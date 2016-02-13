package com.tkmtwo.snops.jackson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;


import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.snops.AbstractSnopsTest;
import com.tkmtwo.snops.client.TableParams;
import java.time.LocalDateTime;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 *
 */
public class SimpleObjectNodeTemplateTest
  extends AbstractSnopsTest {
  
  SimpleObjectNodeTemplate onTemplate = null;

  @Before
  public void setUp() {
    super.setUp();
    onTemplate = new SimpleObjectNodeTemplate(getRestClient());
    onTemplate.afterPropertiesSet();
  }
  
  @Test
  public void test00010FindOneAndGet() {
    String tableName = "incident";
    String incNumber = "INC0000003";
    
    Params params = new TableParams.Builder()
      .queryTemplate("number=${0}")
      .queryValues(incNumber)
      .build();

    ObjectNode incOne = onTemplate.findOne(tableName, params);
    assertNotNull(incOne);
    assertEquals(incNumber, incOne.get("number").asText());

    ObjectNode incTwo = onTemplate.get(tableName, incOne.get("sys_id").asText());
    assertNotNull(incTwo);
    assertEquals(incNumber, incTwo.get("number").asText());
    

    assertEquals(incOne, incTwo);
    
    //Quickie test of on update...
    ObjectNode udOn = JsonNodeFactory.instance.objectNode();
    udOn.put("sys_id", incOne.get("sys_id"));
    udOn.put("short_description", "Some short descr at " + LocalDateTime.now().toString());
    System.out.println("Sending: " + udOn.toString());
    System.out.println(onTemplate.update(tableName, udOn));
    
    //System.out.println(incOne);
    //System.out.println(incTwo);
  }
  

  /* Save these for later    
  //@Test
  public void test00020PopulateUName() {
    List<ObjectNode> sysUsers =
      onTemplate.findMany("sys_user",
                          new TableParams.Builder()
                          .query("user_nameISNOTEMPTY")
                          .limit("500")
                          .build());
    
    for (ObjectNode sysUser : sysUsers) {
      ObjectNode uName = JsonNodeFactory.instance.objectNode();
      uName.put("u_user_name", sysUser.get("user_name"));

      onTemplate.save("u_uname", uName);
    }
  }
  
  ////@Test
  public void test00020PopulateUNameStream() {
    List<ObjectNode> sysUsers =
      onTemplate.findMany("sys_user",
                          new TableParams.Builder()
                          .query("user_nameISNOTEMPTY")
                          .fields("sys_id", "user_name")
                          .limit("100")
                          .build());
    
    ExecutorService execSvc = Executors.newFixedThreadPool(10);
    sysUsers
      .forEach(i -> {
          execSvc.submit(() -> {
              System.out.println("BAM: " + i.get("user_name"));
              ObjectNode uName = JsonNodeFactory.instance.objectNode();
              uName.put("u_user_name", i.get("user_name"));
              onTemplate.save("u_uname", uName);
            });
        }
        );
    
    execSvc.shutdown();
    try {
      execSvc.awaitTermination(300, TimeUnit.SECONDS);
    } catch (InterruptedException interruptedExeption) {
      throw new RuntimeException("Timed out.");
    }
  }
  
  
  //@Test
  public void test00021PurgeUNameIterator() {
    List<ObjectNode> uNames =
      onTemplate.findMany("u_uname",
                          new TableParams.Builder().query("u_user_nameISNOTEMPTY").build());
    
    ExecutorService execSvc = Executors.newFixedThreadPool(3);
    for (ObjectNode uName : uNames) {
      final ObjectNode thisUName = uName;
      execSvc.submit(new Runnable() {
          public void run() {
            System.out.println("Running " + Thread.currentThread().getName() + "for " + thisUName.get("u_user_name"));
          }
        });
    }
    
  }
  
  //@Test
  public void test00021PurgeUNameConfess() {
    System.out.println("CONFESSING");
    List<ObjectNode> uNames =
      onTemplate.findMany("u_uname",
                          new TableParams.Builder().query("u_user_nameISNOTEMPTY").build());
    
    uNames
      .forEach(i -> {
          System.out.println(" -Running " + Thread.currentThread().getName() + "for " + i.get("u_user_name"));
        });
  }
  
  
  //@Test
  public void test00021PurgeUNameStreamConfess() {
    System.out.println("STREAMCONFESSING");
    List<ObjectNode> uNames =
      onTemplate.findMany("u_uname",
                          new TableParams.Builder().query("u_user_nameISNOTEMPTY").build());
    
    
    ExecutorService execSvc = Executors.newFixedThreadPool(3);
    uNames
      .forEach(i -> {
          execSvc.submit(() -> {
              System.out.println(" -Running " + Thread.currentThread().getName() + "for " + i.get("u_user_name"));
            });
        }
        );

  }
  
  
  //@Test
  public void test00021PurgeUNameStreamPurge() {
    System.out.println("STREAMPURGING");
    List<ObjectNode> uNames =
      onTemplate.findMany("u_uname",
                          new TableParams.Builder().query("u_user_nameISNOTEMPTY").build());
    
    
    ExecutorService execSvc = Executors.newFixedThreadPool(10);
    uNames
      .forEach(i -> {
          execSvc.submit(() -> {
              onTemplate.delete("u_uname", i);
            });
        }
        );

    execSvc.shutdown();
    try {
      execSvc.awaitTermination(300, TimeUnit.SECONDS);
    } catch (InterruptedException interruptedExeption) {
      throw new RuntimeException("Timed out.");
    }
    
    
  }
  
  
  //
  @Test
  public void test00021PurgeThese() {
    System.out.println("STREAMPURGING");
    List<ObjectNode> ons =
      onTemplate.findMany("cmdb_ci",
                          new TableParams.Builder()
                          .query("company=c902ec79377f82408ac465e2b3990ef3^sys_created_by=muizz.waljee.admin")
                          .build());
    
    
    ExecutorService execSvc = Executors.newFixedThreadPool(10);
    ons
      .forEach(i -> {
          execSvc.submit(() -> {
              onTemplate.delete("cmdb_ci", i);
              //System.out.println("BOOM: " + i.get("sys_id"));
            });
        }
        );

    execSvc.shutdown();
    try {
      execSvc.awaitTermination(3600, TimeUnit.SECONDS);
    } catch (InterruptedException interruptedExeption) {
      throw new RuntimeException("Timed out.");
    }
    
    
  }
  */  

  
  
}
