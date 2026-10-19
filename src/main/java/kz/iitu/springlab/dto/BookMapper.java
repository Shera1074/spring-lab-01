package kz.iitu.springlab.dto;

import kz.iitu.springlab.catalog.Book;

public class BookMapper {

    public static Book toEntity(BookRequest request, Long id) {
        return new Book(
                id,
                request.title(),
                request.author(),
                request.year()
        );
    }
}