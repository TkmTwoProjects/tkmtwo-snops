package com.tkmtwo.snops.jackson;

import static com.google.common.base.TkmTwoStrings.isBlank;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.tkmtwo.snops.time.DateTimeFormatters;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 *
 */
public final class LocalDateTimeDeserializer
  extends StdScalarDeserializer<LocalDateTime> {
  
  private static final long serialVersionUID = 1L;
  
  public LocalDateTimeDeserializer() {
    super(LocalDateTime.class);
  }
  
  public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
    throws IOException, JsonProcessingException {
    
    if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING) {
      String dtText = jsonParser.getText();
      if (isBlank(dtText)) {
        return null;
      }
      return LocalDateTime.parse(dtText, DateTimeFormatters.LOCAL_DATETIME_EXTENDED);
    }
    
    throw deserializationContext.mappingException("Expected JSON Text");
  }
  
}
