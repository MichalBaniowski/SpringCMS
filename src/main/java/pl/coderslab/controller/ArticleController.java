package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.Article;
import pl.coderslab.entity.Author;
import pl.coderslab.entity.Category;
import pl.coderslab.jpaRepository.ArticleRepository;
import pl.coderslab.jpaRepository.AuthorRepository;
import pl.coderslab.jpaRepository.CategoryRepository;
import pl.coderslab.repository.ArticleDao;
import pl.coderslab.repository.Dao;
import pl.coderslab.repository.DaoType;
import pl.coderslab.validation.CompleteArticleGroup;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ArticleController {

    private ArticleDao articleDao;
    private Dao<Category, Long> categoryDao;
    private Dao<Author, Long> authorDao;

    private ArticleRepository articleRepository;
    private CategoryRepository categoryRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public ArticleController(@DaoType(type = DaoType.Type.ARTICLE) ArticleDao articleDao,
                             @DaoType(type = DaoType.Type.CATEGORY) Dao<Category, Long> categoryDao,
                             @DaoType(type = DaoType.Type.AUTHOR) Dao<Author, Long> authorDao,
                             AuthorRepository authorRepository,
                             ArticleRepository articleRepository,
                             CategoryRepository categoryRepository) {
        this.articleDao = articleDao;
        this.categoryDao = categoryDao;
        this.authorDao = authorDao;

        this.authorRepository = authorRepository;
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryRepository.findAll();
        //categoryDao.all();//dao
    }

    @ModelAttribute("authors")
    public List<Author> authors() {
        return authorRepository.findAll();
                //authorDao.all();//dao
    }

    private List<Article> getShorterContentArticle(List<Article> articles, int limit) {
        return articles.stream()
                .map(article -> {
                    if (article.getContent().length() > limit) {
                        article.setContent(article.getContent().substring(0, limit));
                    }
                    return article;
                })
                .collect(Collectors.toList());
    }

    private List<Article> filterCompleteArticle(List<Article> articles) {
        return articles.stream().filter(article -> !article.isDraft()).collect(Collectors.toList());
    }

    @RequestMapping("/article/form")
    public String chooseForm() {
        return "choose-article-form";
    }

    @RequestMapping("/articles")
    public String allArticles(Model model) {
//        List<Article> articles = filterCompleteArticle(getShorterContentArticle(articleDao.all(), 200));//dao
        List<Article> articles = getShorterContentArticle(articleRepository.findAllByDraft(false), 200);
        model.addAttribute("articles", articles);
        model.addAttribute("headerTitle", "Wszystkie artykuły");
        return "articles";
    }

    @RequestMapping("/articles/by-category/{id}")
    public String articlesByCategory(@PathVariable long id, Model model) {
//        Category category = categoryDao.retrieve(id);//dao
        Category category = categoryRepository.findById(id).get();
//        List<Article> articles = filterCompleteArticle(getShorterContentArticle(articleDao.getAllByCategory(category), 200)); // dao
        List<Article> articles = getShorterContentArticle(articleRepository.findByCategories(category), 200);
        model.addAttribute("articles", articles);
        model.addAttribute("headerTitle", String.format("Artykuły z kategorii: %s", category.getName()));
        return "articles";
    }

    @GetMapping("/article/add")
    public String addArticle(Model model) {
        Article article = new Article();
        model.addAttribute("article", article);
        return "form-article";
    }

    @PostMapping("/article/add")
    public String addArticleProcess(@Validated(CompleteArticleGroup.class) Article article, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "form-article";
        }
//        String actionResult = articleDao.create(article) == null ? "nie udało się stworzyć artykułu" : "Artykuł utworzony"; // dao
        String actionResult = articleRepository.save(article) == null ? "nie udało się stworzyć artykułu" : "Artykuł utworzony";
        model.addAttribute("actionResult", actionResult);
        return "result-prompt";
    }

    @GetMapping("/article/edit/{id}")
    public String editArticle(Model model, @PathVariable long id) {
//        Article article = articleDao.retrieve(id); //dao
        Article article = articleRepository.findById(id).get();
        model.addAttribute("article", article);
        return "form-article";
    }

    @PostMapping("/article/edit/{id}")
    public String editArticleProcess(@Validated(CompleteArticleGroup.class) Article article, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "form-article";
        }
//        articleDao.update(article);//dao
        articleRepository.save(article);
        model.addAttribute("actionResult", "edycja artukułu zakończona");
        return "result-prompt";
    }

    @RequestMapping("/articles/by-author/{id}")
    public String articlesByAuthor(@PathVariable long id, Model model) {
//        Author author = authorDao.retrieve(id); //dao
        Author author = authorRepository.findById(id).get();
//        List<Article> articles = filterCompleteArticle(getShorterContentArticle(articleDao.getAllByAuthor(author), 200));//dao
        List<Article> articles = getShorterContentArticle(articleRepository.findByAuthor(author), 200);
        model.addAttribute("articles", articles);
        model.addAttribute("headerTitle", String.format("Artykuły autora: %s %s", author.getFirstName(), author.getLastName()));
        return "articles";
    }

    @RequestMapping("/articles/details/{id}")
    public String articleDetail(@PathVariable long id, Model model) {
//        Article article = articleDao.retrieve(id);//dao
        Article article = articleRepository.findById(id).get();
        model.addAttribute("article", article);
        return "article-detail";
    }

    @RequestMapping("/article/delete/{id}")
    public String deleteArticle(@PathVariable long id, Model model) {
//        String deleteResult = articleDao.delete(id) ? "usunięto artykuł" : "nie udało się usunąć artykułu"; //dao
//        model.addAttribute("actionResult", deleteResult);// dao
        articleRepository.deleteById(id);
        model.addAttribute("actionResult", "artykuł usuniety");
        return "result-prompt";
    }

}
