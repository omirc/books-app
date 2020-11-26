package com.util.sample.library.resource;


import com.util.sample.library.model.BookModel;
import com.util.sample.library.service.DefaultLibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookResource {

    @Autowired
    private DefaultLibraryService service;

    @PostMapping
    public ResponseEntity<BookModel> saveBook(@Valid @RequestBody BookModel bookModel) {
        return ResponseEntity.ok(service.saveBook(bookModel));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookModel> findBookById(@PathVariable Long bookId) {
        return service.findBookById(bookId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable("bookId") Long bookId,
                                           @Valid @RequestBody BookModel bookModel,
                                           BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        bookModel.setId(bookId);
        service.saveBook(bookModel);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        service.delete(bookId);
    }


}
