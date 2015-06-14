package com.tkmtwo.snops.map;


import com.tkmtwo.snops.TableOperations;

/**
 *
 */
public interface MappedLocationOperations
  extends TableOperations<MappedLocation> {

  MappedLocation findByName(String name);
  
}
