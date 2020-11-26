package com.omirc.library.service;

import com.omirc.library.entity.Review;
import com.omirc.library.dto.mapper.BookMapper;
import com.omirc.library.dto.mapper.ReviewMapper;
import com.omirc.library.repository.BookRepository;
import com.omirc.library.repository.ReviewRepository;
import com.omirc.library.entity.Book;
import com.omirc.library.dto.BookDto;
import com.omirc.library.dto.GenericListDto;
import com.omirc.library.dto.ReviewDto;
import com.omirc.library.dto.filter.BookFilterDto;
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
    public BookDto saveBook(BookDto bookDto) {
        Book book = BookMapper.toEntity(bookDto);
        return BookMapper.toModel(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream()
                .map(BookMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public GenericListDto<BookDto> findAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id"));
        Page<Book> bookPage = bookRepository.findAll(pageable);
        return toGenericListFromPage(bookPage);
    }

    @Override
    public List<ReviewDto> findAllReviews(Book book) {
        List<Review> listReview =  reviewRepository.findByBook(book);
        return listReview.stream()
                .map(ReviewMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public GenericListDto<BookDto> findAllBooks(BookFilterDto bookFilterDto) {
        Pageable pageable = PageRequest.of(bookFilterDto.getPage(), bookFilterDto.getSize(), Sort.by("id"));

        Specification<Book> baseSpecification = Specification.where(null);

        if(StringUtils.hasText(bookFilterDto.getTitleLike())) {
            Specification<Book> nameSpecification = (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("title"), bookFilterDto.getTitleLike());

            baseSpecification = baseSpecification.and(nameSpecification);
        }

        Page<Book> bookPage = bookRepository.findAll(baseSpecification, pageable);
        return toGenericListFromPage(bookPage);
    }

    @Override
    public Optional<ReviewDto> findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(ReviewMapper::toModel);
    }

    @Override
    public ReviewDto saveReview(ReviewDto reviewDto) {
        Review review = ReviewMapper.toEntity(reviewDto);
        return ReviewMapper.toModel(reviewRepository.save(review));
    }

    @Override
    public void delete(Long bookId) {
        Optional<BookDto> bookById = findBookById(bookId);

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
    public Optional<BookDto> findBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .map(BookMapper::toModel);
    }


    private GenericListDto<BookDto> toGenericListFromPage(final Page<Book> bookPage) {
        List<BookDto> bookDtoList = bookPage
                .stream()
                .map(BookMapper::toModel)
                .collect(Collectors.toList());

        return GenericListDto.<BookDto>builder()
                .totalElements(bookPage.getTotalElements())
                .totalPages(bookPage.getTotalPages())
                .items(bookDtoList)
                .build();
    }

}
