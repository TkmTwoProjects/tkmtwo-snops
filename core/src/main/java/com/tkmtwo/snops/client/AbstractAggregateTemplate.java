package com.tkmtwo.snops.client;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.TkmTwoConditions.checkNotBlank;
import static com.google.common.base.TkmTwoStrings.isBlank;

//import com.google.common.collect.ImmutableList;
import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.hc.uri.URIBuilder;
import java.net.URI;
//import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;


/**
 *
 *
 *
 */
public abstract class AbstractAggregateTemplate
  implements InitializingBean {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass()); 

  //public static final List<String> EMPTY_QS_VALUES = ImmutableList.of();
  
  public static final String DEFAULT_API_PATH = "api/now/stats";
  public static final String CONTAINER_NODE_NAME = "result";
  public static final String STATS_NODE_NAME = "stats";
  
  
  private static final Params EMPTY_PARAMS = new Params();
  
  private RestClient restClient;

  private String apiPath = DEFAULT_API_PATH;
  private String tableName;
  
  private String uriBase;
  
  
  protected AbstractAggregateTemplate() {}

  protected AbstractAggregateTemplate(RestClient rc, String tn) {
    setRestClient(rc);
    setTableName(tn);
  }
  
                       
  public String getApiPath() {
    if (isBlank(apiPath)) {
      apiPath = DEFAULT_API_PATH;
    }
    return apiPath;
  }
  protected void setApiPath(String s) { apiPath = s; }
  
  public String getTableName() { return tableName; }
  protected void setTableName(String s) { tableName = s; }
  

  protected String getUriBase() { return uriBase; }
  protected void setUriBase(String s) { uriBase = s; }

  protected RestClient getRestClient() { return restClient; }
  protected void setRestClient(RestClient rc) { restClient = rc; }

  
  
  
  protected URI buildUri() {
    return buildUri(null, EMPTY_PARAMS);
  }
  protected URI buildUri(String path) {
    return buildUri(path, EMPTY_PARAMS);
  }
  protected URI buildUri(Params params) {
    return buildUri(null, params);
  }
  protected URI buildUri(String path, Params params) {
    
    StringBuffer sb = new StringBuffer();
    sb.append(getUriBase());
    if (StringUtils.hasText(path)) {
      sb.append("/").append(path);
    }
    
    String fullBase = sb.toString();
    
    params.put(AggregateParams.SYSPARM_DISPLAY_VALUE, "false");

    
    URI uri = URIBuilder.fromUri(fullBase).withParams(params).build();
    return uri;
  }
  
  
  public void afterPropertiesSet() {
    logger.trace("Coming up.");
    checkNotNull(getRestClient(), "Need a RestClient.");
    
    setUriBase(checkNotBlank(getRestClient().getInstance().getScheme())
               + "://" + checkNotBlank(getRestClient().getInstance().getFqdn())
               + "/" + checkNotBlank(getApiPath())
               + "/" + checkNotBlank(getTableName()));
    
    logger.trace("UriBase is {}", getUriBase());
    
    
  }
  
  
  
}
