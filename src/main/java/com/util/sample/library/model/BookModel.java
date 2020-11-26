package com.util.sample.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookModel {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;
}
