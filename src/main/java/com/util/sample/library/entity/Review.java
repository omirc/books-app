package com.util.sample.library.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "review")
@SequenceGenerator(name = "sequence_generator", sequenceName = "review_seq", allocationSize = 1)
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_book")
    private Book book;

    @NotNull
    @Column(name = "comment_body")
    @Size( max = 1000)
    private String commentBody;


    @Column(name = "comment_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date commentDate;
}
