package com.tkmtwo.snops;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.TkmTwoConditions.checkNotBlank;
import static com.google.common.base.TkmTwoJointers.MAP_JOINER;
//import static com.google.common.base.TkmTwoStrings.isBlank;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
//import com.google.common.collect.ImmutableSet;
//import com.tkmtwo.common.java.net.URIs;
//import com.tkmtwo.common.java.net.UserInfo;
//import java.io.Serializable;
//import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *
 *
 */
public class InstanceKeyedMap<T> {
  
  protected Logger logger = LoggerFactory.getLogger(getClass());
  
  private Map<Instance, T> valuesMap = new HashMap<>();
  private Instance singleInstance = null;
  private T singleValue = null;
  
  private Map<Instance, T> getValuesMap() {
    if (valuesMap == null) {
      valuesMap = new HashMap<>();
    }
    
    return valuesMap;
  }

  
  private void addToValuesMap(Instance i, T t) {
    checkNotNull(i, "Need an Instance.");
    checkNotNull(t, "Need a T.");
    getValuesMap().put(i, t);
    if (getValuesMap().size() == 1) {
      logger.info("Setting single value instance and value {}/{}", i, t);
      singleInstance = i;
      singleValue = t;
    } else {
      logger.info("Unsetting single value instance and value.");
      singleInstance = null;
      singleValue = null;
    }
  }

  /*
  public void add(String uri) {
    URI u = URIs.valueOf(uri);
    UserInfo ui = URIs.getUserInfo(u);
    
    add(new Instance(u.getHost()), new T(ui.getUserName(), ui.getPassword()));
  }
  */
  
  public void add(Instance i, T t) {
    addToValuesMap(i, t);
  }




  public Instance getInstance() { return singleInstance; }
  
  public T get() { return singleValue; }
  
  public T get(Instance i) {
    if (singleValue != null) { return singleValue; }
    return getValuesMap().get(i);
  }
  public T get(String instanceName) {
    if (singleValue != null) { return singleValue; }
    String in = checkNotBlank(instanceName);
    
    for (Map.Entry<Instance, T> me : getValuesMap().entrySet()) {
      if (in.equals(me.getKey().getName())) { return me.getValue(); }
      if (in.equals(me.getKey().getFqdn())) { return me.getValue(); }
    }
    
    return null;
  }
  
  
  public List<Instance> getInstances() {
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
