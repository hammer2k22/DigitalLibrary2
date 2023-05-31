package ru.hammer2000.springapp.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hammer2000.springapp.model.Book;
import ru.hammer2000.springapp.model.Person;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleStartingWithIgnoreCase(String title);
}
