create table book
(
    book_id   integer generated always as identity
        primary key,
    person_id integer
                      references person
                          on delete set null,
    title     varchar not null,
    author    varchar not null,
    year      integer not null
        constraint book_year_check
            check ((year >= 1900) AND (year <= 2023))
);

alter table book
    owner to postgres;


