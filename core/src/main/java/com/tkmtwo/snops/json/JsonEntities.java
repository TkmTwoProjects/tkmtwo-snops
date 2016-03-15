package com.tkmtwo.snops.json;

import static com.google.common.base.ServiceNowConditions.checkSysId;
import static com.google.common.base.TkmTwoStrings.isBlank;

//import com.google.common.collect.ImmutableSet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.MoreObjects;
import com.tkmtwo.snops.time.DateTimeFormatters;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 *
 *
 */
public class JsonEntities {
  
  @JsonProperty("instance_name")
  private String serviceNowInstanceName;
  
  @JsonProperty("table_name")
  private String serviceNowTableName;
  
  @JsonProperty("object_nodes")
  private List<ObjectNode> objectNodes;
  
  
  public JsonEntities() { }
  
  public JsonEntities(String in, String tn) {
    setServiceNowInstanceName(in);
    setServiceNowTableName(tn);
  }
  
  public JsonEntities(String in, String tn, List<ObjectNode> ons) {
    setServiceNowInstanceName(in);
    setServiceNowTableName(tn);
    setObjectNodes(ons);
  }
  
  /*
  public JsonEntities(JsonEntities te) {
    setServiceNowInstanceName(te.getServiceNowInstanceName());
    setServiceNowTableName(te.getServiceNowTableName());
    setObjectNode(te.getObjectNode().deepCopy());
  }
  */
  
  
  
  public String getServiceNowInstanceName() { return serviceNowInstanceName; }
  public void setServiceNowInstanceName(String s) { serviceNowInstanceName = s; }
  
  public String getServiceNowTableName() { return serviceNowTableName; }
  public void setServiceNowTableName(String s) { serviceNowTableName = s; }
  
  public List<ObjectNode> getObjectNodes() {
    if (objectNodes == null) {
      objectNodes = new ArrayList<>();
    }
    return objectNodes;
  }
  public void setObjectNodes(List<ObjectNode> ons) {
    objectNodes = ons;
  }
  
  
  
  /*
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
  */
  
  
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("serviceNowInstanceName", getServiceNowInstanceName())
      .add("serviceNowTableName", getServiceNowTableName())
      .add("objectNodes", String.valueOf(getObjectNodes().size()))
      .toString();
  }
  
  
}
