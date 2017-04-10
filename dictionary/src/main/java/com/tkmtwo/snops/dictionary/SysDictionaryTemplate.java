package com.tkmtwo.snops.dictionary;


//import static com.google.common.base.TkmTwoConditions.checkNotBlank;
import static com.google.common.base.TkmTwoStrings.isBlank;

import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.snops.client.RestClient;
import com.tkmtwo.snops.client.TableParams;
import com.tkmtwo.snops.map.MappedTemplate;
import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class SysDictionaryTemplate
  extends MappedTemplate<SysDictionary>
  implements SysDictionaryOperations {

  private static final String TABLE_NAME = "sys_dictionary";

  
  public SysDictionaryTemplate(RestClient rc) {
    super(rc, TABLE_NAME, SysDictionary.class);
  }
  
  
  public List<SysDictionary> findActive(SysDbObject sdo) {
    if (sdo == null || isBlank(sdo.getName())) { return new ArrayList<SysDictionary>(); }
    
    Params params = new TableParams.Builder()
      .queryTemplate("name=${0}")
      .queryValues(sdo.getName())
      .build();
    return findMany(params);
  }
  
  
  public List<SysDictionary> findActive(String tableName) {
    if (isBlank(tableName)) { return new ArrayList<SysDictionary>(); }
    
    Params params = new TableParams.Builder()
      .queryTemplate("name=${0}")
      .queryValues(tableName)
      .build();
    return findMany(params);
  }

  //internal_type!=collection^name=incident^ORname=task  

  /*
    internal_type!=collection
      ^name=cmdb_ci_ip_router
      ^ORname=cmdb_ci_netgear
      ^ORname=cmdb_ci


  */
}
