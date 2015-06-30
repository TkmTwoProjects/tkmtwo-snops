package com.tkmtwo.snops.jackson;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FieldRemovingObjectNodeVisitor
  implements ObjectNodeVisitor {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  private List<String> fieldNames = ImmutableList.of();
  
  private static final List<String> GLOBAL_DEFAULT_FIELDS =
    ImmutableList.of("sys_mod_count",
                     "sys_created_on", "sys_created_by",
                     "sys_updated_on", "sys_updated_by");
  
  public static final ObjectNodeVisitor GLOBAL_DEFAULT_FIELDS_REMOVER =
    new FieldRemovingObjectNodeVisitor(GLOBAL_DEFAULT_FIELDS);
  
  public FieldRemovingObjectNodeVisitor(List<String> l) {
    setFieldNames(l);
  }
  private List<String> getFieldNames() {
    if (fieldNames == null) {
      fieldNames = ImmutableList.of();
    }
    return fieldNames;
  }
  private void setFieldNames(List<String> l) {
    fieldNames = ImmutableList.copyOf(l);
  }
  
  public void visit(ObjectNode on) {
    if (on == null) {
      logger.trace("ObjectNode is null.  Not removing anything.");
      return;
    }
    
    for (String fieldName : getFieldNames()) {
      if (on.remove(fieldName) != null) {
        logger.trace("Removed field '{}'.", fieldName);
      }
    }
    
  }
  
  
}
