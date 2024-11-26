package org.example;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SpotifyAuthManager {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Value("${spotify.redirect.uri}")
    private String redirectUri;

    public String getAuthorizationUrl() {
        return UriComponentsBuilder.fromHttpUrl("https://accounts.spotify.com/authorize")
                .queryParam("client_id", clientId)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", "user-read-private playlist-read-private playlist-modify-private")
                .toUriString();
    }

    public String getAccessToken(String code) {
        WebClient webClient = WebClient.create();

        // Realiza una solicitud POST para obtener el token
        String response = webClient.post()
                .uri("https://accounts.spotify.com/api/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(UriComponentsBuilder.newInstance()
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("code", code)
                        .queryParam("redirect_uri", redirectUri)
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", clientSecret)
                        .build()
                        .getQuery())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }
}
