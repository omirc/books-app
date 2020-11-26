package com.util.sample.library.commands;

import com.util.sample.library.model.BookModel;
import com.util.sample.library.service.DefaultLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ShellCommand {

    @Autowired
    private DefaultLibraryService service;

    @ShellMethod("insert test data")
    public void insert() {
        service.saveBook(new BookModel(null, "Maitreyi", "Mircea Eliade"));
        service.saveBook(new BookModel(null, "Nuntă în cer", "Mircea Eliade"));
        service.saveBook(new BookModel(null, "Un veac de singuratate", "Gabriel Garcia Marquez"));
        service.saveBook(new BookModel(null, "Cel mai iubit dintre pământeni", "Marin Preda"));
        service.saveBook(new BookModel(null, "Crimă și pedeapsă", "Feodor Dostoievski"));
        service.saveBook(new BookModel(null, "Pădurea spânzuraților", "Liviu Rebreanu"));
        service.saveBook(new BookModel(null, "Portretul lui Dorian Gray", "Oscar Wilde"));
        service.saveBook(new BookModel(null, "Străinul", "Albert Camus"));
        service.saveBook(new BookModel(null, "Micul Prinț", "Antoine de Saint – Exupery"));
        service.saveBook(new BookModel(null, "Pe aripile vântului", "Margaret Mitchell"));
        service.saveBook(new BookModel(null, "Ora 25", "Constantin Virgil Gheorghiu"));
        service.saveBook(new BookModel(null, "Bătrânul și marea", "Ernest Hemingway"));
        service.saveBook(new BookModel(null, "Povara bunătăţii noastre", "Ion Druță"));
        service.saveBook(new BookModel(null, "Ciuleandra", "Liviu Rebreanu"));
        service.saveBook(new BookModel(null, "Adam și Eva", "Liviu Rebreanu"));
        service.saveBook(new BookModel(null, "Dragostea durează 3 ani", "Frédéric Beigbeder"));
    }
}


