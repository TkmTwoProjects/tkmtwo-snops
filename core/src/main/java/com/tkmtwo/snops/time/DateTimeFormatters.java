package com.tkmtwo.snops.time;



import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;

import java.time.ZoneId;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.format.SignStyle;


/**
 *
 *
 */
public final class DateTimeFormatters {
  

  public static final DateTimeFormatter LOCAL_DATE_EXTENDED;
  static {
    DateTimeFormatter localDateExtended = new DateTimeFormatterBuilder()
      .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
      .appendLiteral('-')
      .appendValue(MONTH_OF_YEAR, 2)
      .appendLiteral('-')
      .appendValue(DAY_OF_MONTH, 2)
      .toFormatter();
    
    LOCAL_DATE_EXTENDED =
      localDateExtended
      .withResolverStyle(ResolverStyle.STRICT)
      .withChronology(IsoChronology.INSTANCE)
      .withZone(ZoneId.of("Z"));
  }
  
  public static final DateTimeFormatter LOCAL_TIME_EXTENDED;
  static {
    DateTimeFormatter localTimeExtended = new DateTimeFormatterBuilder()
      .appendValue(HOUR_OF_DAY, 2)
      .appendLiteral(':')
      .appendValue(MINUTE_OF_HOUR, 2)
      .optionalStart()
      .appendLiteral(':')
      .appendValue(SECOND_OF_MINUTE, 2)
      .toFormatter();
    
    LOCAL_TIME_EXTENDED =
      localTimeExtended
      .withResolverStyle(ResolverStyle.STRICT)
      .withChronology(IsoChronology.INSTANCE)
      .withZone(ZoneId.of("Z"));
  }
  
  
  public static final DateTimeFormatter LOCAL_DATETIME_EXTENDED;
  static {
    DateTimeFormatter localDateTimeExtended = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .append(LOCAL_DATE_EXTENDED)
      .appendLiteral(' ')
      .append(LOCAL_TIME_EXTENDED)
      .toFormatter();
    
    LOCAL_DATETIME_EXTENDED =
      localDateTimeExtended
      .withResolverStyle(ResolverStyle.STRICT)
      .withChronology(IsoChronology.INSTANCE)
      .withZone(ZoneId.of("Z"));
  }
  
  
  
}
