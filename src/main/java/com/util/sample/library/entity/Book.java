package com.util.sample.library.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "book")
@SequenceGenerator(name = "sequence_generator", sequenceName = "book_seq", allocationSize = 1)
public class Book extends BaseEntity {


    @NotNull
    @Column(name = "title")
    @Size(min = 2, max = 100)
    private String title;

    @NotNull
    @Column(name = "author")
    @Size(max = 100)
    private String author;
}
