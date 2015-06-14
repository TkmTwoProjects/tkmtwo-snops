package com.tkmtwo.snops.jackson;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.TkmTwoConditions.checkNotBlank;
import static com.google.common.base.TkmTwoJointers.COMMA_JOINER;
import static com.google.common.base.TkmTwoStrings.isBlank;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableList;
import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.interpolate.Interpolator;
import com.tkmtwo.interpolate.PositionalInterpolatorCallback;
import com.tkmtwo.snops.IncorrectResultSizeException;
import com.tkmtwo.snops.TableOperations;
import com.tkmtwo.snops.client.AbstractTableTemplate;
import com.tkmtwo.snops.client.RestClient;
import com.tkmtwo.snops.client.TableParams;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;



/**
 *
 *
 *
 */
public class ObjectNodeTemplate
  extends AbstractTableTemplate
  implements TableOperations<ObjectNode> {
  
  
  public ObjectNodeTemplate(RestClient rc, String p, String tn) {
    super(rc, p, tn);
  }
  public ObjectNodeTemplate(RestClient rc, String p, String tn, Iterable<String> fns) {
    super(rc, p, tn, fns);
  }
  public ObjectNodeTemplate(RestClient rc, String tn) {
    super(rc, tn);
  }
  public ObjectNodeTemplate(RestClient rc, String tn, Iterable<String> fns) {
    super(rc, tn, fns);
  }
                       
  
  
  

  private static String getString(ObjectNode on, String s) {
    checkNotNull(on, "Need an ObjectNode.");
    checkNotNull(s, "Need a field name.");
    if (on.hasNonNull(s)) {
      return on.get(s).asText();
    }
    return null;
  }
  
  public ObjectNode save(ObjectNode on) {
    checkNotNull(on, "Need an ObjectNode to save.");

    String sysId = getString(on, "sys_id");
    
    if (isBlank(sysId)) {
      return create(on);
    } else {
      return update(on);
    }
  }
  
  
  
  
  public ObjectNode create(ObjectNode on) {
    checkNotNull(on, "Need an ObjectNode to save.");
    
    if (logger.isTraceEnabled()) {
      logger.trace("create() creating {}", on);
    }
    
    Params params = new Params();
    params.putAll(TableParams.SYSPARM_FIELDS, getFieldNames());

    URI thisUri = buildUri(params);
    if (logger.isTraceEnabled()) {
      logger.trace("create() using URI {}", thisUri);
    }
    
    
    ObjectNode objectNode =
      getRestClient().getRestTemplate().postForObject(thisUri, on, ObjectNode.class);
    if (logger.isTraceEnabled()) {
      logger.trace("create(t) received JSON {}", objectNode);
    }

    return (ObjectNode) objectNode.get(CONTAINER_NODE_NAME);
  }
  

  public ObjectNode update(ObjectNode on) {
    checkNotNull(on, "Need an ObjectNode to update.");
    
    String sysId = getString(on, "sys_id");
    checkNotBlank(sysId, "ObjectNode needs a sysId to be updated.");
    
    if (logger.isTraceEnabled()) {
      logger.trace("update() updating {}", on);
    }
    
    //overridding, we don't want to return a bunch of fields, we're
    //going to get() it in the end.
    Params params = new Params();
    params.put(TableParams.SYSPARM_FIELDS, "sys_id");
    
    URI thisUri = buildUri(sysId, params);
    if (logger.isTraceEnabled()) {
      logger.trace("update() using URI {}", thisUri);
    }
    
    getRestClient().getRestTemplate().put(thisUri, on);
    return get(sysId);
  }

  


  public ObjectNode get(String sysId) {
    checkNotBlank(sysId, "Need a sysId to retrieve.");
    if (logger.isTraceEnabled()) {
      logger.trace("get() getting {}", sysId);
    }
    
    Params params = new Params();
    if (!getFieldNames().isEmpty()) {
      logger.trace("fieldNames is NOT empty, so I'm adding some stuff..");
      params.putAll(TableParams.SYSPARM_FIELDS, getFieldNames());
    } else {
      logger.trace("fieldNames is indeed empty...nothing to add");
    }
    
    URI thisUri = buildUri(sysId, params);
    if (logger.isTraceEnabled()) {
      logger.trace("get() using URI {}", thisUri);
    }
    
    ObjectNode objectNode =
      getRestClient().getRestTemplate().getForObject(thisUri, ObjectNode.class);
    if (logger.isTraceEnabled()) {
      logger.trace("get() received JSON {}", objectNode);
    }

    return (ObjectNode) objectNode.get(CONTAINER_NODE_NAME);
  }
  
  
  public List<ObjectNode> getMany(String qs) {
    return getMany(qs, EMPTY_QS_VALUES, new Params());
  }
  public List<ObjectNode> getMany(String qs, Params params) {
    return getMany(qs, EMPTY_QS_VALUES, params);
  }
  public List<ObjectNode> getMany(String qsTemplate, List<String> qsValues) {
    return getMany(qsTemplate, qsValues, new Params());
  }
  public List<ObjectNode> getMany(String qsTemplate, List<String> qsValues, Params params) {
    List<ObjectNode> l = findMany(qsTemplate, qsValues, params);
    if (l.isEmpty()) {
      throw new IncorrectResultSizeException("Expected more than zero records from " + qsTemplate);
    }
    return l;
  }
  
  
  
  public ObjectNode getOne(String qs) {
    return getOne(qs, EMPTY_QS_VALUES, new Params());
  }
  public ObjectNode getOne(String qs, Params params) {
    return getOne(qs, EMPTY_QS_VALUES, params);
  }
  public ObjectNode getOne(String qsTemplate, List<String> qsValues) {
    return getOne(qsTemplate, qsValues, new Params());
  }
  public ObjectNode  getOne(String qsTemplate, List<String> qsValues, Params params) {
    ObjectNode on = findOne(qsTemplate, qsValues, params);
    if (on == null) {
      throw new IncorrectResultSizeException(1, 0);
    }
    return on;
  }
  
  
  
  
  public ObjectNode findOne(String qs) {
    return findOne(qs, EMPTY_QS_VALUES, new Params());
  }
  public ObjectNode findOne(String qs, Params params) {
    return findOne(qs, EMPTY_QS_VALUES, params);
  }
  public ObjectNode findOne(String qsTemplate, List<String> qsValues) {
    return findOne(qsTemplate, qsValues, new Params());
  }
  public ObjectNode findOne(String qsTemplate, List<String> qsValues, Params params) {
    List<ObjectNode> l = findMany(qsTemplate, qsValues, params);
    if (l.size() == 0) { return null; }
    if (l.size() == 1) { return l.get(0); }
    throw new IncorrectResultSizeException(1, l.size());
  }
  
  
  
  
  public List<ObjectNode> findMany(String qs) {
    return findMany(qs, EMPTY_QS_VALUES, new Params());
  }
  public List<ObjectNode> findMany(String qs, Params params) {
    return findMany(qs, EMPTY_QS_VALUES, params);
  }
  public List<ObjectNode> findMany(String qsTemplate, List<String> qsValues) {
    return findMany(qsTemplate, qsValues, new Params());
  }
  public List<ObjectNode> findMany(String qsTemplate, List<String> qsValues, Params params) {
    checkNotBlank(qsTemplate, "Need a query string template.");
    checkNotNull(qsValues, "Query string values may not be null.");
    checkNotNull(params, "Need some params.");
    
    if (logger.isTraceEnabled()) {
      logger.trace("findMany() using qsTemplate({}) and qsValues({}) and params({})",
                   qsTemplate, COMMA_JOINER.join(qsValues), params);
    }
    
    String qs = null;
    if (qsValues.isEmpty()) {
      qs = qsTemplate;
    } else {
      PositionalInterpolatorCallback<String> ic =
        new PositionalInterpolatorCallback<>(qsValues);
      qs = Interpolator.interpolate(qsTemplate, ic);
    }
    
    
    params.put(TableParams.SYSPARM_QUERY, qs);
    params.putAll(TableParams.SYSPARM_FIELDS, getFieldNames());

    URI thisUri = buildUri(params);
    if (logger.isTraceEnabled()) {
      logger.trace("findMany() using URI {}", thisUri);
    }
    
    ObjectNode resultNode = null;
    try {
      resultNode = getRestClient().getRestTemplate().getForObject(thisUri, ObjectNode.class);
      if (logger.isTraceEnabled()) {
        logger.trace("findMany() received JSON {}", resultNode);
      }
    } catch (HttpClientErrorException hcee) {
      if (HttpStatus.NOT_FOUND == hcee.getStatusCode()) {
        return ImmutableList.of();
      }
      throw hcee;
    }

    List<ObjectNode> returnList = new ArrayList<>();
    for (JsonNode jn : resultNode.get(CONTAINER_NODE_NAME)) {
      returnList.add((ObjectNode) jn);
    }
    return returnList;
  }

  
  
  public void delete(ObjectNode on) {
    if (on == null) {
      return;
    }
    
    delete(getString(on, "sys_id"));
  }
  public void delete(String sysId) {
    if (isBlank(sysId)) {
      logger.trace("delete() received blank sysId.  Returning.");
      return;
    }
    
    if (logger.isTraceEnabled()) {
      logger.trace("delete() deleting {}", sysId);
    }
    
    URI thisUri = buildUri(sysId);
    if (logger.isTraceEnabled()) {
      logger.trace("delete() using URI {}", thisUri);
    }
    
    getRestClient().getRestTemplate().delete(thisUri);
  }
  
  
}
