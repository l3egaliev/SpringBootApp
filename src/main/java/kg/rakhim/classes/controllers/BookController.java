package kg.rakhim.classes.controllers;

import jakarta.validation.Valid;
import kg.rakhim.classes.model.Book;
import kg.rakhim.classes.model.Person;
import kg.rakhim.classes.services.BookServices;
import kg.rakhim.classes.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookServices bookServices;
    private final PersonService personService;

    @Autowired
    public BookController(BookServices bookServices, PersonService personService) {
        this.bookServices = bookServices;
        this.personService = personService;
    }

    // Index
    @GetMapping
    public String index(@RequestParam(value = "page", required = false)Integer page,
            @RequestParam(value = "books_per_page", required = false)Integer books_per_page, Model model){

        if (page == null&books_per_page==null){
            model.addAttribute("books", bookServices.sortByYear());
        }else {
            model.addAttribute("books", bookServices.findWithPagination(page, books_per_page));
        }
        return "books/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "text", required = false) String text, Model model){
        model.addAttribute("foundedBooks", bookServices.searchByTitleStart(text));
        return "books/search";
    }

    @GetMapping("{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookServices.findOne(id));
        if (bookServices.getBookOwner(id) == null){
            model.addAttribute("people", personService.findAll());
        }else {
            Person owner = bookServices.getBookOwner(id);
            model.addAttribute("owner", owner);
        }

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/new";
        bookServices.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookServices.findOne(id));
        return "books/edit";
    }

    @PatchMapping("{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/edit";

        bookServices.update(id, book);
        return "redirect:/books";

    }
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id){
        bookServices.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookServices.release(id);
        return "redirect:/books/"+id;
    }
//
    @PatchMapping("{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson){
        bookServices.assign(id, selectedPerson);
        return "redirect:/books/"+id;
    }

}
