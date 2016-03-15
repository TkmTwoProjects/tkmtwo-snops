package com.tkmtwo.snops.json;


import com.tkmtwo.snops.TableOperations;
import java.util.List;

/**
 *
 */
public interface JsonLocationOperations
  extends TableOperations<JsonLocation> {

  JsonLocation findByName(String name);
  List<JsonLocation> findRoots();
  JsonEntities findRootEntities();
  
}
