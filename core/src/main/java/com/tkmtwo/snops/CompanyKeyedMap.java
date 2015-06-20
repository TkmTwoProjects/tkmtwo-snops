package com.tkmtwo.snops;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.TkmTwoConditions.checkNotBlank;
import static com.google.common.base.TkmTwoJointers.MAP_JOINER;

import com.google.common.base.CharMatcher;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *
 *
 */
public class CompanyKeyedMap<T> {
  
  protected Logger logger = LoggerFactory.getLogger(getClass());
  protected static final CharMatcher PATH_CHARS = CharMatcher.anyOf("/?\\ ");
  
  private Map<CompanyInstance, T> valuesMap = new HashMap<>();
  private CompanyInstance singleCompanyInstance = null;
  private T singleValue = null;
  
  
  
  private Map<CompanyInstance, T> getValuesMap() {
    if (valuesMap == null) {
      valuesMap = new HashMap<>();
    }
    return valuesMap;
  }
  
  private void addToValuesMap(CompanyInstance i, T t) {
    checkNotNull(i, "Need an CompanyInstance.");
    checkNotNull(t, "Need a value.");
    getValuesMap().put(i, t);
    if (getValuesMap().size() == 1) {
      logger.info("Setting single value CompanyInstance and value {}/{}", i, t);
      singleCompanyInstance = i;
      singleValue = t;
    } else {
      logger.info("Unsetting single value CompanyInstance and value.");
      singleCompanyInstance = null;
      singleValue = null;
    }
  }
  
  /*  
  public void add(String uri) {
    URI u = URIs.valueOf(uri);
    UserInfo ui = URIs.getUserInfo(u);

    add(new CompanyInstance(u.getHost(), PATH_CHARS.trimFrom(u.getPath())),
        new T(ui.getUserName(), ui.getPassword()));
  }
  */
  
  public void add(CompanyInstance i, T t) {
    addToValuesMap(i, t);
  }




  public CompanyInstance getInstance() { return singleCompanyInstance; }
  
  public T get() { return singleValue; }
  
  public T get(CompanyInstance ci) {
    if (singleValue != null) { return singleValue; }
    return getValuesMap().get(ci);
  }
  public T get(String instanceName, String companySysId) {
    if (singleValue != null) { return singleValue; }
    String in = checkNotBlank(instanceName);
    String cid = checkNotBlank(companySysId);
    
    for (Map.Entry<CompanyInstance, T> me : getValuesMap().entrySet()) {
      if (cid.equals(me.getKey().getCompanySysId())) {
        if (in.equals(me.getKey().getName())) { return me.getValue(); }
        if (in.equals(me.getKey().getFqdn())) { return me.getValue(); }
      }
    }
    
    return null;
  }
  
  
  public List<CompanyInstance> getCompanyInstances() {
    return ImmutableList.copyOf(getValuesMap().keySet());
  }
  
  public List<T> getValues() {
    return ImmutableList.copyOf(getValuesMap().values());
  }
  
  public int size() {
    return getValuesMap().size();
  }
  
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("valuesMap", MAP_JOINER.join(getValuesMap()))
      .toString();
  }

}
