package com.tkmtwo.snops.map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MappedEntity {
  
  private String serviceNowInstanceName;
  private String serviceNowTableName;


  
  @JsonProperty("sys_id")
  private String sysId;
  
  @JsonProperty("sys_mod_count")
  private int sysModCount;
  
  @JsonProperty("sys_created_on")
  private LocalDateTime sysCreatedOn;
  
  @JsonProperty("sys_created_by")
  private String sysCreatedBy;
  
  @JsonProperty("sys_updated_on")
  private LocalDateTime sysUpdatedOn;
  
  @JsonProperty("sys_updated_by")
  private String sysUpdatedBy;
  
  
  public String getServiceNowInstanceName() { return serviceNowInstanceName; }
  public void setServiceNowInstanceName(String s) { serviceNowInstanceName = s; }
  
  public String getServiceNowTableName() { return serviceNowTableName; }
  public void setServiceNowTableName(String s) { serviceNowTableName = s; }
  
  public String getSysId() { return sysId; }
  public void setSysId(String s) { sysId = s; }
  
  public int getSysModCount() { return sysModCount; }
  public void setSysModCount(int i) { sysModCount = i; }
  
  public LocalDateTime getSysCreatedOn() { return sysCreatedOn; }
  public void setSysCreatedOn(LocalDateTime ldt) { sysCreatedOn = ldt; }
  
  public String getSysCreatedBy() { return sysCreatedBy; }
  public void setSysCreatedBy(String s) { sysCreatedBy = s; }
  
  public LocalDateTime getSysUpdatedOn() { return sysUpdatedOn; }
  public void setSysUpdatedOn(LocalDateTime ldt) { sysUpdatedOn = ldt; }
  
  public String getSysUpdatedBy() { return sysUpdatedBy; }
  public void setSysUpdatedBy(String s) { sysUpdatedBy = s; }
  
  
  
  
  
  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (!(o instanceof MappedEntity)) { return false; }
    
    MappedEntity impl = (MappedEntity) o;
    
    return
      Objects.equals(getServiceNowInstanceName(), impl.getServiceNowInstanceName())
      && Objects.equals(getServiceNowTableName(), impl.getServiceNowTableName())
      && Objects.equals(getSysId(), impl.getSysId());
  }
  
  
  @Override
  public int hashCode() {
    return Objects.hash(getServiceNowInstanceName(), getServiceNowTableName(), getSysId());
  }
  
  
}
