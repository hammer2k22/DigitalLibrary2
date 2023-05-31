package ru.hammer2000.springapp.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hammer2000.springapp.model.Book;
import ru.hammer2000.springapp.model.Person;
import ru.hammer2000.springapp.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundEmployee = peopleRepository.findById(id);
        return foundEmployee.orElse(null);
    }


    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }


    public List<Book> findBooksByPersonId(int id) {
        Person person = peopleRepository.findById(id).get();
        Hibernate.initialize(person.getBooks());
        List<Book> books = person.getBooks();
        books.forEach(book -> book.setDeadlineExpired((new Date().getTime() -
                                book.getIssueDate().getTime()) > 864000000));
        return books;
    }

    public Optional<Person> existingReader(Person person) {
        return peopleRepository.existingReader(person.getName(), person.getSurname(),
                person.getMiddlename(), person.getYearOfBirth());
    }
}