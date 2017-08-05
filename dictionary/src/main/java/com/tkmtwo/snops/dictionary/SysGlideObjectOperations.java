package com.tkmtwo.snops.dictionary;

import com.tkmtwo.snops.TableOperations;
import java.util.List;
import java.util.Map;

/**
 *
 */
public interface SysGlideObjectOperations
  extends TableOperations<SysGlideObject> {
  
  List<SysGlideObject> findAll();
  SysGlideObject findByName(String name);

  Map<String, SysGlideObject> mapByName();

}
