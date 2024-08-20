package com.educaguard.chat.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
public class SecurityConfig {

    @Bean
    public HandshakeInterceptor jwtInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(
                    ServerHttpRequest request,
                    ServerHttpResponse response,
                    WebSocketHandler wsHandler,
                    Map<String, Object> attributes
            ) throws Exception {
                var auth = request.getURI().getQuery().replace("jwt=", "");
                return validateJwt(auth);
            }

            @Override
            public void afterHandshake(
                    ServerHttpRequest request,
                    ServerHttpResponse response,
                    WebSocketHandler wsHandler,
                    Exception exception
            ) {

            }
        };
    }

    private boolean validateJwt(String auth) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://127.0.0.1:8080/api/login/validate"; // TODO replace with Backend URL property
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(auth, headers);
        ResponseEntity<?> result = restTemplate.exchange(uri, HttpMethod.POST, entity, Void.class);

        return result.getStatusCode().equals(HttpStatus.OK);
    }

}
