package com.tkmtwo.snops;

import static com.google.common.base.ServiceNowConditions.checkSysId;

import com.google.common.base.MoreObjects;
import java.util.Objects;


/**
 *
 *
 */
public class CompanyInstance
  extends Instance {

  private static final long serialVersionUID = 1L;
  
  
  private String companySysId;

  protected CompanyInstance() { }
  
  public CompanyInstance(Instance i, String cid) {
    setName(i.getName());
    setDomain(i.getDomain());
    setFqdn(i.getFqdn());
    setCompanySysId(cid);
  }
  public CompanyInstance(String in, String cid) {
    setInstanceFieldsFromSingleString(in);
    setCompanySysId(cid);
  }
  public CompanyInstance(String in, String dn, String cid) {
    setName(in);
    setDomain(dn);
    setFqdn(getName() + "." + getDomain());
    setCompanySysId(cid);
  }
  
  public String getCompanySysId() { return companySysId; }
  private void setCompanySysId(String s) {
    companySysId = checkSysId(s);
  }
  
  
  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    
    if (o == null || getClass() != o.getClass()) { return false; }
    
    CompanyInstance impl = (CompanyInstance) o;
    
    return
      Objects.equals(getFqdn(), impl.getFqdn())
      && Objects.equals(getCompanySysId(), impl.getCompanySysId());
  }
  
  
  @Override
  public int hashCode() {
    return Objects.hash(getFqdn(), getCompanySysId());
  }
  
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("name", getName())
      .add("domain", getDomain())
      .add("fqdn", getFqdn())
      .add("companySysId", getCompanySysId())
      .toString();
  }
  
}

