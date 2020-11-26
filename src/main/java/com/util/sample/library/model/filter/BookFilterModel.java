package com.util.sample.library.model.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookFilterModel {

    private Integer page = 0;
    private Integer size = 10;
    private String titleLike;
}