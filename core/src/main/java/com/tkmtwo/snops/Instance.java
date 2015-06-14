package com.tkmtwo.snops;

import static com.google.common.base.TkmTwoConditions.checkNotBlank;
import static com.google.common.base.TkmTwoJointers.DOT_JOINER;
import static com.google.common.base.TkmTwoJointers.DOT_SPLITTER;

import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;



/**
 *
 *
 */
public class Instance
  implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String DEFAULT_SCHEME = "https";
  public static final String DEFAULT_DOMAIN = "service-now.com";

  private String scheme = DEFAULT_SCHEME;
  private String name;
  private String domain;
  private String fqdn;


  protected Instance() { }
  
  public Instance(String s) {
    setInstanceFieldsFromSingleString(s);
  }
  
  protected void setInstanceFieldsFromSingleString(String s) {
    checkNotBlank(s, "Single string may not be blank.");
    
    if (s.indexOf('.') < 0) {
      setName(s);
      setDomain(DEFAULT_DOMAIN);
    } else {
      List<String> dotNames = DOT_SPLITTER.splitToList(s);
      setName(dotNames.get(0));
      setDomain(DOT_JOINER.join(dotNames.subList(1, dotNames.size())));
    }
    setFqdn(getName() + "." + getDomain());
  }
  
  public Instance(String n, String d) {
    setName(n);
    setDomain(d);
    setFqdn(getName() + "." + getDomain());
  }

  public String getScheme() { return scheme; }
  protected void setScheme(String s) { scheme = checkNotBlank(s, "Scheme name may not be blank."); }
  
  public String getName() { return name; }
  protected void setName(String s) { name = checkNotBlank(s, "Instance name may not be blank."); }
  
  public String getDomain() { return domain; }
  protected void setDomain(String s) { domain = checkNotBlank(s, "Domain may not be blank."); }
  
  public String getFqdn() { return fqdn; }
  protected void setFqdn(String s) { fqdn = checkNotBlank(s, "FQDN may not be null."); }
  
  
  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    //if (!(o instanceof Instance)) { return false; }
    if (o == null || getClass() != o.getClass()) { return false; }    
    
    Instance impl = (Instance) o;
    
    return Objects.equals(getFqdn(), impl.getFqdn());
  }
  
  
  @Override
  public int hashCode() {
    return Objects.hash(getFqdn());
  }
  
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("scheme", getScheme())
      .add("name", getName())
      .add("domain", getDomain())
      .add("fqdn", getFqdn())
      .toString();
  }
  
}

