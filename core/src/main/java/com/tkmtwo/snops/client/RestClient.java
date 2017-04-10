package com.tkmtwo.snops.client;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.TkmTwoConditions.checkNotBlank;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.common.base.MoreObjects;
import com.tkmtwo.hc.client.HttpClients;
import com.tkmtwo.hc.client.RestTemplates;
import com.tkmtwo.hc.uri.UserInfo;
import com.tkmtwo.snops.Instance;
import com.tkmtwo.snops.jackson.ServiceNowModule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.retry.RetryCallback;
//import org.springframework.retry.RetryContext;
//import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
//import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


/**
 *
 *
 */
public class RestClient
  implements InitializingBean {
  
  public static final int CONN_TIMEOUT = 60 * 1000;
  public static final int CONN_MAX_TOTAL = 50;
  public static final int CONN_MAX_PER_ROUTE = 20;
  public static final int CONN_RETRY_ATTEMPTS = 6;

  
  private Instance instance;
  private UserInfo userInfo;
  private String userSummary;
  
  private List<HttpMessageConverter<?>> messageConverters;
  private MappingJackson2HttpMessageConverter jacksonMessageConverter;
  
  private RestTemplate restTemplate;
  private RetryTemplate retryTemplate = null;

  
  
  public RestClient(Instance i, UserInfo ui) {
    setInstance(i);
    setUserInfo(ui);
  }
  public RestClient(String i, String ui) {
    setInstance(new Instance(i));
    setUserInfo(UserInfo.of(ui));
  }
  
  public Instance getInstance() { return instance; }
  private void setInstance(Instance i) { instance = i; }
  
  public UserInfo getUserInfo() { return userInfo; }
  private void setUserInfo(UserInfo ui) { userInfo = ui; }
  
  public String getUserSummary() { return userSummary; }
  
  public List<HttpMessageConverter<?>> getMessageConverters() {
    if (messageConverters == null) {
      messageConverters = new ArrayList<>();
    }
    return messageConverters;
  }
  public void setMessageConverters(List<HttpMessageConverter<?>> mcs) {
    messageConverters = mcs;
  }

  public MappingJackson2HttpMessageConverter getJacksonMessageConverter() {
    return jacksonMessageConverter;
  }
  public void setJacksonMessageConverter(MappingJackson2HttpMessageConverter jmc) {
    jacksonMessageConverter = jmc;
  }

  public RestTemplate getRestTemplate() {
    return restTemplate;
  }
  protected void setRestTemplate(RestTemplate rt) {
    restTemplate = rt;
  }
  
  public void afterPropertiesSet() {
    checkNotNull(getInstance(), "Need an Instance.");
    checkNotBlank(getInstance().getName(), "Need an Instance with a name.");
    
    checkNotBlank(getUserInfo().getUserName(), "Need a user name.");
    checkNotBlank(getUserInfo().getPassword(), "Need a password.");


    if (getJacksonMessageConverter() == null) {
      setJacksonMessageConverter(defaultJacksonMessageConverter());
    }

    if (getMessageConverters().isEmpty()) {
      setMessageConverters(defaultMessageConverters());
    }

    HttpComponentsClientHttpRequestFactory hcrf = 
      new HttpComponentsClientHttpRequestFactory(HttpClients.build(getUserInfo().getUserName(),
                                                                   getUserInfo().getPassword()));
    setRestTemplate(RestTemplates.build(hcrf, getMessageConverters()));
    
    userSummary = getUserInfo().getUserName() + "@" + getInstance().getName();
    
    
    if (retryTemplate == null) {
      SimpleRetryPolicy srp = new SimpleRetryPolicy(RestClient.CONN_RETRY_ATTEMPTS,
                                                    Collections.singletonMap(Exception.class, true));
      retryTemplate = new RetryTemplate();
      retryTemplate.setRetryPolicy(srp);
    }

  }
  
  public RetryTemplate getRetryTemplate() { return retryTemplate; }

  protected MappingJackson2HttpMessageConverter defaultJacksonMessageConverter() {
    MappingJackson2HttpMessageConverter jmc = new MappingJackson2HttpMessageConverter();
    jmc.setPrettyPrint(true);
    jmc.getObjectMapper().registerModule(new ServiceNowModule());
    jmc.getObjectMapper().enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    return jmc;
  }
  
  protected List<HttpMessageConverter<?>> defaultMessageConverters() {
    List<HttpMessageConverter<?>> mcs = new ArrayList<>();
    mcs.add(getJacksonMessageConverter() == null
            ? defaultJacksonMessageConverter()
            : getJacksonMessageConverter());
    mcs.add(new StringHttpMessageConverter());
    return mcs;
  }
  
  
  


  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("instance", getInstance())
      .add("userInfo", getUserInfo())
      .toString();
  }

  
  
  
}
