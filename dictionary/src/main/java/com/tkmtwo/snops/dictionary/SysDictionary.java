package com.tkmtwo.snops.dictionary;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.MoreObjects;
import com.tkmtwo.snops.map.MappedEntity;


public class SysDictionary
  extends MappedEntity {
  
  @JsonProperty("active")
  private boolean active;
  
  @JsonProperty("column_label")
  private String columnLabel;
  
  @JsonProperty("name")
  private String name;
  
  @JsonProperty("element")
  private String element;
  
  @JsonProperty("internal_type")
  private String internalType;
  
  @JsonProperty("reference_type")
  private String referenceType;
  
  @JsonProperty("reference_key")
  private String referenceKey;
  
  @JsonProperty("reference")
  private String reference;
  
  @JsonProperty("max_length")
  private int maxLength;
  
  
  @JsonIgnore
  private ObjectNode objectNode;
  
  
  public boolean getActive() { return active; }
  public void setActive(boolean b) { active = b; }
  
  public String getColumnLabel() { return columnLabel; }
  public void setColumnLabel(String s) { columnLabel = s; }
  
  public String getName() { return name; }
  public void setName(String s) { name = s; }
  
  public String getElement() { return element; }
  public void setElement(String s) { element = s; }
  
  public String getInternalType() { return internalType; }
  public void setInternalType(String s) { internalType = s; }
  
  public String getReference() { return reference; }
  public void setReference(String s) { reference = s; }
  
  public String getReferenceType() { return referenceType; }
  public void setReferenceType(String s) { referenceType = s; }
  
  public String getReferenceKey() { return referenceKey; }
  public void setReferenceKey(String s) { referenceKey = s; }
  
  public int getMaxLength() { return maxLength; }
  public void setMaxLength(int i) { maxLength = i; }
  
  
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
      .add("active", getActive())
      .add("columnLabel", getColumnLabel())
      .add("name", getName())
      .add("element", getElement())
      .add("internalType", getInternalType())
      .add("reference", getReference())
      .add("referenceType", getReferenceType())
      .add("referenceKey", getReferenceKey())
      .add("maxLength", getMaxLength())
      .toString();
  }
  
}
