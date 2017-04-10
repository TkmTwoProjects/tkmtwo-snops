package com.tkmtwo.snops.dictionary;

import com.tkmtwo.snops.TableOperations;
import java.util.List;

/**
 *
 */
public interface SysDictionaryOperations
  extends TableOperations<SysDictionary> {
  
  List<SysDictionary> findActive(SysDbObject sdo);
  List<SysDictionary> findActive(String tableName);
  
  
  
}
