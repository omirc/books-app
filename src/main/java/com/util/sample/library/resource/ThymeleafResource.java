package com.util.sample.library.resource;

import com.util.sample.library.model.BookModel;
import com.util.sample.library.model.GenericListModel;
import com.util.sample.library.service.DefaultLibraryService;
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
public class ThymeleafResource {

    private final Logger logger = LoggerFactory.getLogger(ThymeleafResource.class);


    private final int ROW_PER_PAGE = 5;

    @Autowired
    private DefaultLibraryService service;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Bine ati venit la librarie virtuala ");
        return "index";
    }

    @GetMapping(value = {"/books/add"})
    public String showAddBook(Model model) {
        BookModel bookModel = new BookModel();
        model.addAttribute("add", true);
        model.addAttribute("book", bookModel);

        return "book";
    }

    @GetMapping(value = {"/books/{bookId}/edit"})
    public String showEditBook(Model model, @PathVariable long bookId) {

        Optional<BookModel> bookById = service.findBookById(bookId);
        if (bookById.isPresent()) {
            model.addAttribute("add", false);
            model.addAttribute("book", bookById.get());
        } else {
            logger.error("Failed to edit the book, could not find any book with this id: ", bookId);

            model.addAttribute("errorMessage", "Book not found");
        }

        return "book";
    }

    @PostMapping(value = {"/books/{bookId}/edit"})
    public String updateBook(Model model,
                             @PathVariable("bookId") long bookId,
                             @Valid @ModelAttribute("book") BookModel bookModel,
                             BindingResult bindingResult) {


        if (!bindingResult.hasErrors()) {
            bookModel.setId(bookId);
            service.saveBook(bookModel);
            logger.info("OK. A new book was updated", bookModel);
            return "redirect:/books";
        } else {
            logger.error("Failed to update a book", bindingResult.getAllErrors());
            model.addAttribute("errorMessage", "failed to save the Book");
            model.addAttribute("add", false);
            return "book";
        }
    }

    @PostMapping(value = {"/books/add"})
    public String addBook(Model model,
                          @Valid @ModelAttribute("book") BookModel bookModel,
                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            logger.error("Failed to add a new book", bindingResult.getAllErrors());
            model.addAttribute("errorMessage", "failed to save the Book; validation failed");
            model.addAttribute("add", true);
            model.addAttribute("book", bookModel);
            return "book";
        }

        try {
            service.saveBook(bookModel);
            logger.info("OK. A new book was added", bookModel);
            return "redirect:/books?create=true";
        } catch (Exception ex) {
            logger.error("Failed to update a new book", ex);
            model.addAttribute("errorMessage", "unexpected exception; check the fields length (at least 2 chars)");
            model.addAttribute("add", true);
            model.addAttribute("book", bookModel);
            return "book";
        }
    }

    @GetMapping(value = "/books")
    public String getBooks(Model model,
                           @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        GenericListModel<BookModel> books = service.findAllBooks(pageNumber, ROW_PER_PAGE);

        long count = service.countBooks();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("books", books);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "books";
    }

    @PostMapping(value = {"/books/{bookId}/delete"})
    public String deleteBook( @PathVariable long bookId) {
        try {
            service.delete(bookId);

        } catch (Exception ex) {
            logger.error("Failed to delete the book", ex);
        }

        return "redirect:/books";
    }

    @GetMapping(value = {"/books/{bookId}/view"})
    public String viewBook(Model model, @PathVariable long bookId) {

        try {
            Optional<BookModel> bookById = service.findBookById(bookId);
            if (bookById.isPresent()) {
                model.addAttribute("book", bookById.get());
            } else {
                model.addAttribute("errorMessage", "Book not found");
            }
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Unexpected error occured");
            logger.error("Unexpected error", ex);
        }

        return "view";
    }
}
