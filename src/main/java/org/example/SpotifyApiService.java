package org.example;

import org.example.Track;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpotifyApiService {
    private final SpotifyAuthService authService;
    private static final String SEARCH_URL = "https://api.spotify.com/v1/search";

    public SpotifyApiService(SpotifyAuthService authService) {
        this.authService = authService;
    }

    public List<Track> searchTracksByMood(String mood) {
        WebClient webClient = WebClient.create();

        Map<String, Object> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(SEARCH_URL)
                        .queryParam("q", mood)
                        .queryParam("type", "track")
                        .queryParam("limit", 10)
                        .build())
                .header("Authorization", "Bearer " + authService.getAccessToken())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response == null || response.isEmpty()) {
            return List.of();
        }

        List<Map<String, Object>> items = (List<Map<String, Object>>) ((Map<String, Object>) response.get("tracks")).get("items");
        return items.stream()
                .map(item -> new Track(
                        (String) item.get("name"),
                        (String) ((Map<String, Object>) item.get("artists")).get("name"),
                        (String) item.get("preview_url")
                ))
                .collect(Collectors.toList());
    }
}