package com.tkmtwo.snops.json;

import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.snops.client.RestClient;
import com.tkmtwo.snops.client.TableParams;
import java.util.List;

/**
 *
 */
public class JsonLocationTemplate
  extends JsonTemplate<JsonLocation>
  implements JsonLocationOperations {
  
  public static final String TABLE_NAME = "cmn_location";
  
  public JsonLocationTemplate(RestClient rc) {
    super(rc, TABLE_NAME, JsonLocation.class);
  }
  
  public JsonLocationTemplate(RestClient rc, Iterable<String> fns) {
    super(rc, TABLE_NAME, fns, JsonLocation.class);
  }
  
  public JsonLocation findByName(String name) {
    Params params = new TableParams.Builder()
      .queryTemplate("name=${0}")
      .queryValues(name)
      .build();
    return findOne(params);
  }
  
  public List<JsonLocation> findRoots() {
    Params params = new TableParams.Builder()
      .query("parentISEMPTY")
      .build();
    return findMany(params);
  }
  
  public JsonEntities findRootEntities() {
    Params params = new TableParams.Builder()
      .query("parentISEMPTY")
      .build();
    return findManyEntities(params);
  }
  
}
