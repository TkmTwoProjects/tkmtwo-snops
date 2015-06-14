package com.tkmtwo.snops.json;

import com.google.common.collect.ImmutableList;
import com.tkmtwo.snops.client.RestClient;


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
    return findOne("name=${0}", ImmutableList.of(name));
  }
  
}
