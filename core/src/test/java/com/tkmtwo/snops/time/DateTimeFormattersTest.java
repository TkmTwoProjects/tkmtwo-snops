package com.tkmtwo.snops.time;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


/**
 *
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class DateTimeFormattersTest {
  
  
  /*
   * On Saturday, August 1, 1981, at 12:01 a.m. Eastern Time
   * MTV launched with the words "Ladies and gentlemen, rock and roll."
   *
   * We'll use 1981-08-01T00:01:01.123456789 in America/New_York time
   *
   */
  
  //This is 1981-08-01T04:01:01Z  
  public static final long EPOCH_SECS = 365486461L;
  
  private static final LocalDate localDate = LocalDate.of(1981, 8, 1);
  private static final LocalTime localTime = LocalTime.of(4, 1, 1);
  private static final LocalDateTime localDateTime = LocalDateTime.of(1981, 8, 1, 4, 1, 1);

  
  @Test
  public void test0010LocalDate() {
    assertEquals(localDate, LocalDate.parse("1981-08-01", DateTimeFormatters.LOCAL_DATE_EXTENDED));
    assertEquals("1981-08-01", DateTimeFormatters.LOCAL_DATE_EXTENDED.format(localDate));
  }
  
  @Test
  public void test0020LocalTime() {
    assertEquals(localTime, LocalTime.parse("04:01:01", DateTimeFormatters.LOCAL_TIME_EXTENDED));
    assertEquals("04:01:01", DateTimeFormatters.LOCAL_TIME_EXTENDED.format(localTime));
  }

  @Test
  public void test0030LocalDateTime() {
    assertEquals(localDateTime, LocalDateTime.parse("1981-08-01 04:01:01", DateTimeFormatters.LOCAL_DATETIME_EXTENDED));
    assertEquals("1981-08-01 04:01:01", DateTimeFormatters.LOCAL_DATETIME_EXTENDED.format(localDateTime));
  }

}
