package com.omirc.library.repository;

import com.omirc.library.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    BookRepository repository;

    @Test
    public void emptyBookRepositoryTest() {
        List<Book> booksBefore = repository.findAll();
        Book book = new Book("Eminescu test", "Poezii");
        entityManager.persist(book);
        List<Book> booksAfter = repository.findAll();
        assertThat(booksBefore.size() + 1).isEqualTo(booksAfter.size());
    }
}