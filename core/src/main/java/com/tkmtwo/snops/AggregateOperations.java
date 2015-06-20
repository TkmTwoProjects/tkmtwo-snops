package com.tkmtwo.snops;

import com.tkmtwo.hc.uri.Params;
import java.util.List;


/**
 *
 *
 */
public interface AggregateOperations<T> {

  T getOne(Params params);
  List<T> getMany(Params params);
  
}
