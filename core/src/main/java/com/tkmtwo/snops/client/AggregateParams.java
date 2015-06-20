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
public class AggregateParams {
  
  public static final Param SYSPARM_QUERY =
    new Param("sysparm_query", ParamType.SCALAR);
  
  public static final Param SYSPARM_GROUP_BY =
    new Param("sysparm_group_by", ParamType.SET, TkmTwoJointers.COMMA_JOINER);
  
  public static final Param SYSPARM_HAVING =
    new Param("sysparm_having", ParamType.SCALAR);
  
  public static final Param SYSPARM_MIN_FIELDS =
    new Param("sysparm_min_fields", ParamType.SET, TkmTwoJointers.COMMA_JOINER);
  
  public static final Param SYSPARM_MAX_FIELDS =
    new Param("sysparm_max_fields", ParamType.SET, TkmTwoJointers.COMMA_JOINER);
  
  public static final Param SYSPARM_AVG_FIELDS =
    new Param("sysparm_avg_fields", ParamType.SET, TkmTwoJointers.COMMA_JOINER);
  
  public static final Param SYSPARM_SUM_FIELDS =
    new Param("sysparm_sum_fields", ParamType.SET, TkmTwoJointers.COMMA_JOINER);
  
  public static final Param SYSPARM_COUNT =
    new Param("sysparm_count", ParamType.SCALAR);
  
  public static final Param SYSPARM_DISPLAY_VALUE =
    new Param("sysparm_display_value", ParamType.SCALAR);
  
  public static final Param SYSPARM_ORDER_BY =
    new Param("sysparm_order_by", ParamType.SET, TkmTwoJointers.COMMA_JOINER);

  public static final class Builder {
    private static final List<String> EMPTY_LIST = ImmutableList.of();
    
    private String query;
    private String queryTemplate;
    private List<String> queryValues = EMPTY_LIST;

    private String having;
    private String havingTemplate;
    private List<String> havingValues = EMPTY_LIST;

    private List<String> groupBy = EMPTY_LIST;
    private List<String> min = EMPTY_LIST;
    private List<String> max = EMPTY_LIST;
    private List<String> avg = EMPTY_LIST;
    private List<String> sum = EMPTY_LIST;

    private boolean count = false;
    private boolean displayValue = false;
    private List<String> orderBy = EMPTY_LIST;

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
    public Builder having(String s) { having = s; return this; }
    public Builder havingTemplate(String s) { havingTemplate = s; return this; }
    public Builder havingValues(String... ss) { havingValues = mkList(ss); return this; }
    
    public Builder groupBy(String... ss) { groupBy = mkList(ss); return this; }
    public Builder min(String... ss) { min = mkList(ss); return this; }
    public Builder max(String... ss) { max = mkList(ss); return this; }
    public Builder avg(String... ss) { avg = mkList(ss); return this; }
    public Builder sum(String... ss) { sum = mkList(ss); return this; }
    public Builder count(boolean b) { count = b; return this; }
    public Builder displayValue(boolean b) { displayValue = b; return this; }
    public Builder orderBy(String... ss) { orderBy = mkList(ss); return this; }

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
      
      
      if (!isBlank(having)) {
        params.put(SYSPARM_HAVING, having);
      }
      if (!isBlank(havingTemplate)) {
        params.put(SYSPARM_HAVING,
                   Interpolator.interpolate(havingTemplate,
                                            new PositionalInterpolatorCallback<String>(havingValues)));
      }

      for (String s : groupBy) { params.put(SYSPARM_GROUP_BY, s); }
      for (String s : min) { params.put(SYSPARM_MIN_FIELDS, s); }
      for (String s : max) { params.put(SYSPARM_MAX_FIELDS, s); }
      for (String s : avg) { params.put(SYSPARM_AVG_FIELDS, s); }
      for (String s : sum) { params.put(SYSPARM_SUM_FIELDS, s); }

      params.put(SYSPARM_COUNT, String.valueOf(count));
      params.put(SYSPARM_DISPLAY_VALUE, String.valueOf(displayValue));

      for (String s: orderBy) { params.put(SYSPARM_ORDER_BY, s); }
      
      
      
      
      return params;
    }
    
  }
  
  
}
