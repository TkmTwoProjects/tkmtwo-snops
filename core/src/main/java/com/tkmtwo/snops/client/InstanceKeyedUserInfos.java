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
public class InstanceKeyedUserInfos
  extends InstanceKeyedMap<UserInfo> {
  
  
  public void add(String uri) {
    URI u = URIs.of(uri);
    UserInfo ui = URIs.getUserInfo(u);
    
    add(new Instance(u.getHost()), ui);
  }
}
