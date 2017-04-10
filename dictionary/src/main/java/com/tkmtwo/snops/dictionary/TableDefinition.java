package com.tkmtwo.snops.dictionary;

import static com.google.common.base.TkmTwoStrings.isBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
//import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;

public final class TableDefinition {
  
  @JsonProperty("name")
  private String name;

  @JsonProperty("fields")
  private Map<String, FieldDefinition> fields;
  
  public TableDefinition() { ; }
  public TableDefinition(SysDbObject sdo, List<SysDictionary> sds) {
    setName(sdo.getName());
    setFieldsFromSysDictionaries(sds);
  }
  
  
  public String getName() { return name; }
  private void setName(String s) { name = s; }
  
  public Map<String, FieldDefinition> getFields() { return fields; }
  private void setFields(Map<String, FieldDefinition> l) { fields = l; }
  private void setFieldsFromSysDictionaries(List<SysDictionary> sds) {
    ImmutableMap.Builder<String, FieldDefinition> imb = new ImmutableMap.Builder<>();
    for (SysDictionary sd : sds) {
      imb.put(sd.getElement(), new FieldDefinition(sd));
    }
    setFields(imb.build());
  }
  /*
    ImmutableList.Builder<FieldDefinition> ilb = new ImmutableList.Builder<>();
    for (SysDictionary sd : sds) {
      if (isBlank(sd.getElement())) { continue; }
      ilb.add(new FieldDefinition(sd));
    }
    setFields(ilb.build());
  }
  */
  
  
  public FieldDefinition getField(String name) {
    if (isBlank(name)) { return null; }
    return getFields().get(name);
    /*
    for (FieldDefinition fd : getFields()) {
      if (name.equals(fd.getColumnName())) {
        return fd;
      }
    }
    
    return null;
    */
  }
  
  
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("name", getName())
      .add("numberOfFields", getFields().size())
      .toString();
  }

  
}
