package com.tkmtwo.snops.dictionary;


//import static com.google.common.base.TkmTwoConditions.checkNotBlank;

import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.snops.client.RestClient;
import com.tkmtwo.snops.client.TableParams;
import com.tkmtwo.snops.map.MappedTemplate;
import java.util.List;

/**
 *
 */
public class SysDbObjectTemplate
  extends MappedTemplate<SysDbObject>
  implements SysDbObjectOperations {

  private static final String TABLE_NAME = "sys_db_object";

  
  public SysDbObjectTemplate(RestClient rc) {
    super(rc, TABLE_NAME, SysDbObject.class);
  }
  
  
  public List<SysDbObject> findAll() {
    Params params = new TableParams.Builder()
      .query("nameISNOTEMPTY")
      .build();
    return findMany(params);
  }
  
  
  public SysDbObject findByName(String name) {
    Params params = new TableParams.Builder()
      .queryTemplate("name=${0}")
      .queryValues(name)
      .build();
    return findOne(params);
  }
  
  
  
}
