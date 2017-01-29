package com.tkmtwo.snops.jackson;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.TkmTwoConditions.checkNotBlank;
import static com.google.common.base.TkmTwoJointers.COMMA_JOINER;
import static com.google.common.base.TkmTwoStrings.isBlank;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.hc.uri.URIBuilder;
import com.tkmtwo.snops.IncorrectResultSizeException;
import com.tkmtwo.snops.client.RestClient;
import com.tkmtwo.snops.client.TableParams;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.web.client.RestClientException;



/**
 *
 *
 *
 */
public class SimpleObjectNodeTemplate
  implements InitializingBean {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass()); 

  public static final Set<String> EMPTY_FIELD_NAMES = ImmutableSet.of();
  
  public static final String DEFAULT_API_PATH = "api/now/table";
  public static final String CONTAINER_NODE_NAME = "result";
  
  private static final Params EMPTY_PARAMS = new Params();
  
  private RestClient restClient;
  private Set<String> fieldNames;
  
  private List<ObjectNodeVisitor> createVisitors = null;
  private List<ObjectNodeVisitor> readVisitors = null;
  private List<ObjectNodeVisitor> updateVisitors = null;

  
  
  public SimpleObjectNodeTemplate(RestClient rc) {
    this(rc, EMPTY_FIELD_NAMES);
  }
  public SimpleObjectNodeTemplate(RestClient rc, Iterable<String> fns) {
    setRestClient(rc);
    setFieldNames(fns);
  }
  
  protected RestClient getRestClient() { return restClient; }
  protected void setRestClient(RestClient rc) { restClient = rc; }


  public void addFieldNames(Iterable<String> is) {
    if (is == null) { return; }
    ImmutableSet.Builder<String> isb = new ImmutableSet.Builder<String>();
    isb.addAll(getFieldNames());
    isb.addAll(is);
    fieldNames = isb.build();
  }
  public void setFieldNames(Iterable<String> is) {
    if (is == null) {
      fieldNames = EMPTY_FIELD_NAMES;
      return;
    }
    
    ImmutableSet.Builder<String> isb = new ImmutableSet.Builder<String>();
    isb.addAll(is);
    fieldNames = isb.build();
    
    if (fieldNames.size() > 0
        && !fieldNames.contains("sys_id")) {
      addFieldNames(ImmutableList.of("sys_id"));
    }

  }
  
  public Set<String> getFieldNames() {
    if (fieldNames == null) {
      fieldNames = EMPTY_FIELD_NAMES;
    }
    return fieldNames;
  }
  
  public List<ObjectNodeVisitor> getCreateVisitors() { return createVisitors; }
  public void setCreateVisitors(List<ObjectNodeVisitor> l) { createVisitors = ImmutableList.copyOf(l); }

  public List<ObjectNodeVisitor> getReadVisitors() { return readVisitors; }
  public void setReadVisitors(List<ObjectNodeVisitor> l) { readVisitors = ImmutableList.copyOf(l); }

  public List<ObjectNodeVisitor> getUpdateVisitors() { return updateVisitors; }
  public void setUpdateVisitors(List<ObjectNodeVisitor> l) { updateVisitors = ImmutableList.copyOf(l); }
  
  
  
  /*  
  private String uriBase(String tn) {
    return uriBase(DEFAULT_API_PATH, tn);
  }
  */

  private String uriBase(String ap, String tn) {
    return checkNotBlank(getRestClient().getInstance().getScheme())
      + "://" + checkNotBlank(getRestClient().getInstance().getFqdn())
      + "/" + checkNotBlank(ap)
      + "/" + checkNotBlank(tn);
  }
  
  
  
  
  
  
  
  protected URI buildUri(String apiPath, String tableName) {
    return buildUri(apiPath, tableName, null, EMPTY_PARAMS);
  }
  protected URI buildUri(String apiPath, String tableName, String path) {
    return buildUri(apiPath, tableName, path, EMPTY_PARAMS);
  }
  protected URI buildUri(String apiPath, String tableName, Params params) {
    return buildUri(apiPath, tableName, null, params);
  }
  protected URI buildUri(String apiPath, String tableName, String path, Params params) {
    
    StringBuffer sb = new StringBuffer();
    sb.append(uriBase(apiPath, tableName));
    if (StringUtils.hasText(path)) {
      sb.append("/").append(path);
    }
    
    String fullBase = sb.toString();
    
    params.put(TableParams.SYSPARM_DISPLAY_VALUE, "false");
    params.put(TableParams.SYSPARM_EXCLUDE_REFERENCE_LINK, "true");
    
    URI uri = URIBuilder.fromUri(fullBase).withParams(params).build();
    return uri;
  }
  
  
  public void afterPropertiesSet() {
    logger.trace("Coming up.");
    checkNotNull(getRestClient(), "Need a RestClient.");
    
    logger.trace("FieldNames are {}", COMMA_JOINER.join(getFieldNames()));
    

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
  
  public ObjectNode save(String tableName, ObjectNode on) {
    return save(DEFAULT_API_PATH, tableName, on);
  }
  public ObjectNode save(String apiPath, String tableName, ObjectNode on) {
    checkNotNull(on, "Need an ObjectNode to save.");

    String sysId = getString(on, "sys_id");
    
    if (isBlank(sysId)) {
      return create(apiPath, tableName, on);
    } else {
      return update(apiPath, tableName, on);
    }
  }
  
  
  
  
  public ObjectNode create(String tableName, ObjectNode on) {
    return create(DEFAULT_API_PATH, tableName, on);
  }
  public ObjectNode create(String apiPath, String tableName, ObjectNode on) {
    checkNotNull(on, "Need an ObjectNode to save.");
    visit(on, getCreateVisitors());
    
    if (logger.isTraceEnabled()) {
      logger.trace("{} create() creating {}", getRestClient().getUserSummary(), on);
    }
    
    Params params = new Params();
    params.putAll(TableParams.SYSPARM_FIELDS, getFieldNames());

    URI thisUri = buildUri(apiPath, tableName, params);
    if (logger.isTraceEnabled()) {
      logger.trace("{} create() using URI {}", getRestClient().getUserSummary(), thisUri);
    }
    
    
    ObjectNode objectNode =
      getRestClient().getRetryTemplate().execute(new RetryCallback<ObjectNode, RestClientException>() {
          public ObjectNode doWithRetry(RetryContext context) throws RestClientException {
            return getRestClient().getRestTemplate().postForObject(thisUri, on, ObjectNode.class);
          }
        });
    
    if (logger.isTraceEnabled()) {
      logger.trace("{} create(t) received JSON {}", getRestClient().getUserSummary(), objectNode);
    }
    visit(objectNode, getReadVisitors());
    return (ObjectNode) objectNode.get(CONTAINER_NODE_NAME);
  }
  

  public ObjectNode update(String tableName, ObjectNode on) {
    return update(DEFAULT_API_PATH, tableName, on);
  }
  public ObjectNode update(String apiPath, String tableName, ObjectNode on) {
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
    
    URI thisUri = buildUri(apiPath, tableName, sysId, params);
    if (logger.isTraceEnabled()) {
      logger.trace("{} update() using URI {}", getRestClient().getUserSummary(), thisUri);
    }

    String rs =
      getRestClient().getRetryTemplate().execute(new RetryCallback<String, RestClientException>() {
          public String doWithRetry(RetryContext context) throws RestClientException {
          getRestClient().getRestTemplate().put(thisUri, on);
          return "OK";
        }
      });
    
    return get(apiPath, tableName, sysId);
  }

  


  public ObjectNode get(String tableName, String sysId) {
    return get(DEFAULT_API_PATH, tableName, sysId);
  }
  public ObjectNode get(String apiPath, String tableName, String sysId) {
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
    
    URI thisUri = buildUri(apiPath, tableName, sysId, params);
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
    
    visit(objectNode, getReadVisitors());
    return (ObjectNode) objectNode.get(CONTAINER_NODE_NAME);
  }
  
  
  
  
  
  
  public List<ObjectNode> getMany(String tableName, Params params) {
    return getMany(DEFAULT_API_PATH, tableName, params);
  }
  public List<ObjectNode> getMany(String apiPath, String tableName, Params params) {
    List<ObjectNode> l = findMany(apiPath, tableName, params);
    if (l.isEmpty()) {
      throw new IncorrectResultSizeException("Expected more than zero records from query.");
    }
    return l;
  }

  

  public ObjectNode  getOne(String tableName, Params params) {
    return getOne(DEFAULT_API_PATH, tableName, params);
  }
  public ObjectNode  getOne(String apiPath, String tableName, Params params) {
    ObjectNode on = findOne(apiPath, tableName, params);
    if (on == null) {
      throw new IncorrectResultSizeException(1, 0);
    }
    return on;
  }
  
  
  public ObjectNode findOne(String tableName, Params params) {
    return findOne(DEFAULT_API_PATH, tableName, params);
  }
  public ObjectNode findOne(String apiPath, String tableName, Params params) {
    List<ObjectNode> l = findMany(apiPath, tableName, params);
    if (l.size() == 0) { return null; }
    if (l.size() == 1) { return l.get(0); }
    throw new IncorrectResultSizeException(1, l.size());
  }
  
  
  public List<ObjectNode> findMany(String tableName, Params params) {
    return findMany(DEFAULT_API_PATH, tableName, params);
  }
  public List<ObjectNode> findMany(String apiPath, String tableName, Params params) {
    checkNotNull(params, "Need some params.");
    
    if (logger.isTraceEnabled()) {
      logger.trace("{} findMany() using params({})", getRestClient().getUserSummary(), params);
    }
    
    //params.put(TableParams.SYSPARM_QUERY, qs);
    params.putAll(TableParams.SYSPARM_FIELDS, getFieldNames());

    URI thisUri = buildUri(apiPath, tableName, params);
    if (logger.isTraceEnabled()) {
      logger.trace("{} findMany() using URI {}", getRestClient().getUserSummary(), thisUri);
    }
    
    ObjectNode resultNode = null;
    try {
      resultNode =
        getRestClient().getRetryTemplate().execute(new RetryCallback<ObjectNode, RestClientException>() {
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

  
  
  
  
  
  
  
  public void delete(String tableName, ObjectNode on) {
    delete(DEFAULT_API_PATH, tableName, on);
  }
  public void delete(String apiPath, String tableName, ObjectNode on) {
    if (on == null) {
      return;
    }
    
    delete(apiPath, tableName, getString(on, "sys_id"));
  }
  
  public void delete(String tableName, String sysId) {
    delete(DEFAULT_API_PATH, tableName, sysId);
  }
  public void delete(String apiPath, String tableName, String sysId) {
    if (isBlank(sysId)) {
      logger.trace("delete() received blank sysId.  Returning.");
      return;
    }
    
    if (logger.isTraceEnabled()) {
      logger.trace("delete() deleting {}", sysId);
    }
    
    URI thisUri = buildUri(apiPath, tableName, sysId);
    if (logger.isTraceEnabled()) {
      logger.trace("delete() using URI {}", thisUri);
    }
    
    String rs =
      getRestClient().getRetryTemplate().execute(new RetryCallback<String, RestClientException>() {
          public String doWithRetry(RetryContext context) throws RestClientException {
            getRestClient().getRestTemplate().delete(thisUri);
            return "DELETED";
          }
        });
    
  }
  
  
  
}
