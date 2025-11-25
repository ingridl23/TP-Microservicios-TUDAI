package com.microservicio.servicio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import com.microservicio.dto.MonopatinDTO;

public class RestParada {
    private static final String MONOPATINES_URL = "http://localhost:8081/monopatines?paradaId=";/*Extraer la URL del microservicio en una constante para evitar hardcodeo:*/
    private final RestTemplate restTemplate;

    public RestParada() {
		this.restTemplate = new RestTemplate();
    }
	public MonopatinDTO[] PedidoRest(Long idParada){
	String url = MONOPATINES_URL + idParada;

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String token = null;

    if (auth != null && auth.getCredentials() != null) {
        token = auth.getCredentials().toString();
    }

    HttpHeaders headers = new HttpHeaders();
    if (token != null) {
        headers.set("Authorization", "Bearer " + token);
    }

    HttpEntity<Void> entity = new HttpEntity<>(headers);;
    ResponseEntity<MonopatinDTO[]> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            MonopatinDTO[].class
    );

    MonopatinDTO[] body = response.getBody();
    return body;
	}
	
	 @Configuration
	    public static class RestTemplateConfig {
	        @Bean
	        public RestTemplate restTemplate() {
	            return new RestTemplate();
	        }
	    }
}
