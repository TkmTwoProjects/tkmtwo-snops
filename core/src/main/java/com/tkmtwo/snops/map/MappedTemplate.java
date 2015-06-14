package com.tkmtwo.snops.map;

import static com.google.common.base.Preconditions.checkNotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.snops.TableOperations;
import com.tkmtwo.snops.UncategorizedException;
import com.tkmtwo.snops.client.AbstractTableTemplate;
import com.tkmtwo.snops.client.RestClient;
import com.tkmtwo.snops.jackson.ObjectNodeTemplate;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.reflections.ReflectionUtils;



/**
 *
 *
 *
 */
public class MappedTemplate<T extends MappedEntity>
  extends AbstractTableTemplate
  implements TableOperations<T> {
  
  private Class<T> tableClass;
  private TableOperations<ObjectNode> onOps = null;
  

  
  protected MappedTemplate(RestClient rc, String p, String tn, Class<T> tc) {
    super(rc, p, tn);
    setTableClass(tc);
  }
  protected MappedTemplate(RestClient rc, String p, String tn, Iterable<String> fns, Class<T> tc) {
    super(rc, p, tn, fns);
    setTableClass(tc);
  }
  protected MappedTemplate(RestClient rc, String tn, Class<T> tc) {
    super(rc, tn);
    setTableClass(tc);
  }
  protected MappedTemplate(RestClient rc, String tn, Iterable<String> fns, Class<T> tc) {
    super(rc, tn, fns);
    setTableClass(tc);
  }
  
  
  protected Class<T> getTableClass() { return tableClass; }
  protected void setTableClass(Class<T> c) {
    tableClass = checkNotNull(c);
    setFieldNames(getFieldNamesViaReflection());
  }
  
  
  public void afterPropertiesSet() {
    super.afterPropertiesSet();
    if (onOps == null) {
      ObjectNodeTemplate ont = new ObjectNodeTemplate(getRestClient(), getApiPath(), getTableName(), getFieldNames());
      ont.afterPropertiesSet();
      onOps = ont;
    }
  }
  
  /*  
  private String cn() {
    return getTableClass().getSimpleName();
  }
  */
  
  public T save(T t) {
    return newTee(onOps.save(newObjectNode(t)));
  }
  public T create(T t) {
    return newTee(onOps.create(newObjectNode(t)));
  }
  public T update(T t) {
    return newTee(onOps.update(newObjectNode(t)));
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
  
  
  public T getOne(String qs) {
    return newTee(onOps.getOne(qs));
  }
  public T getOne(String qs, Params params) {
    return newTee(onOps.getOne(qs, params));
  }
  public T getOne(String qsTemplate, List<String> qsValues) {
    return newTee(onOps.getOne(qsTemplate, qsValues));
  }
  public T getOne(String qsTemplate, List<String> qsValues, Params params) {
    return newTee(onOps.getOne(qsTemplate, qsValues, params));
  }
  
  public List<T> getMany(String qs) {
    return newTees(onOps.getMany(qs));
  }
  public List<T> getMany(String qs, Params params) {
    return newTees(onOps.getMany(qs, params));
  }
  public List<T> getMany(String qsTemplate, List<String> qsValues) {
    return newTees(onOps.getMany(qsTemplate, qsValues));
  }
  public List<T> getMany(String qsTemplate, List<String> qsValues, Params params) {
    return newTees(onOps.getMany(qsTemplate, qsValues, params));
  }
  
  public T findOne(String qs) {
    return newTee(onOps.findOne(qs));
  }
  public T findOne(String qs, Params params) {
    return newTee(onOps.findOne(qs, params));
  }
  public T findOne(String qsTemplate, List<String> qsValues) {
    return newTee(onOps.findOne(qsTemplate, qsValues));
  }
  public T findOne(String qsTemplate, List<String> qsValues, Params params) {
    return newTee(onOps.findOne(qsTemplate, qsValues, params));
  }
  
  public List<T> findMany(String qs) {
    return newTees(onOps.findMany(qs));
  }
  public List<T> findMany(String qs, Params params) {
    return newTees(onOps.findMany(qs, params));
  }
  public List<T> findMany(String qsTemplate, List<String> qsValues) {
    return newTees(onOps.findMany(qsTemplate, qsValues));
  }
  public List<T> findMany(String qsTemplate, List<String> qsValues, Params params) {
    return newTees(onOps.findMany(qsTemplate, qsValues, params));
  }


  
  @SuppressWarnings("unchecked")
  protected Set<String> getFieldNamesViaReflection() {
    Set<String> fieldNames = new HashSet<>();
    
    Set<Field> fields =
      ReflectionUtils.getAllFields(getTableClass(), ReflectionUtils.withAnnotation(JsonProperty.class));
    for (Field field : fields) {
      JsonProperty jp = field.getAnnotation(JsonProperty.class);
      fieldNames.add(jp.value());
    }
    
    return fieldNames;
  }

  
  @SuppressWarnings("unchecked")
  protected T newTee(ObjectNode on) {
    try {
      if (on == null) { return null; }
      
      T t =
        getRestClient()
        .getJacksonMessageConverter()
        .getObjectMapper()
        .treeToValue(on, getTableClass());
      t.setInstanceName(getRestClient().getInstance().getName());
      t.setTableName(getTableName());
      return t;
    } catch (JsonProcessingException jpe) {
      throw new UncategorizedException("Boom on newTee", jpe);
    }
    
  }
  
  @SuppressWarnings("unchecked")
  protected List<T> newTees(List<ObjectNode> ons) {
    List<T> tees = new ArrayList<>(ons.size());
    for (ObjectNode on : ons) {
      tees.add(newTee(on));
    }
    return tees;
  }

  protected ObjectNode newObjectNode(T t) {
    JsonNode jn =
      getRestClient()
      .getJacksonMessageConverter()
      .getObjectMapper()
      .valueToTree(t);
    return (ObjectNode) jn;
  }
    
    
  
}
