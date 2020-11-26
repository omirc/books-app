package com.util.sample.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericListModel<T> {

    private List<T> items;
    private long totalElements;
    private int totalPages;
}