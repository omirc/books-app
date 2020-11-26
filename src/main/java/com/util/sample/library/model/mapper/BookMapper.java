package com.util.sample.library.model.mapper;

import com.util.sample.library.entity.Book;
import com.util.sample.library.model.BookModel;

public class BookMapper {

    public static Book toEntity(final BookModel bookModel) {
        return Book.builder()
                .id(bookModel.getId())
                .author(bookModel.getAuthor())
                .title(bookModel.getTitle())
                .build();
    }

    public static BookModel toModel(final Book book) {
        return BookModel.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .title(book.getTitle())
                .build();
    }
}