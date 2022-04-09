package com.hong_hoan.iuheducation.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.http.codec.multipart.Part;

import java.io.IOException;

public class PartDeserializer extends JsonDeserializer<Part> {


    @Override
    public Part deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return null;
    }
}
