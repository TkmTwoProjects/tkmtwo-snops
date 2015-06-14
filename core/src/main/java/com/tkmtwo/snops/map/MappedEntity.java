package com.tkmtwo.snops.map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;


/**
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MappedEntity {
  
  private String instanceName;
  private String tableName;


  
  @JsonProperty("sys_id")
  private String sysId;
  
  
  public String getInstanceName() { return instanceName; }
  public void setInstanceName(String s) { instanceName = s; }
  
  public String getTableName() { return tableName; }
  public void setTableName(String s) { tableName = s; }

  
  public String getSysId() { return sysId; }
  public void setSysId(String s) { sysId = s; }






  
  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (!(o instanceof MappedEntity)) { return false; }
    
    MappedEntity impl = (MappedEntity) o;
    
    return
      Objects.equals(getInstanceName(), impl.getInstanceName())
      && Objects.equals(getTableName(), impl.getTableName())
      && Objects.equals(getSysId(), impl.getSysId());
  }
  
  
  @Override
  public int hashCode() {
    return Objects.hash(getInstanceName(), getTableName(), getSysId());
  }

  
}
