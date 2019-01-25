package com.kargo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * Created by abner.zhang on 2018/9/3.
 */
@Configuration
public class RestTemplateConfig {


    @Bean(name="requestFactory")
    public SimpleClientHttpRequestFactory requestFactory(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(30000);
        requestFactory.setConnectTimeout(30000);
        return requestFactory;
    }



    @Bean(name="restTemplate")
    public RestTemplate restTemplate(@Autowired SimpleClientHttpRequestFactory requestFactory){
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }



}
