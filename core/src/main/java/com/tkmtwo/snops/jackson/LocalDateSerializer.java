package com.tkmtwo.snops.jackson;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.tkmtwo.snops.time.DateTimeFormatters;
import java.io.IOException;
import java.time.LocalDate;


/**
 *
 */
public final class LocalDateSerializer
  extends StdScalarSerializer<LocalDate> {
  
  private static final long serialVersionUID = 1L;

  public LocalDateSerializer() {
    super(LocalDate.class);
  }

  public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider provider)
    throws IOException, JsonProcessingException {
    jgen.writeString(DateTimeFormatters.LOCAL_DATE_EXTENDED.format(value));
  }
  
  
}
