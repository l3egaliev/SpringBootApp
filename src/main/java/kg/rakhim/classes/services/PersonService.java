package kg.rakhim.classes.services;

import kg.rakhim.classes.model.Book;
import kg.rakhim.classes.model.Person;
import kg.rakhim.classes.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Person findOne(int id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person){
        person.setId(id);
        personRepository.save(person);
    }

    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }

    public List<Book> getBooksByPersonId(int id) {
        Person person = personRepository.findById(id).get();
        if (!person.getBooks().isEmpty()){
            return person.getBooks();
        }else
            return Collections.EMPTY_LIST;
    }

    public Optional<Person> findByFullName(String fullName){
        return personRepository.findByFullName(fullName);
    }
}

