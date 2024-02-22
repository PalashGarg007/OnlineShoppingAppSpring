package com.genpact.onlineShoppingApp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.genpact.onlineShoppingApp.entity.Shopkeeper;

@Component
class ApiHandler{
	//TODO: Not able to fetch the url from yml file. 
	@Value("${base.url}")
	private String baseUrl;
	private UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8080");
	@Autowired
	private RestTemplate restTemplate;
	private HttpHeaders headers = new HttpHeaders();
	
	public Shopkeeper shopkeeperLogInRequest(String userName, String password) throws IOException, URISyntaxException {
		System.out.println("--------------> " + baseUrl);
//		URI uri = new URI(baseUrl + "/shopkeeper/logIn");
		URI uri = builder.path("/shopkeeper/logIn")
				.build()
				.toUri();
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\r\n"
				+ "  \"userName\" : \"" + userName + "\",\r\n"
				+ "  \"password\" : \"" + password + "\"\r\n"
				+ "}",
				headers);
		
		return restTemplate.postForObject(uri, request, Shopkeeper.class);
		
	}
}
