package ru.hammer2000.springapp.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, max = 30, message = "Имя должно быть длиной от 2 до 30 символов")
    @Column(name = "name")
    private String name;

    @Size(min = 2, max = 30, message = "Фамилия должна быть длиной от 2 до 30 символов")
    @Column(name = "surname")
    private String surname;

    @Size(max = 30, message = "Отчество должно быть длиной до 30 символов")
    @Column(name = "middlename")
    private String middlename;

    @Max(value = 2016, message = "Год рождения не может быть позже 2016")
    @Min(value = 1900, message = "Год рождения не может быть раньше 1900")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;


    public Person() {
    }

    public Person(int id, String name, String surname, String middlename, int yearOfBirth) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.middlename = middlename;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getFullName() {
        return surname + " " + name + " " + middlename;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}