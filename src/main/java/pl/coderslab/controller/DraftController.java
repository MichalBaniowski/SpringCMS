package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.entity.Article;
import pl.coderslab.jpaRepository.ArticleRepository;
import pl.coderslab.repository.Dao;
import pl.coderslab.repository.DaoType;
import pl.coderslab.validation.CompleteArticleGroup;
import pl.coderslab.validation.DraftArticleGroup;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DraftController {

    private Dao<Article, Long> articleDao;
    private ArticleRepository articleRepository;

    @Autowired
    public DraftController(@DaoType(type = DaoType.Type.ARTICLE) Dao<Article, Long> articleDao, ArticleRepository articleRepository) {
        this.articleDao = articleDao;
        this.articleRepository = articleRepository;
    }

    private List<Article> getShorterContentArticle(List<Article> articles, int limit) {
        return articles.stream()
                .map(article -> {
                    if(article.getContent().length() > limit){
                        article.setContent(article.getContent().substring(0, limit));
                    }
                    return article;
                })
                .collect(Collectors.toList());
    }

    private List<Article> filterDraftArticle(List<Article> articles) {
        return articles.stream().filter(article -> article.isDraft()).collect(Collectors.toList());
    }

    @RequestMapping("/drafts")
    public String allDrafts(Model model) {
//        List<Article> articles = filterDraftArticle(getShorterContentArticle(articleDao.all(), 200)); //dao
        List<Article> articles = getShorterContentArticle(articleRepository.findAllByDraft(true), 200);
        model.addAttribute("articles", articles);
        model.addAttribute("headerTitle", "Wszystkie szkice artykułów");
        return "articles";
    }

    @GetMapping("/draft/add")
    public String addDraft(Model model){
        Article article = new Article();
        model.addAttribute("article", article);
        return "form-draft";
    }

    @PostMapping("/draft/add")
    public String addDraftProcess(@Validated(DraftArticleGroup.class) Article article, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "form-draft";
        }
//        String actionResult = articleDao.create(article) == null ? "nie udało się stworzyć szkicu" : "Szkic utworzony"; //dao
        String actionResult = articleRepository.save(article) == null ? "nie udało się stworzyć szkicu" : "Szkic utworzony";
        model.addAttribute("actionResult", actionResult);
        return "result-prompt";
    }

    @GetMapping("/draft/edit/{id}")
    public String editDraft(Model model, @PathVariable long id) {
//        Article article = articleDao.retrieve(id);//dao
        Article article = articleRepository.findById(id).get();
        model.addAttribute("article", article);
        return "form-draft";
    }

    @PostMapping("/draft/edit/{id}")
    public String editDraftProcess(@Validated(DraftArticleGroup.class) Article article, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "form-draft";
        }
//        articleDao.update(article);//dao
        articleRepository.save(article);
        model.addAttribute("actionResult", "edycja szkicu zakończona");
        return "result-prompt";
    }
}
