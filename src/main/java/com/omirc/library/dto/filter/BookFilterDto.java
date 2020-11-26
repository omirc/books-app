package com.omirc.library.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookFilterDto {

    @Builder.Default
    private Integer page = 0;

    @Builder.Default
    private Integer size = 10;

    private String titleLike;
}