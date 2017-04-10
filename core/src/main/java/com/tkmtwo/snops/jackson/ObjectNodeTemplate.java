package com.tkmtwo.snops.jackson;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.base.TkmTwoConditions.checkNotBlank;
import static com.google.common.base.TkmTwoStrings.isBlank;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableList;
import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.snops.IncorrectResultSizeException;
import com.tkmtwo.snops.TableOperations;
import com.tkmtwo.snops.client.AbstractTableTemplate;
import com.tkmtwo.snops.client.RestClient;
import com.tkmtwo.snops.client.TableParams;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;


/**
 *
 *
 *
 */
public class ObjectNodeTemplate
  extends AbstractTableTemplate
  implements TableOperations<ObjectNode> {

  private List<ObjectNodeVisitor> createVisitors = null;
  private List<ObjectNodeVisitor> readVisitors = null;
  private List<ObjectNodeVisitor> updateVisitors = null;
  
  
  
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
                       
  public void afterPropertiesSet() {
    super.afterPropertiesSet();
    
    if (getCreateVisitors() == null) {
      logger.info("No create visitors declared, adding FieldRemovingObjectNodeVisitor.GLOBAL_DEFAULT_FIELDS_REMOVER");
      createVisitors = ImmutableList.of(FieldRemovingObjectNodeVisitor.GLOBAL_DEFAULT_FIELDS_REMOVER);
    }
    if (getReadVisitors() == null) {
      logger.info("No read visitors declared.");
      readVisitors = ImmutableList.of();
    }
    if (getUpdateVisitors() == null) {
      logger.info("No update visitors declared, adding FieldRemovingObjectNodeVisitor.GLOBAL_DEFAULT_FIELDS_REMOVER");
      updateVisitors = ImmutableList.of(FieldRemovingObjectNodeVisitor.GLOBAL_DEFAULT_FIELDS_REMOVER);
    }
    
    
    
  }
  

  public List<ObjectNodeVisitor> getCreateVisitors() { return createVisitors; }
  public void setCreateVisitors(List<ObjectNodeVisitor> l) { createVisitors = ImmutableList.copyOf(l); }

  public List<ObjectNodeVisitor> getReadVisitors() { return readVisitors; }
  public void setReadVisitors(List<ObjectNodeVisitor> l) { readVisitors = ImmutableList.copyOf(l); }

  public List<ObjectNodeVisitor> getUpdateVisitors() { return updateVisitors; }
  public void setUpdateVisitors(List<ObjectNodeVisitor> l) { updateVisitors = ImmutableList.copyOf(l); }
  
  
  
  private static String getString(ObjectNode on, String s) {
    checkNotNull(on, "Need an ObjectNode.");
    checkNotNull(s, "Need a field name.");
    if (on.hasNonNull(s)) {
      return on.get(s).asText();
    }
    return null;
  }
  
  private void visit(ObjectNode on, List<ObjectNodeVisitor> onvs) {
    if (on == null) { return; }
    if (onvs == null || onvs.isEmpty()) { return; }
    for (ObjectNodeVisitor onv : onvs) {
      onv.visit(on);
    }
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
    visit(on, getCreateVisitors());
    
    if (logger.isTraceEnabled()) {
      logger.trace("{} create() creating {}", getRestClient().getUserSummary(), on);
    }
    
    Params params = new Params();
    params.putAll(TableParams.SYSPARM_FIELDS, getFieldNames());

    URI thisUri = buildUri(params);
    if (logger.isTraceEnabled()) {
      logger.trace("{} create() using URI {}", getRestClient().getUserSummary(), thisUri);
    }
    
    
    //ObjectNode objectNode =
    //  getRestClient().getRestTemplate().postForObject(thisUri, on, ObjectNode.class);
    ObjectNode objectNode = 
      getRestClient().getRetryTemplate().execute(new RetryCallback<ObjectNode, RestClientException>() {
          public ObjectNode doWithRetry(RetryContext context) throws RestClientException {
            return getRestClient().getRestTemplate().postForObject(thisUri, on, ObjectNode.class);
          }
        });
    
    if (logger.isTraceEnabled()) {
      logger.trace("{} create(t) received JSON {}", getRestClient().getUserSummary(), objectNode);
    }
    
    if (objectNode == null) { return null; }
    visit(objectNode, getReadVisitors());
    return (ObjectNode) objectNode.get(CONTAINER_NODE_NAME);
  }
  

  public ObjectNode update(ObjectNode on) {
    checkNotNull(on, "Need an ObjectNode to update.");
    visit(on, getUpdateVisitors());
    
    String sysId = getString(on, "sys_id");
    checkNotBlank(sysId, "ObjectNode needs a sysId to be updated.");
    
    if (logger.isTraceEnabled()) {
      logger.trace("{} update() updating {}", getRestClient().getUserSummary(), on);
    }
    
    //overridding, we don't want to return a bunch of fields, we're
    //going to get() it in the end.
    Params params = new Params();
    params.put(TableParams.SYSPARM_FIELDS, "sys_id");
    
    URI thisUri = buildUri(sysId, params);
    if (logger.isTraceEnabled()) {
      logger.trace("{} update() using URI {}", getRestClient().getUserSummary(), thisUri);
    }
    
    String sr = getRestClient().getRetryTemplate().execute(new RetryCallback<String, RestClientException>() {
        public String doWithRetry(RetryContext context) throws RestClientException {
          getRestClient().getRestTemplate().put(thisUri, on);
          return "UPDATED";
        }
      });
    logger.trace("update() response was {}", nullToEmpty(sr));

    
    return get(sysId);
  }

  


  public ObjectNode get(String sysId) {
    checkNotBlank(sysId, "Need a sysId to retrieve.");
    if (logger.isTraceEnabled()) {
      logger.trace("{} get() getting {}", getRestClient().getUserSummary(), sysId);
    }
    
    Params params = new Params();
    if (!getFieldNames().isEmpty()) {
      logger.trace("fieldNames IS NOT empty, so I'm adding them.");
      params.putAll(TableParams.SYSPARM_FIELDS, getFieldNames());
    } else {
      logger.trace("fieldNames IS empty, nothing to add");
    }
    
    URI thisUri = buildUri(sysId, params);
    if (logger.isTraceEnabled()) {
      logger.trace("{} get() using URI {}", getRestClient().getUserSummary(), thisUri);
    }
    
    ObjectNode objectNode =
      getRestClient().getRetryTemplate().execute(new RetryCallback<ObjectNode, RestClientException>() {
          public ObjectNode doWithRetry(RetryContext context) throws RestClientException {
            return getRestClient().getRestTemplate().getForObject(thisUri, ObjectNode.class);
          }
        });
    
    if (logger.isTraceEnabled()) {
      logger.trace("{} get() received JSON {}", getRestClient().getUserSummary(), objectNode);
    }
    
    if (objectNode == null) { return null; }
    visit(objectNode, getReadVisitors());
    return (ObjectNode) objectNode.get(CONTAINER_NODE_NAME);
  }
  
  
  
  
  
  
  public List<ObjectNode> getMany(Params params) {
    List<ObjectNode> l = findMany(params);
    if (l.isEmpty()) {
      throw new IncorrectResultSizeException("Expected more than zero records from query.");
    }
    return l;
  }

  
  public ObjectNode  getOne(Params params) {
    ObjectNode on = findOne(params);
    if (on == null) {
      throw new IncorrectResultSizeException(1, 0);
    }
    return on;
  }
  
  
  public ObjectNode findOne(Params params) {
    List<ObjectNode> l = findMany(params);
    if (l.size() == 0) { return null; }
    if (l.size() == 1) { return l.get(0); }
    throw new IncorrectResultSizeException(1, l.size());
  }
  
  
  public List<ObjectNode> findMany(Params params) {
    checkNotNull(params, "Need some params.");
    
    if (logger.isTraceEnabled()) {
      logger.trace("{} findMany() using params({})", getRestClient().getUserSummary(), params);
    }
    
    //params.put(TableParams.SYSPARM_QUERY, qs);
    params.putAll(TableParams.SYSPARM_FIELDS, getFieldNames());

    URI thisUri = buildUri(params);
    if (logger.isTraceEnabled()) {
      logger.trace("{} findMany() using URI {}", getRestClient().getUserSummary(), thisUri);
    }
    
    ObjectNode resultNode = null;
    try {
      //resultNode = getRestClient().getRestTemplate().getForObject(thisUri, ObjectNode.class);
      resultNode = getRestClient().getRetryTemplate().execute(new RetryCallback<ObjectNode, RestClientException>() {
          public ObjectNode doWithRetry(RetryContext context) throws RestClientException {
            return getRestClient().getRestTemplate().getForObject(thisUri, ObjectNode.class);
          }
        });
      
      if (logger.isTraceEnabled()) {
        logger.trace("{} findMany() received JSON {}", getRestClient().getUserSummary(), resultNode);
      }
    } catch (HttpClientErrorException hcee) {
      if (HttpStatus.NOT_FOUND == hcee.getStatusCode()) {
        return ImmutableList.of();
      }
      throw hcee;
    } catch (Exception ex) {
      throw ex;
    }

    List<ObjectNode> returnList = new ArrayList<>();
    for (JsonNode jn : resultNode.get(CONTAINER_NODE_NAME)) {
      returnList.add((ObjectNode) jn);
    }
    
    for (ObjectNode on : returnList) {
      visit(on, getReadVisitors());
    }
    
    logger.trace("{} findMany() found {} results.", getRestClient().getUserSummary(), String.valueOf(returnList.size()));
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
    
    String sr = getRestClient().getRetryTemplate().execute(new RetryCallback<String, RestClientException>() {
        public String doWithRetry(RetryContext context) throws RestClientException {
          getRestClient().getRestTemplate().delete(thisUri);
          return "DELETED";
        }
      });
    logger.trace("delete() response was {}", nullToEmpty(sr));
    
    
  }
  
  
}
