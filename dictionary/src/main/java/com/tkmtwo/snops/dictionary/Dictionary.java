package com.tkmtwo.snops.dictionary;



public interface Dictionary {
  
  TableDefinition tableDefinition(String tableName);
  FieldDefinition fieldDefinition(String tableName, String fieldName);
  
  //SysDbObject sysDbObjectByName(String name);
  //SysDbObject sysDbObjectBySysId(String sid);
  
}
