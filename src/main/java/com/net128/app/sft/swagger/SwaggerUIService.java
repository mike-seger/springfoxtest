package com.net128.app.sft.swagger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.base.Strings;
import io.swagger.models.Swagger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import javax.inject.Inject;

@Service
@Profile("dev")
public class SwaggerUIService {

    @Inject
    private DocumentationCache documentationCache;

    @Inject
    private ServiceModelToSwagger2Mapper mapper;

    private ObjectMapper ymlObjectMapper = new ObjectMapper(new YAMLFactory())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            ;

    private Swagger getSwaggerObject(String group) {
        if (Strings.isNullOrEmpty(group)) {
            return null;
        }
        Documentation documentation = this.documentationCache.documentationByGroup(group);
        if (documentation == null) {
            return null;
        } else {
            return mapper.mapDocumentation(documentation);
        }
    }

    public String getSwaggerYmlSpec(String group) throws JsonProcessingException {
        return ymlObjectMapper.writeValueAsString(getSwaggerObject(group));
    }

}