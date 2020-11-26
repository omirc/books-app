package com.util.sample.library.service;

import com.util.sample.library.entity.Book;
import com.util.sample.library.entity.Review;
import com.util.sample.library.model.BookModel;
import com.util.sample.library.model.GenericListModel;
import com.util.sample.library.model.ReviewModel;
import com.util.sample.library.model.filter.BookFilterModel;
import com.util.sample.library.model.mapper.BookMapper;
import com.util.sample.library.model.mapper.ReviewMapper;
import com.util.sample.library.repository.BookRepository;
import com.util.sample.library.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DefaultLibraryService implements BookService, ReviewService {

    private final BookRepository bookRepository;

    private final ReviewRepository reviewRepository;

    @Autowired
    public DefaultLibraryService(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public BookModel saveBook(BookModel bookModel) {
        Book book = BookMapper.toEntity(bookModel);
        return BookMapper.toModel(bookRepository.save(book));
    }

    @Override
    public List<BookModel> findAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream()
                .map(BookMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public GenericListModel<BookModel> findAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id"));
        Page<Book> bookPage = bookRepository.findAll(pageable);
        return toGenericListFromPage(bookPage);
    }

    @Override
    public List<ReviewModel> findAllReviews(Book book) {
        List<Review> listReview =  reviewRepository.findByBook(book);
        return listReview.stream()
                .map(ReviewMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public GenericListModel<BookModel> findAllBooks(BookFilterModel bookFilterModel) {
        Pageable pageable = PageRequest.of(bookFilterModel.getPage(), bookFilterModel.getSize(), Sort.by("id"));

        Specification<Book> baseSpecification = Specification.where(null);

        if(StringUtils.hasText(bookFilterModel.getTitleLike())) {
            Specification<Book> nameSpecification = (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("title"), bookFilterModel.getTitleLike());

            baseSpecification = baseSpecification.and(nameSpecification);
        }

        Page<Book> bookPage = bookRepository.findAll(baseSpecification, pageable);
        return toGenericListFromPage(bookPage);
    }

    @Override
    public Optional<ReviewModel> findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(ReviewMapper::toModel);
    }

    @Override
    public ReviewModel saveReview(ReviewModel reviewModel) {
        Review review = ReviewMapper.toEntity(reviewModel);
        return ReviewMapper.toModel(reviewRepository.save(review));
    }

    @Override
    public void delete(Long bookId) {
        Optional<BookModel> bookById = findBookById(bookId);

        if (bookById.isPresent()) {
            List<Review> listReviews = reviewRepository.findByBook(BookMapper.toEntity(bookById.get()));
            if (listReviews != null && !listReviews.isEmpty()) {
                for (Review review : listReviews) {
                    reviewRepository.delete(review);
                }
            }
        }

        bookRepository.deleteById(bookId);
    }

    @Override
    public Long countBooks() {
        return bookRepository.count();
    }

    @Override
    public Optional<BookModel> findBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .map(BookMapper::toModel);
    }


    private GenericListModel<BookModel> toGenericListFromPage(final Page<Book> bookPage) {
        List<BookModel> bookModelList = bookPage
                .stream()
                .map(BookMapper::toModel)
                .collect(Collectors.toList());

        return GenericListModel.<BookModel>builder()
                .totalElements(bookPage.getTotalElements())
                .totalPages(bookPage.getTotalPages())
                .items(bookModelList)
                .build();
    }

}
