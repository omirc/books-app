package com.omirc.library.controller;


import com.omirc.library.dto.BookDto;
import com.omirc.library.service.DefaultLibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private DefaultLibraryService service;

    @PostMapping
    public ResponseEntity<BookDto> saveBook(@Valid @RequestBody BookDto bookDto) {
        return ResponseEntity.ok(service.saveBook(bookDto));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> findBookById(@PathVariable Long bookId) {
        return service.findBookById(bookId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable("bookId") Long bookId,
                                           @Valid @RequestBody BookDto bookDto,
                                           BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        bookDto.setId(bookId);
        service.saveBook(bookDto);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        service.delete(bookId);
    }


}
