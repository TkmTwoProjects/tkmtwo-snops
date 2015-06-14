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
  
  T getOne(String qs);
  T getOne(String qs, Params params);
  T getOne(String qsTemplate, List<String> qsValues);
  T getOne(String qsTemplate, List<String> qsValues, Params params);
  
  List<T> getMany(String qs);
  List<T> getMany(String qs, Params params);
  List<T> getMany(String qsTemplate, List<String> qsValues);
  List<T> getMany(String qsTemplate, List<String> qsValues, Params params);
  
  T findOne(String qs);
  T findOne(String qs, Params params);
  T findOne(String qsTemplate, List<String> qsValues);
  T findOne(String qsTemplate, List<String> qsValues, Params params);
  
  List<T> findMany(String qs);
  List<T> findMany(String qs, Params params);
  List<T> findMany(String qsTemplate, List<String> qsValues);
  List<T> findMany(String qsTemplate, List<String> qsValues, Params params);
  
}
