package com.tkmtwo.snops.json;

import static com.google.common.base.ServiceNowConditions.checkSysId;
import static com.google.common.base.TkmTwoStrings.isBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.MoreObjects;
//import com.google.common.collect.ImmutableSet;
import com.tkmtwo.snops.time.DateTimeFormatters;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
//import java.util.Set;

/**
 *
 *
 *
 */
public class JsonEntity {
  
  @JsonProperty("instance_name")
  private String serviceNowInstanceName;
  
  @JsonProperty("table_name")
  private String serviceNowTableName;
  
  @JsonProperty("object_node")
  private ObjectNode objectNode;
  
  
  public JsonEntity() { }
  
  public JsonEntity(String tn) {
    setServiceNowTableName(tn);
  }
  
  public JsonEntity(String tn, ObjectNode on) {
    setServiceNowTableName(tn);
    setObjectNode(on);
  }
  
  public JsonEntity(JsonEntity te) {
    setServiceNowInstanceName(te.getServiceNowInstanceName());
    setServiceNowTableName(te.getServiceNowTableName());
    setObjectNode(te.getObjectNode().deepCopy());
  }
  
  
  
  
  public String getServiceNowInstanceName() { return serviceNowInstanceName; }
  public void setServiceNowInstanceName(String s) { serviceNowInstanceName = s; }
  
  public String getServiceNowTableName() { return serviceNowTableName; }
  public void setServiceNowTableName(String s) { serviceNowTableName = s; }
  
  public ObjectNode getObjectNode() {
    if (objectNode == null) {
      objectNode = new ObjectNode(JsonNodeFactory.instance);
    }
    return objectNode;
  }
  public void setObjectNode(ObjectNode on) {
    objectNode = on;
  }
  
  
  @JsonIgnore
  public String getSysId() {
    String sysId = getString("sys_id");
    if (!isBlank(sysId)) { return checkSysId(sysId); }
    return null;
  }
  
  public String getSysId(String s) {
    String sysId = getString(s);
    if (!isBlank(sysId)) { return checkSysId(sysId); }
    return null;
  }
  
  public String getString(String s) {
    if (getObjectNode().hasNonNull(s)) {
      return getObjectNode().get(s).asText();
    }
    return null;
  }
  
  public String getFirstString(String... names) {
    for (String name : names) {
      if (getObjectNode().has(name)) {
        return getString(name);
      }
    }
    return null;
  }
  
  public boolean getBoolean(String s) {
    if (getObjectNode().hasNonNull(s)) {
      return getObjectNode().get(s).asBoolean();
    }
    return false;
  }
  public int getInt(String s) {
    if (getObjectNode().hasNonNull(s)) {
      return getObjectNode().get(s).asInt();
    }
    return 0;
  }
  public long getLong(String s) {
    if (getObjectNode().hasNonNull(s)) {
      return getObjectNode().get(s).asLong();
    }
    return 0L;
  }
  public double getDouble(String s) {
    if (getObjectNode().hasNonNull(s)) {
      return getObjectNode().get(s).asDouble();
    }
    return 0.0;
  }
  public LocalDateTime getLocalDateTime(String s) {
    if (getObjectNode().hasNonNull(s)) {
      return LocalDateTime.parse(getObjectNode().get(s).asText(), DateTimeFormatters.LOCAL_DATETIME_EXTENDED);
    }
    return null;
  }
  public LocalDate getLocalDate(String s) {
    if (getObjectNode().hasNonNull(s)) {
      return LocalDate.parse(getObjectNode().get(s).asText(), DateTimeFormatters.LOCAL_DATE_EXTENDED);
    }
    return null;
  }
  public LocalTime getLocalTime(String s) {
    if (getObjectNode().hasNonNull(s)) {
      return LocalTime.parse(getObjectNode().get(s).asText(), DateTimeFormatters.LOCAL_TIME_EXTENDED);
    }
    return null;
  }
  
  
  
  public void put(String n, String v) {
    getObjectNode().put(n, v);
  }
  
  
  
  
  
  
  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    
    JsonEntity impl = (JsonEntity) o;
    
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
      .add("objectNode", getObjectNode())
      .toString();
  }
  
  
}
