package com.util.sample.library.model;

import com.util.sample.library.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewModel {

    private Long id;

    @NotBlank
    private String commentBody;

    @NotBlank
    private Date commentDate;

    @NotBlank
    private Book book;
}
