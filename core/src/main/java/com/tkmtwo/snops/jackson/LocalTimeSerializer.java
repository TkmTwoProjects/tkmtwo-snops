package com.tkmtwo.snops.jackson;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.tkmtwo.snops.time.DateTimeFormatters;
import java.io.IOException;
import java.time.LocalTime;


/**
 *
 */
public final class LocalTimeSerializer
  extends StdScalarSerializer<LocalTime> {
  
  private static final long serialVersionUID = 1L;
  
  public LocalTimeSerializer() {
    super(LocalTime.class);
  }

  public void serialize(LocalTime value, JsonGenerator jgen, SerializerProvider provider)
    throws IOException, JsonProcessingException {
    jgen.writeString(DateTimeFormatters.LOCAL_TIME_EXTENDED.format(value));
  }
  
  
}
