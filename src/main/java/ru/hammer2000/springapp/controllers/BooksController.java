package ru.hammer2000.springapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.hammer2000.springapp.model.Book;
import ru.hammer2000.springapp.model.Person;
import ru.hammer2000.springapp.services.BooksService;
import ru.hammer2000.springapp.services.PeopleService;
import java.util.List;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {

        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(@RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear,
                        @RequestParam(value = "sort_by_title", required = false) boolean sortByTitle,
                        Model model) {

        if (page != null && booksPerPage != null) {
            if (sortByTitle) {
                model.addAttribute("books", booksService.findAll(page, booksPerPage,
                        "title"));
            } else if (sortByYear) {
                model.addAttribute("books", booksService.findAll(page, booksPerPage,
                        "year"));
            } else {
                model.addAttribute("books", booksService.findAll(page, booksPerPage));
            }
        } else {
            if (sortByTitle) {
                model.addAttribute("books", booksService.findAll("title"));
            } else if (sortByYear) {
                model.addAttribute("books", booksService.findAll("year"));
            } else {
                model.addAttribute("books", booksService.findAll(0, 3));
            }
        }
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findOne(id));

        if (booksService.findOne(id).getOwner() != null) {
            model.addAttribute("owner", booksService.findOne(id).getOwner());
        } else {
            model.addAttribute("people", peopleService.findAll());
            model.addAttribute("person", new Person());
        }

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";

    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String addBookToPerson(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        booksService.addBookToPerson(id, person);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/setFree")
    public String removeBookFromPerson(@PathVariable("id") int id) {
        booksService.removeBookFromPerson(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "book_for_search", required = false) String bookForSearch,
                         Model model) {

        if (bookForSearch != null && !bookForSearch.equals("")) {
            List<Book> books = booksService.searchBook(bookForSearch);
            if (!books.isEmpty()) {
                model.addAttribute("books", books);
            } else {
                model.addAttribute("notBooks", "");
            }
        }


        return "books/search";
    }

}