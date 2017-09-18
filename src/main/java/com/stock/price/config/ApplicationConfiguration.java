package com.stock.price.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {
 @Value("$infor.name")
 String appName;
    @Value("${connection.timeout}")
    private int timeout;

    @Primary
    @ConditionalOnProperty("connection.timeout")
    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        return getClientHttpRequestFactory();
    }

    private RestTemplate getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(timeout);
        factory.setConnectTimeout(timeout);
        return new RestTemplate(factory);
    }
}
