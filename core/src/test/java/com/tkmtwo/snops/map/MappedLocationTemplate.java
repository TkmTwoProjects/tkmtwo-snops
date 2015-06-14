package com.tkmtwo.snops.map;


import com.google.common.collect.ImmutableList;
import com.tkmtwo.snops.client.RestClient;


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
    return findOne("name=${0}", ImmutableList.of(name));
  }
  
}
