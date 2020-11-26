package com.util.sample.library.service;

import com.util.sample.library.model.BookModel;
import com.util.sample.library.model.GenericListModel;
import com.util.sample.library.model.ReviewModel;
import com.util.sample.library.model.filter.BookFilterModel;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookModel saveBook(BookModel bookModel);

    void delete(final Long bookId);

    Long countBooks();

    Optional<BookModel> findBookById(Long bookId);

    GenericListModel<BookModel> findAllBooks(final int page, final int size);

    List<BookModel> findAllBooks();

    GenericListModel<BookModel> findAllBooks(final BookFilterModel reviewFilterModel);
}
