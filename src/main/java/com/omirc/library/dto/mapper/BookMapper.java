package com.omirc.library.dto.mapper;

import com.omirc.library.entity.Book;
import com.omirc.library.dto.BookDto;

public class BookMapper {

    public static Book toEntity(final BookDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .author(bookDto.getAuthor())
                .title(bookDto.getTitle())
                .build();
    }

    public static BookDto toModel(final Book book) {
        return BookDto.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .title(book.getTitle())
                .build();
    }
}