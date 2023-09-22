package kg.rakhim.classes.services;

import kg.rakhim.classes.model.Book;
import kg.rakhim.classes.model.Person;
import kg.rakhim.classes.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookServices {
    private final BookRepository bookRepository;
    private final PersonService personService;

    @Autowired
    public BookServices(BookRepository bookRepository, PersonService personService) {
        this.bookRepository = bookRepository;
        this.personService = personService;
//        this.personRepository = personRepository;
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public List<Book> sortByYear(){
        return bookRepository.findAll(Sort.by("year"));
    }
    public List<Book> findWithPagination(int page, int books_per_page){
        return bookRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("year"))).getContent();
    }

    public List<Book> searchByTitleStart(String text){
        if (bookRepository.findByTitleStartingWith(text) == null){
            return Collections.emptyList();
        }else
            return bookRepository.findByTitleStartingWith(text);
    }

    public Book findOne(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book){
        book.setBook_id(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    public Person getBookOwner(int id) {
        return bookRepository.findById(id).map(Book::getPerson).orElse(null);
    }

    @Transactional
    public void release(int id) {
//        bookRepository.findById(id).ifPresent(book -> {
//            book.setPerson(null);
//        });
        Book book = bookRepository.findById(id).get();
        book.setPerson(null);
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
//        Book book = bookRepository.findById(id).get();
//        book.setPerson(selectedPerson);
        bookRepository.findById(id).ifPresent(b -> {
            b.setPerson(selectedPerson);
        });
    }
}
