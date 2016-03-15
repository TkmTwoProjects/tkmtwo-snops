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
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.*;
import org.junit.Before;
import org.junit.Test;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;


/**
 *
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleObjectNodeTemplateTest
  extends AbstractSnopsTest {
  
  private static final String BIG_LIMIT = "100000";
  
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
    
    /*
    //Quickie test of on update...
    ObjectNode udOn = JsonNodeFactory.instance.objectNode();
    udOn.put("sys_id", incOne.get("sys_id"));
    udOn.put("short_description", "Some short descr at " + LocalDateTime.now().toString());
    System.out.println("Sending: " + udOn.toString());
    System.out.println(onTemplate.update(tableName, udOn));
    */
    
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
  
  //@Test
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
  
  */  
  

  //@Test
  public void testPurgeBReferences() {
    String tableName = "u_int_reference";
    
    List<ObjectNode> ons =
      onTemplate.findMany(tableName,
                          new TableParams.Builder()
                          .query("u_taskISEMPTY")
                          .fields("sys_id", "u_exchange")
                          .limit(BIG_LIMIT)
                          .build());
    logger.info("There are '" + ons.size() + "' u_int_reference records to delete.");
    
    
    ExecutorService execSvc = Executors.newFixedThreadPool(1);
    ons
      .forEach(i -> {
          //try { Thread.sleep(200); } catch (Exception ex) { logger.warn(ex.getMessage()); }
          execSvc.submit(() -> {
              logger.info("DELETING: " + i);
              onTemplate.delete(tableName, i);
            });
        }
        );
    
    execSvc.shutdown();
    try {
      execSvc.awaitTermination(86400, TimeUnit.SECONDS);
    } catch (InterruptedException interruptedExeption) {
      throw new RuntimeException("Timed out.");
    }
    
    
    
    
  }
  
  
  //@Test
  public void testPurgeBOlogs() {
    String tableName = "u_int_olog";
    
    List<ObjectNode> ons =
      onTemplate.findMany(tableName,
                          new TableParams.Builder()
                          .query("u_taskISEMPTY")
                          .fields("sys_id", "u_subscription")
                          .limit(BIG_LIMIT)
                          .build());
    logger.info("There are '" + ons.size() + "' u_int_olog records to delete.");
    
    
    ExecutorService execSvc = Executors.newFixedThreadPool(1);
    ons
      .forEach(i -> {
          //try { Thread.sleep(200); } catch (Exception ex) { logger.warn(ex.getMessage()); }
          execSvc.submit(() -> {
              logger.info("DELETING: " + i);
              onTemplate.delete(tableName, i);
            });
        }
        );
    
    execSvc.shutdown();
    try {
      execSvc.awaitTermination(86400, TimeUnit.SECONDS);
    } catch (InterruptedException interruptedExeption) {
      throw new RuntimeException("Timed out.");
    }
    
    
    
    
  }
  
  
  
  //@Test
  public void testPurgeBOErrors() {
    String tableName = "u_int_oerror";
    
    List<ObjectNode> ons =
      onTemplate.findMany(tableName,
                          new TableParams.Builder()
                          .query("u_taskISEMPTY")
                          .fields("sys_id", "u_subscription")
                          .limit(BIG_LIMIT)
                          .build());
    logger.info("There are '" + ons.size() + "' u_int_oerror records to delete.");
    
    
    ExecutorService execSvc = Executors.newFixedThreadPool(1);
    ons
      .forEach(i -> {
          //try { Thread.sleep(200); } catch (Exception ex) { logger.warn(ex.getMessage()); }
          execSvc.submit(() -> {
              logger.info("DELETING: " + i);
              onTemplate.delete(tableName, i);
            });
        }
        );
    
    execSvc.shutdown();
    try {
      execSvc.awaitTermination(86400, TimeUnit.SECONDS);
    } catch (InterruptedException interruptedExeption) {
      throw new RuntimeException("Timed out.");
    }
    
    
    
    
  }

  //@Test
  public void testPurgeAIncident() {
    String tableName = "incident";
    
    
    List<ObjectNode> ons =
      onTemplate.findMany(tableName,
                          new TableParams.Builder()
                          //.query("company.name=Krakatoa^sys_created_on<javascript:gs.dateGenerate('2016-02-13','00:00:00')")
                          .query("sys_created_on<javascript:gs.dateGenerate('2016-03-06','00:00:00')")
                          .fields("sys_id", "number")
                          .limit(BIG_LIMIT)
                          //.limit("10")
                          .build());
    logger.info("There are '" + ons.size() + "' incident records to delete.");
    
    
    ExecutorService execSvc = Executors.newFixedThreadPool(1);
    ons
      .forEach(i -> {
          try { Thread.sleep(100); } catch (Exception ex) { logger.warn(ex.getMessage()); }
          execSvc.submit(() -> {

              /*
              String uref = i.get("u_reference").asText();
              if (uref != null  && uref.length() == 36) {
                logger.info("DELETING " + i);
                onTemplate.delete(tableName, i);
              }
              */
              
              logger.info("DELETING: " + i);
              onTemplate.delete(tableName, i);

            });
        }
        );
    
    execSvc.shutdown();
    try {
      execSvc.awaitTermination(86400, TimeUnit.SECONDS);
    } catch (InterruptedException interruptedExeption) {
      throw new RuntimeException("Timed out.");
    }
    
    
    
    
  }
  
  
  
  
  //@Test
  public void testPurgeBInboundReferences() {
    String tableName = "u_int_inbound_reference";
    
    List<ObjectNode> ons =
      onTemplate.findMany(tableName,
                          new TableParams.Builder()
                          //.query("u_taskISEMPTY")
                          //.query("sys_idISNOTEMPTY")
                          .limit(BIG_LIMIT)
                          .build());
    logger.info("There are '" + ons.size() + "' u_int_inbound_reference records to delete.");
    
    
    ExecutorService execSvc = Executors.newFixedThreadPool(1);
    ons
      .forEach(i -> {
          //try { Thread.sleep(200); } catch (Exception ex) { logger.warn(ex.getMessage()); }
          execSvc.submit(() -> {
              logger.info("DELETING: " + i);
              onTemplate.delete(tableName, i);
            });
        }
        );
    
    execSvc.shutdown();
    try {
      execSvc.awaitTermination(86400, TimeUnit.SECONDS);
    } catch (InterruptedException interruptedExeption) {
      throw new RuntimeException("Timed out.");
    }
    
    
    
    
  }
  
  
  
}
