package com.tkmtwo.snops.dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.tkmtwo.snops.map.MappedEntity;

/**
 * A ServiceNow table
 *
 */
public class SysGlideObject
  extends MappedEntity {
  
  @JsonProperty("name")
  private String name;
  
  @JsonProperty("label")
  private String label;
  
  @JsonProperty("scalar_type")
  private String scalarType;
  
  
  
  public String getName() { return name; }
  public void setName(String s) { name = s; }
  
  public String getLabel() { return label; }
  public void setLabel(String s) { label = s; }
  
  public String getScalarType() { return scalarType; }
  public void setScalarType(String s) { scalarType = s; }
  
  
  
  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }
  
  @Override
  public int hashCode() {
    return super.hashCode();
  }
  
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("serviceNowInstanceName", getServiceNowInstanceName())
      .add("serviceNowTableName", getServiceNowTableName())
      .add("sysId", getSysId())
      .add("name", getName())
      .add("label", getLabel())
      .add("scalarType", getScalarType())
      .toString();
  }
  
}
