package com.tkmtwo.snops.dictionary;


import static com.google.common.base.Strings.emptyToNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


/**
 *
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TableDefinitionTest {
  
  private static final String TEST_STRING =
    "{"
    + "\"instanceName\" : \"instanceone\","
    + "\"tableName\" : \"incident\","
    + "  \"fields\" : {"
    + "    \"caller_id\" : {"
    + "    \"table_name\" : \"incident\","
    + "    \"column_name\" : \"caller_id\","
    + "    \"column_label\" : \"Caller\","
    + "    \"internal_type\" : \"REFERENCE\","
    + "    \"reference\" : \"sys_user\","
    + "    \"reference_key\" : \"\","
    + "    \"max_length\" : 32"
    + "  },"
    + "  \"sys_updated_by\" : {"
    + "    \"table_name\" : \"task\","
    + "    \"column_name\" : \"sys_updated_by\","
    + "    \"column_label\" : \"Updated by\","
    + "    \"internal_type\" : \"STRING\","
    + "    \"reference\" : \"\","
    + "    \"reference_key\" : \"\","
    + "    \"max_length\" : 40"
    + "    }"
    + "  }"
    + "}";
  
  
  @Test
  public void test0010MappedValue()
  throws Exception {
    String tableName = "incident";
    
    ObjectMapper om = new ObjectMapper();

    /*
    
    TableDefinition td = om.readValue(TEST_STRING, TableDefinition.class);
    assertNotNull(td);
    assertEquals("incident", td.getTableName());

    FieldDefinition sysUpdatedByFd = td.getField("sys_updated_by");
    assertNotNull(sysUpdatedByFd);
    assertEquals("task", sysUpdatedByFd.getTableName());
    assertEquals("sys_updated_by", sysUpdatedByFd.getColumnName());
    assertEquals(InternalType.STRING, sysUpdatedByFd.getInternalType());
    assertNull(emptyToNull(sysUpdatedByFd.getReference()));
    assertEquals(40, sysUpdatedByFd.getMaxLength());

    FieldDefinition callerIdFd = td.getField("caller_id");
    assertEquals("incident", callerIdFd.getTableName());
    assertEquals("caller_id", callerIdFd.getColumnName());
    assertEquals(InternalType.REFERENCE, callerIdFd.getInternalType());
    assertEquals("sys_user", callerIdFd.getReference());
    assertEquals(32, callerIdFd.getMaxLength());
    */
    
  }
  
}
