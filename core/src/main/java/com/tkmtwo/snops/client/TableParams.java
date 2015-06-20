package com.tkmtwo.snops.client;

import static com.google.common.base.TkmTwoStrings.isBlank;

import com.google.common.base.TkmTwoJointers;
import com.google.common.collect.ImmutableList;
import com.tkmtwo.hc.uri.Param;
import com.tkmtwo.hc.uri.ParamType;
import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.interpolate.Interpolator;
import com.tkmtwo.interpolate.PositionalInterpolatorCallback;
import java.util.List;


/**
 *
 *
 */
public class TableParams {
  
  public static final Param SYSPARM_QUERY =
    new Param("sysparm_query", ParamType.SCALAR);
  
  public static final Param SYSPARM_DISPLAY_VALUE =
    new Param("sysparm_display_value", ParamType.SCALAR);
  
  public static final Param SYSPARM_FIELDS =
    new Param("sysparm_fields", ParamType.SET, TkmTwoJointers.COMMA_JOINER);
  
  public static final Param SYSPARM_VIEW =
    new Param("sysparm_view", ParamType.SCALAR);
  
  public static final Param SYSPARM_LIMIT =
    new Param("sysparm_limit", ParamType.SCALAR);
  
  public static final Param SYSPARM_OFFSET =
    new Param("sysparm_offset", ParamType.SCALAR);
  
  public static final Param SYSPARM_EXCLUDE_REFERENCE_LINK =
    new Param("sysparm_exclude_reference_link", ParamType.SCALAR);
  
  public static final Param SYSPARM_READ_REPLICA_CATEGORY =
    new Param("sysparm_read_replica_category", ParamType.SCALAR);
  
  
  public static final class Builder {
    private static final List<String> EMPTY_LIST = ImmutableList.of();
    
    private String query;
    private String queryTemplate;
    private List<String> queryValues = EMPTY_LIST;

    private boolean displayValue = false;
    private List<String> fields = EMPTY_LIST;
    private String view;
    private String limit;
    private String offset;

    private boolean excludeReferenceLink = false;
    private String readReplicaCategory;


    private static List<String> mkList(String... ss) {
      ImmutableList.Builder<String> ilb = new ImmutableList.Builder<>();
      for (String s : ss) {
        ilb.add(s);
      }
      return ilb.build();
    }

    public Builder() { }
    
    public Builder query(String s) { query = s; return this; }
    public Builder queryTemplate(String s) { queryTemplate = s; return this; }
    public Builder queryValues(String... ss) { queryValues = mkList(ss); return this; }
    
    public Builder displayValue(boolean b) { displayValue = b; return this; }
    public Builder fields(String... ss) { fields = mkList(ss); return this; }
    public Builder view(String s) { view = s; return this; }
    public Builder limit(String s) { limit = s; return this; }
    public Builder offset(String s) { offset = s; return this; }
    public Builder excludeReferenceLink(boolean b) { excludeReferenceLink = b; return this; }
    public Builder readReplicaCategory(String s) { readReplicaCategory = s; return this; }


    public Params build() {
      Params params = new Params();
      
      if (!isBlank(query)) {
        params.put(SYSPARM_QUERY, query);
      }
      if (!isBlank(queryTemplate)) {
        params.put(SYSPARM_QUERY,
                   Interpolator.interpolate(queryTemplate,
                                            new PositionalInterpolatorCallback<String>(queryValues)));
      }
      
      params.put(SYSPARM_DISPLAY_VALUE, String.valueOf(displayValue));
      
      for (String s : fields) { params.put(SYSPARM_FIELDS, s); }
      if (!isBlank(view)) { params.put(SYSPARM_VIEW, view); }
      if (!isBlank(limit)) { params.put(SYSPARM_LIMIT, limit); }
      if (!isBlank(offset)) { params.put(SYSPARM_OFFSET, offset); }
      
      params.put(SYSPARM_EXCLUDE_REFERENCE_LINK, String.valueOf(excludeReferenceLink));
      
      if (!isBlank(readReplicaCategory)) { params.put(SYSPARM_READ_REPLICA_CATEGORY, readReplicaCategory); }
      
      
      return params;
    }
    
  }

  
}
