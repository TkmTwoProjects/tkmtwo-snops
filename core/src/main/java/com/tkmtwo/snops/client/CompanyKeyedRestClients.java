package com.tkmtwo.snops.client;


import com.tkmtwo.hc.uri.URIs;
import com.tkmtwo.hc.uri.UserInfo;
import com.tkmtwo.snops.CompanyInstance;
import com.tkmtwo.snops.CompanyKeyedMap;
import java.net.URI;


/**
 *
 *
 */
public class CompanyKeyedRestClients
  extends CompanyKeyedMap<RestClient> {
  
  public void add(CompanyInstance ci, UserInfo ui) {
    RestClient rc = new RestClient(ci, ui);
    rc.afterPropertiesSet();
    add(ci, rc);
  }
  
  public void add(String uri) {
    URI u = URIs.of(uri);
    UserInfo ui = URIs.getUserInfo(u);
    
    CompanyInstance ci = new CompanyInstance(u.getHost(), PATH_CHARS.trimFrom(u.getPath()));
    RestClient rc = new RestClient(ci, ui);
    
    add(ci, rc);
  }
  
  
}
