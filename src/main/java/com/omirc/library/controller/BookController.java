package com.omirc.library.controller;

import com.omirc.library.dto.BookDto;
import com.omirc.library.dto.GenericListDto;
import com.omirc.library.dto.filter.BookFilterDto;
import com.omirc.library.exceptions.ApplicationException;
import com.omirc.library.exceptions.InvalidInputException;
import com.omirc.library.service.DefaultLibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final Logger logger = LoggerFactory.getLogger(BookController.class);


    private final DefaultLibraryService service;

    @Autowired
    public BookController(DefaultLibraryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> findAll() {
        try {
            return ResponseEntity.ok(service.findAllBooks());
        } catch (Exception exception) {
            logger.error("Cannot list the books", exception);
            throw new ApplicationException("Cannot list the books", exception);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<GenericListDto<BookDto>> findByAllBookFilter(
            @ModelAttribute BookFilterDto bookFilterDto) {
        try {
            return ResponseEntity.ok(service.findAllBooks(bookFilterDto));
        } catch (Exception exception) {
            logger.error("Cannot filter the list of the  books", exception);
            throw new ApplicationException("Cannot filter the list of the  books", exception);
        }
    }

    @PostMapping
    public ResponseEntity<BookDto> saveBook(@Valid @RequestBody BookDto bookDto,
                                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            logger.error("Cannot add the book {}", bindingResult.getAllErrors());
            throw new InvalidInputException("Invalid parameters for the Book");
        }

        try {
            return ResponseEntity.ok(service.saveBook(bookDto));
        } catch (Exception exception) {
            logger.error("Cannot save  books", exception);
            throw new ApplicationException("Cannot save  books", exception);
        }
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> findBookById(@PathVariable Long bookId) {
        try {
            return service.findBookById(bookId)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception exception) {
            logger.error("Unexpected error occurred", exception);
            throw new ApplicationException("Unexpected error occurred", exception);
        }
    }


    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable("bookId") Long bookId,
                                        @Valid @RequestBody BookDto bookDto,
                                        BindingResult bindingResult) {

        try {
            if (bindingResult.hasErrors()) {
                logger.error("Cannot update the book {}", bindingResult.getAllErrors());
                throw new InvalidInputException("Invalid parameters for the Book");
            }

            bookDto.setId(bookId);
            service.saveBook(bookDto);
            return ResponseEntity.ok(null);
        } catch (Exception exception) {
            logger.error("Cannot update the book", exception);
            throw new ApplicationException("Cannot update the book", exception);
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable("bookId") Long bookId) {
        try {
            service.delete(bookId);
            return ResponseEntity.ok(null);
        } catch (Exception exception) {
            logger.error("Cannot delete the book", exception);
            throw new ApplicationException("Cannot delete the book", exception);
        }
    }

    @GetMapping("/runtimeError")
    public void runtimeError() {
        throw new ApplicationException("Test exception");
    }
}
