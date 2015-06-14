package com.tkmtwo.snops.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import java.util.Objects;

public class MappedLocation
  extends MappedEntity {

  @JsonProperty("name")
  private String name;
  
  @JsonProperty("street")
  private String street;

  
  public String getName() { return name; }
  public void setName(String s) { name = s; }
  
  public String getStreet() { return street; }
  public void setStreet(String s) { street = s; }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    //if (!(o instanceof MappedLocation)) { return false; }
    if (o == null || getClass() != o.getClass()) { return false; }    
    MappedLocation impl = (MappedLocation) o;
    
    return
      Objects.equals(getInstanceName(), impl.getInstanceName())
      && Objects.equals(getTableName(), impl.getTableName())
      && Objects.equals(getSysId(), impl.getSysId());
  }
  
  
  @Override
  public int hashCode() {
    return Objects.hash(getInstanceName(), getTableName(), getSysId());
  }

  
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("instanceName", getInstanceName())
      .add("tableName", getTableName())
      .add("name", getName())
      .add("street", getStreet())
      .toString();
  }
  
}
