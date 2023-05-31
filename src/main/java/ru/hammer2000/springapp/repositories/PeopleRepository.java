package ru.hammer2000.springapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hammer2000.springapp.model.Person;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    @Query("from Person p where p.name = :name and p.surname = :surname" +
            " and p.middlename = :middlename and p.yearOfBirth = :yearOfBirth")
    Optional<Person> existingReader(@Param("name")String name, @Param("surname")String surname,
                                    @Param("middlename")String middlename,@Param("yearOfBirth") int yearOfBirth);

}
