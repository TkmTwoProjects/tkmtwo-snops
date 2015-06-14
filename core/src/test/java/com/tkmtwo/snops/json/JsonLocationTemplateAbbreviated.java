package com.tkmtwo.snops.json;

import com.google.common.collect.ImmutableList;
import com.tkmtwo.snops.client.RestClient;
import java.util.List;



/**
 *
 */
public final class JsonLocationTemplateAbbreviated
  extends JsonLocationTemplate {
  
  public static final List<String> FIELD_NAMES = ImmutableList.of("sys_id", "name", "street");
  
  public JsonLocationTemplateAbbreviated(RestClient rc) {
    super(rc, FIELD_NAMES);
  }
  
}
