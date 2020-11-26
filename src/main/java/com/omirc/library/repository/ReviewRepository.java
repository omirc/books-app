package com.omirc.library.repository;

import com.omirc.library.entity.Book;
import com.omirc.library.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>,
        JpaSpecificationExecutor<Review> {

    List<Review> findByBook(Book book);
}
