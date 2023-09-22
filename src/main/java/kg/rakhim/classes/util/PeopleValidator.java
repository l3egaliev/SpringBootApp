package kg.rakhim.classes.util;

import kg.rakhim.classes.model.Person;
import kg.rakhim.classes.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PeopleValidator implements Validator {
    private final PersonService personService;

    @Autowired
    public PeopleValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personService.findByFullName(person.getFullName()).isPresent()){
            errors.rejectValue("fullName", "", "Такой человек уже есть в нашей базе!");
        }
    }
}
