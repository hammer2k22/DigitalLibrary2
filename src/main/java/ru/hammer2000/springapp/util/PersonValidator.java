package ru.hammer2000.springapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.hammer2000.springapp.model.Person;
import ru.hammer2000.springapp.services.PeopleService;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private  final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Person newPerson = (Person) o;
        Optional<Person> person = peopleService.existingReader(newPerson);

        if(person.isPresent() && person.get().getId()!= newPerson.getId()){
            errors.rejectValue("yearOfBirth","", "Этот читатель уже существует");
        }
    }
}