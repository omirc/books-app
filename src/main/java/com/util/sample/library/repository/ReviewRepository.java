package com.util.sample.library.repository;

import com.util.sample.library.entity.Book;
import com.util.sample.library.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>,
        JpaSpecificationExecutor<Review> {

    List<Review> findByBook(Book book);
}
