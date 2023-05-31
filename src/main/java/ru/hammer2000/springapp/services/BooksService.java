package ru.hammer2000.springapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hammer2000.springapp.model.Book;
import ru.hammer2000.springapp.model.Person;
import ru.hammer2000.springapp.repositories.BooksRepository;
import ru.hammer2000.springapp.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public List<Book> findAll(String sortParam) {
        return booksRepository.findAll(Sort.by(sortParam));
    }

    public List<Book> findAll(Integer page, Integer booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage))
                .getContent();
    }

    public List<Book> findAll(Integer page, Integer booksPerPage, String sortParam) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by(sortParam))).getContent();
    }

    public Book findOne(int id) {
        Optional<Book> foundEmployee = booksRepository.findById(id);
        return foundEmployee.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        book.setOwner(findOne(id).getOwner());
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void addBookToPerson(int id, Person person) {
        Book bookToBeUpdate = findOne(id);
        Person owner = peopleRepository.findById(person.getId()).orElse(null);
        bookToBeUpdate.setOwner(owner);
        bookToBeUpdate.setIssueDate(new Date());
        booksRepository.save(bookToBeUpdate);
    }

    @Transactional
    public void removeBookFromPerson(int id) {
        Book bookToBeUpdate = findOne(id);
        bookToBeUpdate.setOwner(null);
        bookToBeUpdate.setIssueDate(null);
        booksRepository.save(bookToBeUpdate);
    }


    public List<Book> searchBook(String title) {

        return booksRepository.findByTitleStartingWithIgnoreCase(title);
    }
}