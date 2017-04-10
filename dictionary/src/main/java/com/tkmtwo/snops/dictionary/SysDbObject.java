package com.tkmtwo.snops.dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.tkmtwo.snops.map.MappedEntity;


public class SysDbObject
  extends MappedEntity {
  
  @JsonProperty("name")
  private String name;
  
  @JsonProperty("sys_class_name")
  private String sysClassName;
  
  @JsonProperty("super_class")
  private String superClass;
  
  @JsonProperty("is_extendable")
  private boolean extendable;
  
  
  
  
  public String getName() { return name; }
  public void setName(String s) { name = s; }
  
  public String getSysClassName() { return sysClassName; }
  public void setSysClassName(String s) { sysClassName = s; }
  
  public String getSuperClass() { return superClass; }
  public void setSuperClass(String s) { superClass = s; }
  
  public boolean getExtendable() { return extendable; }
  public void setExtendable(boolean b) { extendable = b; }
  
  
  
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
      .add("sysClassName", getSysClassName())
      .add("superClass", getSuperClass())
      .add("extendable", getExtendable())
      .toString();
  }
  
}
