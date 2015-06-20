package com.tkmtwo.snops.json;

import static com.google.common.base.Preconditions.checkNotNull;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.snops.TableOperations;
import com.tkmtwo.snops.client.AbstractTableTemplate;
import com.tkmtwo.snops.client.RestClient;
import com.tkmtwo.snops.jackson.ObjectNodeTemplate;
import java.util.ArrayList;
import java.util.List;


/**
 *
 *
 *
 */
public class JsonTemplate<T extends JsonEntity>
  extends AbstractTableTemplate
  implements TableOperations<T> {
  
  
  private Class<T> tableClass;
  private TableOperations<ObjectNode> onOps = null;
  
  
  protected JsonTemplate(RestClient rc,
                         String p,
                         String tn,
                         Class<T> tc) {
    super(rc, p, tn);
    setTableClass(tc);
  }
  protected JsonTemplate(RestClient rc,
                         String p,
                         String tn,
                         Iterable<String> fns,
                         Class<T> tc) {
    super(rc, p, tn, fns);
    setTableClass(tc);
  }

  protected JsonTemplate(RestClient rc,
                         String tn,
                         Class<T> tc) {
    super(rc, tn);
    setTableClass(tc);
  }
  
  protected JsonTemplate(RestClient rc,
                         String tn,
                         Iterable<String> fns,
                         Class<T> tc) {
    super(rc, tn, fns);
    setTableClass(tc);
  }
  
  protected Class<T> getTableClass() { return tableClass; }
  protected void setTableClass(Class<T> c) { tableClass = checkNotNull(c); }
  
  
  public void afterPropertiesSet() {
    super.afterPropertiesSet();
    if (onOps == null) {
      ObjectNodeTemplate ont =
        new ObjectNodeTemplate(getRestClient(),
                               getApiPath(),
                               getTableName(),
                               getFieldNames());
      ont.afterPropertiesSet();
      onOps = ont;
    }
  }  
  
  
  
  
  public T save(T t) {
    return newTee(onOps.save(t.getObjectNode()));
  }
  public T create(T t) {
    return newTee(onOps.create(t.getObjectNode()));
  }
  public T update(T t) {
    return newTee(onOps.update(t.getObjectNode()));
  }
  public T get(String sysId) {
    return newTee(onOps.get(sysId));
  }
  public void delete(T t) {
    if (t == null) { return; }
    delete(t.getSysId());
  }
  public void delete(String sysId) {
    onOps.delete(sysId);
  }
  
  
  
    
  public T getOne(Params params) {
    return newTee(onOps.getOne(params));
  }
  
  
  public List<T> getMany(Params params) {
    return newTees(onOps.getMany(params));
  }



  public T findOne(Params params) {
    return newTee(onOps.findOne(params));
  }
  
  
  
  public List<T> findMany(Params params) {
    return newTees(onOps.findMany(params));
  }

  
  
  @SuppressWarnings("unchecked")
  private T newTee(ObjectNode on) {
    
    T t = null;
    if (on == null) { return t; }
    try {
      t = (T) getTableClass().newInstance();
    } catch (InstantiationException | IllegalAccessException ex) {
      throw new RuntimeException("Could not instantiate.", ex);
    }
    t.setInstanceName(getRestClient().getInstance().getName());
    t.setTableName(getTableName());
    t.setObjectNode(on);
    return t;
  }
  
  
  @SuppressWarnings("unchecked")
  private List<T> newTees(List<ObjectNode> ons) {
    List<T> tees = new ArrayList<>(ons.size());
    for (ObjectNode on : ons) {
      tees.add(newTee(on));
    }
    return tees;
  }
  
  
}
