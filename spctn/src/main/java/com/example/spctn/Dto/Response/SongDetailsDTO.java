package com.example.spctn.Dto.Response;


public record SongDetailsDTO(
    String aboutStory,
    String trivia,
    String description,
    String language,
    int year
) {}
