package com.tkmtwo.snops.json;


import com.tkmtwo.snops.TableOperations;

/**
 *
 */
public interface JsonLocationOperations
  extends TableOperations<JsonLocation> {

  JsonLocation findByName(String name);
  
}
