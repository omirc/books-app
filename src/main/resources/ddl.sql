CREATE SEQUENCE book_seq;
CREATE TABLE book (
    id BIGINT NOT NULL DEFAULT NEXTVAL ('book_seq'),
    author VARCHAR(100) NOT NULL,
	title VARCHAR(100) NOT NULL,
    version INT NOT NULL DEFAULT 0,
    CONSTRAINT book_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE review_seq;
CREATE TABLE review (
    id BIGINT NOT NULL DEFAULT NEXTVAL ('review_seq'),
    fk_book BIGINT NOT NULL,
    comment_body VARCHAR(100) NOT NULL,
    comment_date DATE NOT NULL,
    version INT NOT NULL DEFAULT 0,
    CONSTRAINT nom_judet_pkey PRIMARY KEY (id),
    CONSTRAINT fk_review_book
        FOREIGN KEY (fk_book)
        REFERENCES book(id)
);