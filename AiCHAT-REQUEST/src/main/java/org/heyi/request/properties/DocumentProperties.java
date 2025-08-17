package org.heyi.request.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "document.api")
public class DocumentProperties {
    private String baseUrl;
    private String model;
    private Integer maxTokens;
    private Double temperature;
}
