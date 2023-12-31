package kg.rakhim.classes.controllers;


import jakarta.validation.Valid;
import kg.rakhim.classes.model.Person;
import kg.rakhim.classes.services.BookServices;
import kg.rakhim.classes.services.PersonService;
//import kg.rakhim.classes.util.PeopleValidator;
import kg.rakhim.classes.util.PeopleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/people")
@Transactional
public class PeopleController {
    private final PersonService personService;
    private final PeopleValidator peopleValidator;
    private final BookServices bookServices;

    @Autowired
    public PeopleController(PersonService personService, PeopleValidator peopleValidator, BookServices bookServices) {
        this.personService = personService;
        this.peopleValidator = peopleValidator;
        this.bookServices = bookServices;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("people", personService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model){
        model.addAttribute("person", personService.findOne(id));
        model.addAttribute("books", personService.getBooksByPersonId(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person")Person person){
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") Person person, BindingResult bindingResult){
        peopleValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/new";
        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("edit_person", personService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("edit_person") @Valid Person person,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "people/edit";

        personService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id){
        personService.delete(id);
        return "redirect:/people";
    }
}
