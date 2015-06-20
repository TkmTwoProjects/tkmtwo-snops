package com.tkmtwo.snops.map;


import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.snops.client.RestClient;
import com.tkmtwo.snops.client.TableParams;


/**
 *
 */
public class MappedLocationTemplate
  extends MappedTemplate<MappedLocation>
  implements MappedLocationOperations {

  public static final String TABLE_NAME = "cmn_location";
  
  public MappedLocationTemplate(RestClient rc) {
    super(rc, TABLE_NAME, MappedLocation.class);
  }
  
  public MappedLocation findByName(String name) {
    Params params = new TableParams.Builder()
      .queryTemplate("name=${0}")
      .queryValues(name)
      .build();
    return findOne(params);
  }
  
}
