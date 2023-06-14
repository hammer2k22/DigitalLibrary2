create table person
(
    person_id     integer generated always as identity
        constraint person_pk
            primary key,
    name          varchar(30) not null,
    surname       varchar(30) not null,
    middlename    varchar(30),
    year_of_birth integer     not null
        constraint person_yearofbirth_check
            check ((year_of_birth >= 1900) AND (year_of_birth <= 2016)),
    constraint unique_person
        unique (name, surname, middlename, year_of_birth)
);

alter table person
    owner to postgres;


