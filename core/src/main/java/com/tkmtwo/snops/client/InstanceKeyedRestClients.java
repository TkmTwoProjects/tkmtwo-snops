package com.tkmtwo.snops.client;

import com.tkmtwo.hc.uri.URIs;
import com.tkmtwo.hc.uri.UserInfo;
import com.tkmtwo.snops.Instance;
import com.tkmtwo.snops.InstanceKeyedMap;
import java.net.URI;


/**
 *
 *
 */
public class InstanceKeyedRestClients
  extends InstanceKeyedMap<RestClient> {

  public void add(Instance i, UserInfo ui) {
    RestClient rc = new RestClient(i, ui);
    rc.afterPropertiesSet();
    add(i, rc);
  }
  
  public void add(String uri) {
    URI u = URIs.of(uri);
    UserInfo ui = URIs.getUserInfo(u);
    
    Instance i = new Instance(u.getHost());
    RestClient rc = new RestClient(i, ui);
    rc.afterPropertiesSet();
    add(i, rc);
  }
  
}
