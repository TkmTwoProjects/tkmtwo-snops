package com.tkmtwo.snops.jackson;

import static com.google.common.base.TkmTwoStrings.isBlank;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.tkmtwo.snops.time.DateTimeFormatters;
import java.io.IOException;
import java.time.LocalTime;


/**
 *
 */
public final class LocalTimeDeserializer
  extends StdScalarDeserializer<LocalTime> {
  
  private static final long serialVersionUID = 1L;
  
  public LocalTimeDeserializer() {
    super(LocalTime.class);
  }
  
  public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
    throws IOException, JsonProcessingException {
    
    if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING) {
      String dtText = jsonParser.getText();
      if (isBlank(dtText)) {
        return null;
      }
      return LocalTime.parse(dtText, DateTimeFormatters.LOCAL_TIME_EXTENDED);
    }
    
    throw deserializationContext.mappingException("Expected JSON Text");
  }
  
}
