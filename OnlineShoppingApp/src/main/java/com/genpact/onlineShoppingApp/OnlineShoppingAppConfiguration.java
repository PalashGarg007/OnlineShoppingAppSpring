package com.genpact.onlineShoppingApp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.genpact.onlineShoppingApp.controller.Views;

@Configuration
public class OnlineShoppingAppConfiguration {
	
	@Bean
    ObjectMapper getObjectMapper() {
		return JsonMapper.builder()
				.findAndAddModules()
				.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
				.build();
	}
    
    @Bean
    RestTemplate getRestTemplate() {
    	return new RestTemplate();
    }
    
    @Bean
    Views getViews() {
    	return new Views();
    }
    
}