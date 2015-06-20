package com.tkmtwo.snops;

import com.tkmtwo.hc.uri.Params;
import java.util.List;


/**
 *
 *
 */
public interface TableOperations<T> {
  
  T save(T entity);
  T create(T entity);
  T update(T entity);
  T get(String sysId);
  void delete(String sysId);
  void delete(T t);
  
  T getOne(Params params);
  
  List<T> getMany(Params params);
  
  T findOne(Params params);
  
  List<T> findMany(Params params);
  
}
