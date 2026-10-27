package kz.iitu.lab8;

import java.util.List;

// the API returns this DTO, never the entity itself
public record BookResponse(Long id, String title, Genre genre, int year,
                           boolean archived, String author, List<String> tags) { }
