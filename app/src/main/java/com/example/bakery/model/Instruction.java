package com.example.bakery.model;

public class Instruction {
    private String shortDescription;
    private String description;
    private String videoURL;

    public Instruction(String shortDescription, String description, String videoURL) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }
}
