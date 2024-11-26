package org.example;

public class Track {
    private String name;
    private String artist;
    private String previewUrl;

    public Track(String name, String artist, String previewUrl) {
        this.name = name;
        this.artist = artist;
        this.previewUrl = previewUrl;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }
}