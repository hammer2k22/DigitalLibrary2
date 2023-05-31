package ru.hammer2000.springapp.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, message = "Название должно быть длиной от 2 символов")
    @Column(name = "title")
    private String title;

    @Size(min = 5, message = "Фамилия и имя автора должны быть минимум по 2 символа")
    @Column(name = "author")
    private String author;

    @Max(value = 2023, message = "Год не может быть позже 2023")
    @Min(value = 1900, message = "Год не может быть раньше 1900")
    @Column(name = "year")
    private int year;

    @Column(name = "issue_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueDate;

    @Transient
    private boolean isDeadlineExpired;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    public Book() {
    }

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public boolean isDeadlineExpired() {
        return isDeadlineExpired;
    }

    public void setDeadlineExpired(boolean deadlineExpired) {
        isDeadlineExpired = deadlineExpired;
    }

}