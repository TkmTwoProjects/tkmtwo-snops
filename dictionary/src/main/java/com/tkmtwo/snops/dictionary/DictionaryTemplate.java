package com.tkmtwo.snops.dictionary;

//import static com.google.common.base.ServiceNowStrings.isaSysId;
//import static com.google.common.base.TkmTwoConditions.checkNotBlank;
//import static com.google.common.base.TkmTwoJointers.COMMA_JOINER;
//import static com.google.common.base.TkmTwoJointers.COMMA_SPLITTER;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.TkmTwoStrings.isBlank;

//import com.tkmtwo.snops.client.TableParams;
//import java.util.ArrayList;
import com.tkmtwo.snops.client.RestClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;


/**
 *
 */
public class DictionaryTemplate
  implements DictionaryOperations, InitializingBean {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  private RestClient restClient;
  
  private SysDictionaryOperations sysDictionaryOps;
  private SysDbObjectOperations sysDbObjectOps;
  
  protected DictionaryTemplate() { }
  public DictionaryTemplate(RestClient rc) {
    setRestClient(rc);
  }
  
  public void afterPropertiesSet() {
    logger.trace("Initializing...");
    checkNotNull(getRestClient(), "Need a RestClient.");
    
    if (sysDbObjectOps == null) {
      SysDbObjectTemplate sysDbObjectTemplate = new SysDbObjectTemplate(getRestClient());
      sysDbObjectTemplate.afterPropertiesSet();
      sysDbObjectOps = sysDbObjectTemplate;
    }
    
    if (sysDictionaryOps == null) {
      SysDictionaryTemplate sysDictionaryTemplate = new SysDictionaryTemplate(getRestClient());
      sysDictionaryTemplate.afterPropertiesSet();
      sysDictionaryOps = sysDictionaryTemplate;
    }
    
  }
  
  public String getUserName() {
    return getRestClient().getUserInfo().getUserName();
  }
  
  protected RestClient getRestClient() { return restClient; }
  private void setRestClient(RestClient rc) { restClient = rc; }
  
  public SysDbObjectOperations sysDbObjectOps() { return sysDbObjectOps; }
  protected void setSysDbObjectOps(SysDbObjectOperations ops) { sysDbObjectOps = ops; }
  
  public SysDictionaryOperations sysDictionaryOps() { return sysDictionaryOps; }
  protected void setSysDictionaryOps(SysDictionaryOperations ops) { sysDictionaryOps = ops; }
  
  
  
  
  private void includeFields(Map<String, SysDictionary> m, SysDbObject sdo) {
    List<SysDictionary> sds = sysDictionaryOps().findActive(sdo);
    logger.trace("There are {} fields in table '{}'", sds.size(), sdo.getName());
    for (SysDictionary sd : sds) {
      if (isBlank(sd.getElement())) { continue; }
      m.put(sd.getElement(), sd);
    }
  }
  public Map<String, SysDictionary> fieldsForTable(String tableName) {
    Map<String, SysDictionary> m = new HashMap<>();
    
    SysDbObject sdo = sysDbObjectOps().findByName(tableName);
    if (sdo == null) { return m; }
    includeFields(m, sdo);
    
    String superClassId = sdo.getSuperClass();
    while (!isBlank(superClassId)) {
      SysDbObject scSdo = sysDbObjectOps().get(superClassId);
      if (scSdo != null) {
        includeFields(m, scSdo);
        superClassId = scSdo.getSuperClass();
      } else {
        //superClassId = null;
        break;
      }
    }
    
    return m;
  }
  
  
  
  private void includeDataFields(List<SysDictionary> l, SysDbObject sdo) {
    List<SysDictionary> sds = sysDictionaryOps().findActive(sdo);
    logger.trace("There are {} fields in table '{}'", sds.size(), sdo.getName());
    for (SysDictionary sd : sds) {
      if (isBlank(sd.getElement())) { continue; } //if no element name, can't be a data col...TODO(mahaffey): maybe more?
      if (hasColumnName(l, sd.getElement())) { continue; } //don't replace same-named columns.  assuming loading extended-first
      l.add(sd);
    }
  }
  private static boolean hasColumnName(Iterable<SysDictionary> sds, String columnName) {
    for (SysDictionary sd : sds) {
      if (sd.getElement().equals(columnName)) {
        return true;
      }
    }
    return false;
  }
  public List<SysDictionary> dataFieldsForTable(String tableName) {
    List<SysDictionary> l = new ArrayList<>();
    
    SysDbObject sdo = sysDbObjectOps().findByName(tableName);
    if (sdo == null) { return l; }
    includeDataFields(l, sdo);
    
    String superClassId = sdo.getSuperClass();
    while (!isBlank(superClassId)) {
      SysDbObject scSdo = sysDbObjectOps().get(superClassId);
      if (scSdo != null) {
        includeDataFields(l, scSdo);
        superClassId = scSdo.getSuperClass();
      } else {
        //superClassId = null;
        break;
      }
    }
    
    return l;
  }
  
  
  public TableDefinition tableDefinition(String tableName) {
    SysDbObject sdo = checkNotNull(sysDbObjectOps().findByName(tableName),
                                   "Table with name %s not found.", tableName);
    
    List<SysDictionary> sds = dataFieldsForTable(sdo.getName());
    return new TableDefinition(sdo, sds);
  }
  
  
  
}
