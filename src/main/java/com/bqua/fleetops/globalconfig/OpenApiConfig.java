package com.bqua.fleetops.globalconfig;

import com.bqua.fleetops.common.dto.ErrorResponse;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class OpenApiConfig {


    @Bean
    public OperationCustomizer globalResponseCustomizer() {
        return (operation, handlerMethod) -> {
            ApiResponses responses = operation.getResponses();
            if (responses != null) {
                if (!responses.containsKey("400")) {
                    responses.addApiResponse("400", createApiResponse(HttpStatus.BAD_REQUEST.getReasonPhrase()));
                }
                if (!responses.containsKey("404")) {
                    responses.addApiResponse("404", createApiResponse(HttpStatus.NOT_FOUND.getReasonPhrase()));
                }
                if (!responses.containsKey("409")) {
                    responses.addApiResponse("409", createApiResponse(HttpStatus.CONFLICT.getReasonPhrase()));
                }
                if (!responses.containsKey("500")) {
                    responses.addApiResponse("500", createApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
                }
            }
            return operation;
        };
    }

    private ApiResponse createApiResponse(String description) {
        ApiResponse response = new ApiResponse()
                .description(description);

        Content content = new Content();
        MediaType mediaType = new MediaType();

        Schema<?> schema = ModelConverters.getInstance()
                .readAllAsResolvedSchema(ErrorResponse.class)
                .schema;

        mediaType.setSchema(schema);
        content.addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, mediaType);

        response.setContent(content);
        return response;
    }
}