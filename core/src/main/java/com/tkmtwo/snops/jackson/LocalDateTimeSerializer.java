package com.tkmtwo.snops.jackson;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.tkmtwo.snops.time.DateTimeFormatters;
import java.io.IOException;
import java.time.LocalDateTime;


/**
 *
 */
public final class LocalDateTimeSerializer
  extends StdScalarSerializer<LocalDateTime> {
  
  private static final long serialVersionUID = 1L;

  public LocalDateTimeSerializer() {
    super(LocalDateTime.class);
  }

  public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider)
    throws IOException, JsonProcessingException {
    jgen.writeString(DateTimeFormatters.LOCAL_DATETIME_EXTENDED.format(value));
  }
  
  
}
