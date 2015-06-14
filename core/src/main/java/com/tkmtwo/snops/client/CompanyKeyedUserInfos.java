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
public class CompanyKeyedUserInfos
  extends CompanyKeyedMap<UserInfo> {
  

  public void add(String uri) {
    URI u = URIs.of(uri);
    UserInfo ui = URIs.getUserInfo(u);
    
    add(new CompanyInstance(u.getHost(),
                            PATH_CHARS.trimFrom(u.getPath())),
        ui);
  }
  
}
