package com.tkmtwo.snops.dictionary;


//import static com.google.common.base.TkmTwoConditions.checkNotBlank;

import com.google.common.collect.ImmutableMap;
import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.snops.client.RestClient;
import com.tkmtwo.snops.client.TableParams;
import com.tkmtwo.snops.map.MappedTemplate;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SysGlideObjectTemplate
  extends MappedTemplate<SysGlideObject>
  implements SysGlideObjectOperations {

  private static final String TABLE_NAME = "sys_glide_object";

  
  public SysGlideObjectTemplate(RestClient rc) {
    super(rc, TABLE_NAME, SysGlideObject.class);
  }
  
  
  public List<SysGlideObject> findAll() {
    Params params = new TableParams.Builder()
      .query("nameISNOTEMPTY")
      .build();
    return findMany(params);
  }
  
  
  public SysGlideObject findByName(String name) {
    Params params = new TableParams.Builder()
      .queryTemplate("name=${0}")
      .queryValues(name)
      .build();
    return findOne(params);
  }
  
  public Map<String, SysGlideObject> mapByName() {
    ImmutableMap.Builder<String, SysGlideObject> imb =
      new ImmutableMap.Builder<>();
    for (SysGlideObject sgo : findAll()) {
      imb.put(sgo.getName(), sgo);
    }
    return imb.build();
  }
  
  
}
