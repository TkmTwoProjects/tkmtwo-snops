package com.tkmtwo.snops.dictionary;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;



public class CachingDictionary
  implements Dictionary, InitializingBean {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  private static final int DEFAULT_EXPIRE_MAGNITUDE = 10;
  private static final TimeUnit DEFAULT_EXPIRE_UNITS = TimeUnit.MINUTES;

  private DictionaryOperations dictionaryOps;
  
  private LoadingCache<String, TableDefinition> tableDefinitionCache;
  
  
  public CachingDictionary() { ; }
  public CachingDictionary(DictionaryOperations dops) {
    setDictionaryOperations(dops);
  }
  
  public void setDictionaryOperations(DictionaryOperations sdo) { dictionaryOps = sdo; }
  protected DictionaryOperations getDictionaryOperations() { return dictionaryOps; }
  
  
  public void afterPropertiesSet() {
    checkNotNull(getDictionaryOperations(), "DictionaryOperations is null");
    
    logger.info("CachingDictionary starting up with expiry of {} {}",
                String.valueOf(DEFAULT_EXPIRE_MAGNITUDE), DEFAULT_EXPIRE_UNITS);
    
    
    
    
    
    tableDefinitionCache = CacheBuilder.newBuilder()
      //.expireAfterAccess(DEFAULT_EXPIRE_MAGNITUDE, DEFAULT_EXPIRE_UNITS)
      .build(
             new CacheLoader<String, TableDefinition>() {
               public TableDefinition load(String tableName) { // no checked exception
                 logger.info("Loading TableDefinition for {} into tableDefinitionCache.", tableName);
                 return getDictionaryOperations().tableDefinition(tableName);
               }
             });
    
  }
  
  
  public void invalidateAll() {
    tableDefinitionCache.invalidateAll();
  }
  
  public TableDefinition tableDefinition(String tableName) {
    return tableDefinitionCache.getUnchecked(tableName);
  }
  public FieldDefinition fieldDefinition(String tableName, String fieldName) {
    TableDefinition td = tableDefinition(tableName);
    return td.getField(fieldName);
  }
  
  
}
