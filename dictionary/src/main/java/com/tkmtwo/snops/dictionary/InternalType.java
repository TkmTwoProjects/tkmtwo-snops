package com.tkmtwo.snops.dictionary;

import static com.google.common.base.TkmTwoStrings.isBlank;

import java.util.EnumSet;
/*

=UPPER(B2) + "(" + CHAR(34) + B2 + CHAR(34) + "),"


=CONCATENATE(UPPER(B2), "(", CHAR(34), B2, CHAR(34), ", ", "GlideType.", UPPER(G2), "),")

 */

/**
 *
 *
 */
public enum InternalType {
  AUDIO("audio", GlideType.STRING),
  AUTO_INCREMENT("auto_increment", GlideType.INTEGER),
  AUTO_NUMBER("auto_number", GlideType.STRING),
  BOOLEAN("boolean", GlideType.BOOLEAN),
  BREAKDOWN_ELEMENT("breakdown_element", GlideType.SYS_ID),
  CATALOG_PREVIEW("catalog_preview", GlideType.STRING),
  CHAR("char", GlideType.SYS_ID),
  CHOICE("choice", GlideType.STRING),
  COLLECTION("collection", GlideType.STRING),
  COLOR("color", GlideType.STRING),
  COLOR_DISPLAY("color_display", GlideType.STRING),
  COMPOSITE_FIELD("composite_field", GlideType.STRING),
  COMPOSITE_NAME("composite_name", GlideType.STRING),
  COMPRESSED("compressed", GlideType.STRING),
  CONDITIONS("conditions", GlideType.STRING),
  CONDITION_STRING("condition_string", GlideType.STRING),
  COUNTER("counter", GlideType.STRING),
  CURRENCY("currency", GlideType.DECIMAL),
  DATA_ARRAY("data_array", GlideType.STRING),
  DATA_OBJECT("data_object", GlideType.STRING),
  DATA_STRUCTURE("data_structure", GlideType.STRING),
  DATE("date", GlideType.DATE),
  DATETIME("datetime", GlideType.DATE_TIME),
  DAYS_OF_WEEK("days_of_week", GlideType.STRING),
  DAY_OF_WEEK("day_of_week", GlideType.INTEGER),
  DECIMAL("decimal", GlideType.DECIMAL),
  DECORATION("decoration", GlideType.STRING),
  DOCUMENTATION_FIELD("documentation_field", GlideType.STRING),
  DOCUMENT_ID("document_id", GlideType.SYS_ID),
  DOMAIN_ID("domain_id", GlideType.SYS_ID),
  DOMAIN_NUMBER("domain_number", GlideType.DECIMAL),
  DOMAIN_PATH("domain_path", GlideType.STRING),
  DUE_DATE("due_date", GlideType.DATE_TIME),
  EMAIL("email", GlideType.STRING),
  EMAIL_SCRIPT("email_script", GlideType.STRING),
  EXTERNAL_NAMES("external_names", GlideType.STRING),
  FIELD_LIST("field_list", GlideType.STRING),
  FIELD_NAME("field_name", GlideType.STRING),
  FLOAT("float", GlideType.FLOATING_POINT_NUMBER),
  FORMULA("formula", GlideType.STRING),
  GLIDE_ACTION_LIST("glide_action_list", GlideType.STRING),
  GLIDE_DATE("glide_date", GlideType.DATE),
  GLIDE_DATE_TIME("glide_date_time", GlideType.DATE_TIME),
  GLIDE_DURATION("glide_duration", GlideType.DATE_TIME),
  GLIDE_LIST("glide_list", GlideType.STRING),
  GLIDE_PRECISE_TIME("glide_precise_time", GlideType.STRING),
  GLIDE_TIME("glide_time", GlideType.DATE_TIME),
  GLIDE_VAR("glide_var", GlideType.STRING),
  GUID("GUID", GlideType.STRING),
  HTML("html", GlideType.STRING),
  HTML_SCRIPT("html_script", GlideType.STRING),
  IMAGE("image", GlideType.STRING),
  INDEX_NAME("index_name", GlideType.STRING),
  INT("int", GlideType.STRING),
  INTEGER("integer", GlideType.INTEGER),
  INTEGER_DATE("integer_date", GlideType.INTEGER),
  INTEGER_TIME("integer_time", GlideType.INTEGER),
  INTERNAL_TYPE("internal_type", GlideType.STRING),
  IP_ADDRESS("ip_address", GlideType.STRING),
  JOURNAL("journal", GlideType.STRING),
  JOURNAL_INPUT("journal_input", GlideType.STRING),
  JOURNAL_LIST("journal_list", GlideType.STRING),
  LONG("long", GlideType.STRING),
  LONGINT("longint", GlideType.LONGINT),
  MASK_CODE("mask_code", GlideType.STRING),
  METRIC_ABSOLUTE("metric_absolute", GlideType.FLOATING_POINT_NUMBER),
  METRIC_COUNTER("metric_counter", GlideType.FLOATING_POINT_NUMBER),
  METRIC_DERIVE("metric_derive", GlideType.FLOATING_POINT_NUMBER),
  METRIC_GAUGE("metric_gauge", GlideType.FLOATING_POINT_NUMBER),
  MID_CONFIG("mid_config", GlideType.STRING),
  MONTH_OF_YEAR("month_of_year", GlideType.INTEGER),
  MULTI_SMALL("multi_small", GlideType.STRING),
  MULTI_TWO_LINES("multi_two_lines", GlideType.STRING),
  NAME_VALUES("name_values", GlideType.STRING),
  NL_TASK_INT1("nl_task_int1", GlideType.INTEGER),
  ORDER_INDEX("order_index", GlideType.INTEGER),
  PASSWORD("password", GlideType.STRING),
  PASSWORD2("password2", GlideType.STRING),
  PERCENT_COMPLETE("percent_complete", GlideType.DECIMAL),
  PHONE_NUMBER("phone_number", GlideType.STRING),
  PHONE_NUMBER_E164("phone_number_e164", GlideType.STRING),
  PH_NUMBER("ph_number", GlideType.STRING),
  PRICE("price", GlideType.DECIMAL),
  RADIO("radio", GlideType.STRING),
  REFERENCE("reference", GlideType.SYS_ID),
  REFERENCE_NAME("reference_name", GlideType.STRING),
  RELATED_TAGS("related_tags", GlideType.STRING),
  REMINDER_FIELD_NAME("reminder_field_name", GlideType.STRING),
  REPEAT_COUNT("repeat_count", GlideType.INTEGER),
  REPEAT_TYPE("repeat_type", GlideType.STRING),
  REPLICATION_PAYLOAD("replication_payload", GlideType.STRING),
  SCHEDULE_DATE_TIME("schedule_date_time", GlideType.STRING),
  SCHEDULE_INTERVAL_COUNT("schedule_interval_count", GlideType.INTEGER),
  SCRIPT("script", GlideType.STRING),
  SCRIPT_PLAIN("script_plain", GlideType.STRING),
  SHORT_FIELD_NAME("short_field_name", GlideType.STRING),
  SHORT_TABLE_NAME("short_table_name", GlideType.STRING),
  SLUSHBUCKET("slushbucket", GlideType.STRING),
  SOURCE_ID("source_id", GlideType.SYS_ID),
  SOURCE_NAME("source_name", GlideType.STRING),
  SOURCE_TABLE("source_table", GlideType.STRING),
  STRING("string", GlideType.STRING),
  STRING_BOOLEAN("string_boolean", GlideType.STRING),
  STRING_FULL_UTF8("string_full_utf8", GlideType.STRING),
  SYSEVENT_NAME("sysevent_name", GlideType.STRING),
  SYSRULE_FIELD_NAME("sysrule_field_name", GlideType.STRING),
  SYS_CLASS_NAME("sys_class_name", GlideType.STRING),
  TABLE_NAME("table_name", GlideType.STRING),
  TEMPLATE_VALUE("template_value", GlideType.STRING),
  TIME("time", GlideType.TIME),
  TIMER("timer", GlideType.DATE_TIME),
  TRANSLATED("translated", GlideType.STRING),
  TRANSLATED_FIELD("translated_field", GlideType.STRING),
  TRANSLATED_HTML("translated_html", GlideType.STRING),
  TRANSLATED_TEXT("translated_text", GlideType.STRING),
  TREE_CODE("tree_code", GlideType.STRING),
  TREE_PATH("tree_path", GlideType.STRING),
  URL("url", GlideType.STRING),
  USER_IMAGE("user_image", GlideType.STRING),
  USER_INPUT("user_input", GlideType.STRING),
  USER_ROLES("user_roles", GlideType.STRING),
  VARIABLES("variables", GlideType.STRING),
  VARIABLE_CONDITIONS("variable_conditions", GlideType.STRING),
  VERSION("version", GlideType.STRING),
  VIDEO("video", GlideType.STRING),
  WEEK_OF_MONTH("week_of_month", GlideType.INTEGER),
  WIDE_TEXT("wide_text", GlideType.STRING),
  WIKI_TEXT("wiki_text", GlideType.STRING),
  WMS_JOB("wms_job", GlideType.STRING),
  WORKFLOW("workflow", GlideType.STRING),
  XML("xml", GlideType.STRING);
  

  
  private final String glideName;
  private final GlideType glideType;

  private InternalType(String gn, GlideType gt) {
    glideName = gn;
    glideType = gt;
  }

  public String glideName() { return glideName; }
  public GlideType glideType() { return glideType; }


  //public EnumSet<InternalType> NON_DATA_TYPE = EnumSet.of(UNKNOWN, COLLECTION);
  public static final EnumSet<InternalType> NON_DATA_TYPE = EnumSet.of(COLLECTION);
  public boolean isData(String s) {
    return isData(from(s));
  }
  public boolean isData(InternalType it) {
    return !NON_DATA_TYPE.contains(it);
  }


  public static InternalType from(String s) {
    if (isBlank(s)) { return null; }
    for (InternalType et : EnumSet.allOf(InternalType.class)) {

      if (et.name().equalsIgnoreCase(s)) {
        return et;
      }
    }
    return null;
  }
  
  public static InternalType from(SysDictionary sd) {
    if (sd == null) { return null; }
    return from(sd.getInternalType());
  }
  
  
}
