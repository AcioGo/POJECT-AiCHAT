package org.heyi.request.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "http-client.server")
public class HttpClientProperties {
    private Integer readTimeout = 10000;
    private Integer connectTimeout = 5000;
}
