package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.entity.Author;
import pl.coderslab.jpaRepository.ArticleRepository;
import pl.coderslab.jpaRepository.AuthorRepository;
import pl.coderslab.repository.Dao;
import pl.coderslab.repository.DaoType;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthorController {

    private Dao<Author, Long> authorDao;
    private AuthorRepository authorRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public AuthorController(@DaoType(type = DaoType.Type.AUTHOR)Dao<Author, Long> authorDao,
                            AuthorRepository authorRepository,
                            ArticleRepository articleRepository) {
        this.authorDao = authorDao;
        this.authorRepository = authorRepository;
        this.articleRepository = articleRepository;
    }

    @RequestMapping("/authors")
    public String authors(Model model) {
//        List<Author> authors = authorDao.all(); //dao
        List<Author> authors = authorRepository.findAll(); //repository
        model.addAttribute("authors", authors);
        return "authors";
    }

    @GetMapping("/author/add")
    public String addAuthor(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "form-author";
    }

    @PostMapping("/author/add")
    public String addAuthorProcess(@Valid Author author, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "form-author";
        }
//        String actionResult = authorDao.create(author) == null ? "nie udało się dodać autora" : "autor dodany"; //dao
        String actionResult = authorRepository.save(author) == null ? "nie udało się dodać autora" : "autor dodany"; //repository
        model.addAttribute("actionResult", actionResult);
        return "result-prompt";
    }

    @GetMapping("/author/edit/{id}")
    public String editAuthor(@PathVariable long id, Model model) {
//        Author author = authorDao.retrieve(id); //dao
        Optional<Author> authorOptional = authorRepository.findById(id); // repository
        if(authorOptional.isPresent()){//repository
            model.addAttribute("author", authorOptional.get());//repository
            return "form-author";//repository
        }
        model.addAttribute("actionResult", "brak takiego autora");//repository
        return "result-prompt";//repository
//        model.addAttribute("author", authorOptional.get()); //dao
//        return "form-author";//dao
    }

    @PostMapping("/author/edit/{id}")
    public String editAuthor(@Valid Author author, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "form-author";
        }
//        authorDao.update(author);// dao
        authorRepository.save(author);//repository
        model.addAttribute("actionResult", "edycja autora zakończona");
        return "result-prompt";
    }

    @RequestMapping("/author/delete/{id}")
    public String deleteAuthor(@PathVariable long id, Model model) {
        Author author = authorRepository.findById(id).get();
        articleRepository.deleteAllByAuthor(author);
        authorRepository.delete(author);

        model.addAttribute("actionResult", "autor i jego artykuły usunięte");
        return "result-prompt";
    }
}
