package com.tkmtwo.snops.dictionary;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;


public final class FieldDefinition {
  
  @JsonProperty("table_name")
  private String tableName;
  
  @JsonProperty("column_name")
  private String columnName;
  
  @JsonProperty("column_label")
  private String columnLabel;
  
  @JsonProperty("internal_type")
  private InternalType internalType;
  
  @JsonProperty("reference")
  private String reference;
  
  @JsonProperty("reference_key")
  private String referenceKey;
  
  @JsonProperty("max_length")
  private int maxLength;
  
  public FieldDefinition() { ; }
  public FieldDefinition(SysDictionary sd) {
    if (sd == null) { return; }
    setTableName(sd.getName());
    setColumnName(sd.getElement());
    setColumnLabel(sd.getColumnLabel());
    setInternalType(InternalType.from(sd.getInternalType()));
    setReference(sd.getReference());
    setReferenceKey(sd.getReferenceKey());
    setMaxLength(sd.getMaxLength());
  }

  public String getTableName() { return tableName; }
  private void setTableName(String s) { tableName = s; }
  
  public String getColumnName() { return columnName; }
  private void setColumnName(String s) { columnName = s; }
  
  public String getColumnLabel() { return columnLabel; }
  private void setColumnLabel(String s) { columnLabel = s; }
  
  public InternalType getInternalType() { return internalType; }
  private void setInternalType(InternalType it) { internalType = it; }
  //private void setInternalType(String s) { internalType = InternalType.from(s); }
  
  public String getReference() { return reference; }
  private void setReference(String s) { reference = s; }
  
  public String getReferenceKey() { return referenceKey; }
  private void setReferenceKey(String s) { referenceKey = s; }
  
  public int getMaxLength() { return maxLength; }
  private void setMaxLength(int i) { maxLength = i; }
 
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("tableName", getTableName())
      .add("columnName", getColumnName())
      .add("columnLabel", getColumnLabel())
      .add("internalType", getInternalType())
      .add("glideType", getInternalType().glideType())
      .add("reference", getReference())
      .add("referenceKey", getReferenceKey())
      .add("maxLength", getMaxLength())
      .toString();
  }


  
}
