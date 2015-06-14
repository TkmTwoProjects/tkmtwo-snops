package com.tkmtwo.snops.json;

import static com.google.common.base.ServiceNowConditions.checkSysId;
import static com.google.common.base.TkmTwoStrings.isBlank;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.tkmtwo.snops.time.DateTimeFormatters;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

/**
 *
 *
 *
 */
public class JsonEntity {
  
  public static final Set<String> EMPTY_FIELD_NAMES = ImmutableSet.of();
  
  private String instanceName;
  private String tableName;
  private ObjectNode objectNode;
  
  
  public JsonEntity() { }
  
  public JsonEntity(String tn) {
    setTableName(tn);
  }
  
  public JsonEntity(String tn, ObjectNode on) {
    setTableName(tn);
    setObjectNode(on);
  }
  
  public JsonEntity(JsonEntity te) {
    setInstanceName(te.getInstanceName());
    setTableName(te.getTableName());
    setObjectNode(te.getObjectNode().deepCopy());
  }
  
  
  
  
  public String getInstanceName() { return instanceName; }
  public void setInstanceName(String s) { instanceName = s; }
  
  public String getTableName() { return tableName; }
  public void setTableName(String s) { tableName = s; }
  
  public ObjectNode getObjectNode() {
    if (objectNode == null) {
      objectNode = new ObjectNode(JsonNodeFactory.instance);
    }
    return objectNode;
  }
  public void setObjectNode(ObjectNode on) {
    objectNode = on;
  }
  
  
  
  public String getSysId() {
    String sysId = getString("sys_id");
    if (!isBlank(sysId)) { return checkSysId(sysId); }
    return null;
  }
  
  public String getString(String s) {
    if (getObjectNode().hasNonNull(s)) {
      return getObjectNode().get(s).asText();
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
      .add("objectNode", getObjectNode())
      .toString();
  }
  
  
}
