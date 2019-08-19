package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.entity.Article;
import pl.coderslab.repository.ArticleDao;
import pl.coderslab.repository.DaoType;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomePageController {

    private ArticleDao articleDao;

    @Autowired
    public HomePageController(@DaoType(type = DaoType.Type.ARTICLE) ArticleDao articleDao){
        this.articleDao = articleDao;
    }

    @RequestMapping("/")
    String lastArticles(Model model){
        List<Article> latest5 = articleDao.getLatest5().stream()
                .map(article -> {
                    article.setContent(article.getContent().substring(0, 200));
                    return article;})
                .collect(Collectors.toList());

        model.addAttribute("articles", latest5);
        model.addAttribute("headerTitle", "Ostatnio dodane artykuły");
        return "articles";
    }
}
