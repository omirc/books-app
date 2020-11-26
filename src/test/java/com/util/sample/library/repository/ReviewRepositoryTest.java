package com.util.sample.library.repository;

import com.util.sample.library.entity.Book;
import com.util.sample.library.entity.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ReviewRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ReviewRepository repository;

    @Test
    public void emptyRepositoryTest() {
        List<Review> reviews = repository.findAll();
        assertThat(reviews).isEmpty();
    }

    @Test
    public void oneRecordTest() {
        Book book = new Book("Eminescu", "Poezii");
        Date current = new Date();
        Review review = repository.save(new Review(book, "comentariu ok", current));

        assertThat(review).hasFieldOrPropertyWithValue("commentBody", "comentariu ok");
        assertThat(review).hasFieldOrPropertyWithValue("commentDate", current);
        assertThat(review).hasFieldOrPropertyWithValue("book", book);
    }


    @Test
    public void findAllRepoTest() {
        Book book = new Book("Eminescu", "Poezii");
        Date current = new Date();

        entityManager.persist(book);

        Review rev1 = new Review(book, "comentariu 1", current);
        entityManager.persist(rev1);

        Review rev2 = new Review(book, "comentariu 2", current);
        entityManager.persist(rev2);

        Review rev3 = new Review(book, "comentariu 3", current);
        entityManager.persist(rev3);

        Iterable<Review> tutorials = repository.findAll();

        assertThat(tutorials).hasSize(3).contains(rev1, rev2, rev3);
    }


    @Test
    public void findReviewByIdTest() {
        Book book = new Book("Eminescu", "Poezii");
        Date current = new Date();

        entityManager.persist(book);

        Review rev1 = new Review(book, "comentariu 1", current);
        entityManager.persist(rev1);

        Review rev2 = new Review(book, "comentariu 2", current);
        entityManager.persist(rev2);

        Review foundReview = repository.findById(rev2.getId()).get();

        assertThat(foundReview).isEqualTo(rev2);
    }

    @Test
    public void findReviewByBookTest() {
        Date current = new Date();
        
        Book book1 = new Book("Eminescu", "Poezii");
        entityManager.persist(book1);
        Book book2 = new Book("Blaga", "Poezii");
        entityManager.persist(book2);

        Review rev1 = new Review(book1, "comentariu 1", current);
        entityManager.persist(rev1);

        Review rev2 = new Review(book2, "comentariu 2", current);
        entityManager.persist(rev2);

        Iterable<Review> reviewList1 =  repository.findByBook(book1);
        assertThat(reviewList1).hasSize(1).contains(rev1);

        Iterable<Review> reviewList2 =  repository.findByBook(book2);
        assertThat(reviewList2).hasSize(1).contains(rev2);
    }
}