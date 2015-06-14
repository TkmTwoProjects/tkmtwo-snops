package com.tkmtwo.snops.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 *
 *
 */
public final class ServiceNowModule
  extends SimpleModule {

  private static final long serialVersionUID = 1L;

  public ServiceNowModule() {
    
    addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
    addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
    
    addDeserializer(LocalDate.class, new LocalDateDeserializer());
    addSerializer(LocalDate.class, new LocalDateSerializer());
    
    addDeserializer(LocalTime.class, new LocalTimeDeserializer());
    addSerializer(LocalTime.class, new LocalTimeSerializer());
    
  }
  
  
}
