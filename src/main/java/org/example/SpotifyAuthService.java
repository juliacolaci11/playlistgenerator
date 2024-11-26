package org.example;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;
import java.util.Map;

@Service
public class SpotifyAuthService {
    private static final String TOKEN_URL = "https://accounts.spotify.com/authorize?client_id=0b58db893e8c4d2d930115010bcc7438&response_type=code&redirect_uri=http://localhost:8080/callback&scope=playlist-modify-public\n";
    private static final String CLIENT_ID = "0b58db893e8c4d2d930115010bcc7438"; // Reemplaza con tu Client ID
    private static final String CLIENT_SECRET = "94338b7db9194a5bb28f99c5f61a35ad"; // Reemplaza con tu Client Secret
    private String accessToken;

    public String getAccessToken() {
        if (accessToken == null) {
            accessToken = fetchAccessToken();
        }
        return accessToken;
    }

    private String fetchAccessToken() {
        String encodedCredentials = Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes());

        WebClient webClient = WebClient.create();
        Map<String, String> response = webClient.post()
                .uri(UriComponentsBuilder.fromHttpUrl(TOKEN_URL).toUriString())
                .header("Authorization", "Basic " + encodedCredentials)
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return response != null ? response.get("access_token") : null;
    }
}