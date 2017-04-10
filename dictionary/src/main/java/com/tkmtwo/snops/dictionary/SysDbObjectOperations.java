package com.tkmtwo.snops.dictionary;

import com.tkmtwo.snops.TableOperations;
import java.util.List;

/**
 *
 */
public interface SysDbObjectOperations
  extends TableOperations<SysDbObject> {
  
  List<SysDbObject> findAll();
  SysDbObject findByName(String name);
  
}
