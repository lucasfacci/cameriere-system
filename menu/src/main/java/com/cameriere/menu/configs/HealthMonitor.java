package com.cameriere.menu.configs;

import java.util.Base64;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HealthMonitor implements HealthIndicator {
	
	@Override
	public Health health() {
		RestTemplate restTemplate = new RestTemplate();
		final String url = "http://localhost:8080/products";
		String auth = "root" + ":" + "root";
		String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", encodedAuth);
		HttpEntity<String> params = new HttpEntity<String>("parameters", headers);
		ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, params, Object.class);

		if (response.getStatusCodeValue() == 200) {
			return Health.up().build();
		} else {
			return Health.down().build();
		}
	}
}
