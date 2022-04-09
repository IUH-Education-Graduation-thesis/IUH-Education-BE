package com.hong_hoan.iuheducation.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hong_hoan.iuheducation.config.PartDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class Config {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Part.class, new PartDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);
        return multipartResolver;
    }
}
