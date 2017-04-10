package com.tkmtwo.snops.dictionary;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface DictionaryOperations {
  
  String getUserName();
  
  SysDictionaryOperations sysDictionaryOps();
  SysDbObjectOperations sysDbObjectOps();
  
  Map<String, SysDictionary> fieldsForTable(String tableName);
  //TableDefinition getTable(String tableName);
  
  List<SysDictionary> dataFieldsForTable(String tableName);
  TableDefinition tableDefinition(String tableName);
}
