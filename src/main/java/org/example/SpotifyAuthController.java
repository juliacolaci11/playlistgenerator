package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpotifyAuthController {

    @GetMapping("/login")
    public String login() {
        // URL para autenticación de Spotify
        String clientId = "tu-client-id";
        String redirectUri = "http://localhost:8080/callback";
        String scope = "playlist-modify-public";
        String authUrl = "https://accounts.spotify.com/authorize?client_id=" + clientId +
                "&response_type=code" +
                "&redirect_uri=" + redirectUri +
                "&scope=" + scope;

        return "Por favor, visita la siguiente URL para autenticarte: <a href=\"" + authUrl + "\">Login con Spotify</a>";
    }

    @GetMapping("/callback")
    public String handleSpotifyCallback(@RequestParam("code") String code) {
        // Aquí recibimos el código de autorización enviado por Spotify
        System.out.println("Authorization code received: " + code);

        // Este código se intercambiará por un token de acceso
        return "Authorization code: " + code;
    }
}
