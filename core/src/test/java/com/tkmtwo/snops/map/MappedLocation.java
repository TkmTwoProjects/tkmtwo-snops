package com.tkmtwo.snops.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.tkmtwo.snops.time.DateTimeFormatters;
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
      Objects.equals(getServiceNowInstanceName(), impl.getServiceNowInstanceName())
      && Objects.equals(getServiceNowTableName(), impl.getServiceNowTableName())
      && Objects.equals(getSysId(), impl.getSysId());
  }
  
  
  @Override
  public int hashCode() {
    return Objects.hash(getServiceNowInstanceName(), getServiceNowTableName(), getSysId());
  }

  
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("serviceNowInstanceName", getServiceNowInstanceName())
      .add("serviceNowTableName", getServiceNowTableName())
      .add("sysId", getSysId())
      .add("sysModCount", getSysModCount())
      .add("sysCreatedOn",
           (getSysCreatedOn() == null)
           ? ""
           : DateTimeFormatters.LOCAL_DATETIME_EXTENDED.format(getSysCreatedOn()))
      .add("sysCreatedBy", getSysCreatedBy())
      .add("sysUpdatedOn",
           (getSysUpdatedOn() == null)
           ? ""
           : DateTimeFormatters.LOCAL_DATETIME_EXTENDED.format(getSysUpdatedOn()))
      .add("sysUpdatedBy", getSysUpdatedBy())
      .add("name", getName())
      .add("street", getStreet())
      .toString();
  }
  
}
