package com.omirc.library.service;

import com.omirc.library.dto.BookDto;
import com.omirc.library.dto.GenericListDto;
import com.omirc.library.dto.filter.BookFilterDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookDto saveBook(BookDto bookDto);

    void delete(final Long bookId);

    Long countBooks();

    Optional<BookDto> findBookById(Long bookId);

    GenericListDto<BookDto> findAllBooks(final int page, final int size);

    List<BookDto> findAllBooks();

    GenericListDto<BookDto> findAllBooks(final BookFilterDto reviewFilterModel);
}
