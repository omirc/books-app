package com.omirc.library.controller;

import com.omirc.library.exceptions.ApplicationException;
import com.omirc.library.dto.BookDto;
import com.omirc.library.dto.GenericListDto;
import com.omirc.library.service.DefaultLibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@Controller
public class ThymeleafBookController {

    private final Logger logger = LoggerFactory.getLogger(ThymeleafBookController.class);


    private static final int ROWS_PER_PAGE = 5;

    @Autowired
    private DefaultLibraryService service;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Bine ati venit la librarie virtuala ");
        return "index";
    }

    @GetMapping(value = {"/books/add"})
    public String showAddBook(Model model) {
        BookDto bookDto = new BookDto();
        model.addAttribute("add", true);
        model.addAttribute("book", bookDto);

        return "book";
    }

    @GetMapping(value = {"/books/{bookId}/edit"})
    public String showEditBook(Model model, @PathVariable long bookId) {

        Optional<BookDto> bookById = service.findBookById(bookId);
        if (bookById.isPresent()) {
            model.addAttribute("add", false);
            model.addAttribute("book", bookById.get());
        } else {
            logger.error("Failed to edit the book, could not find any book with this id: {}", bookId);

            model.addAttribute("errorMessage", "Book not found");
        }

        return "book";
    }

    @PostMapping(value = {"/books/{bookId}/edit"})
    public String updateBook(Model model,
                             @PathVariable("bookId") long bookId,
                             @Valid @ModelAttribute("book") BookDto bookDto,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            logger.error("Failed to update a book {}", bindingResult.getAllErrors());
            model.addAttribute("errorMessage", "failed to save the Book");
            model.addAttribute("add", false);
            return "book";
        }


        try {
            bookDto.setId(bookId);
            service.saveBook(bookDto);
            logger.info("OK. A new book was updated {}", bookDto);
            return "redirect:/books?update=true";
        } catch (Exception exception) {
            throw new ApplicationException("cannot update the book", exception);
        }

    }

    @PostMapping(value = {"/books/add"})
    public String addBook(Model model,
                          @Valid @ModelAttribute("book") BookDto bookDto,
                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            logger.error("Failed to add a new book {}", bindingResult.getAllErrors());
            model.addAttribute("errorMessage", "failed to save the Book; validation failed");
            model.addAttribute("add", true);
            model.addAttribute("book", bookDto);
            return "book";
        }

        try {
            service.saveBook(bookDto);
            logger.info("OK. A new book was added {}", bookDto);
            return "redirect:/books?create=true";
        } catch (Exception exception) {
            throw new ApplicationException("cannot save the book", exception);
        }
    }

    @GetMapping(value = "/books")
    public String getBooks(Model model,
                           @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        GenericListDto<BookDto> books = service.findAllBooks(pageNumber, ROWS_PER_PAGE);

        long count = service.countBooks();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROWS_PER_PAGE) < count;
        model.addAttribute("books", books);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "books";
    }

    @PostMapping(value = {"/books/{bookId}/delete"})
    public String deleteBook(@PathVariable long bookId) {
        try {
            service.delete(bookId);
            logger.info("OK. The book was deleted {}", bookId);
        } catch (Exception exception) {
            throw new ApplicationException("cannot delete the book", exception);
        }

        return "redirect:/books";
    }

    @GetMapping(value = {"/books/{bookId}/view"})
    public String viewBook(Model model, @PathVariable long bookId) {

        try {
            Optional<BookDto> bookById = service.findBookById(bookId);
            if (bookById.isPresent()) {
                model.addAttribute("book", bookById.get());
            } else {
                model.addAttribute("errorMessage", "Book not found");
            }
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Unexpected error occurred");
            logger.error("Unexpected error", ex);
        }

        return "view";
    }
}
