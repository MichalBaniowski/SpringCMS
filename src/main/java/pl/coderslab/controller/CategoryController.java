package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.entity.Category;
import pl.coderslab.jpaRepository.ArticleRepository;
import pl.coderslab.jpaRepository.CategoryRepository;
import pl.coderslab.repository.Dao;
import pl.coderslab.repository.DaoType;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {

    private Dao<Category, Long> categoryDao;
    private CategoryRepository categoryRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public CategoryController(@DaoType(type = DaoType.Type.CATEGORY) Dao<Category, Long> categoryDao,
                              CategoryRepository categoryRepository,
                              ArticleRepository articleRepository) {
        this.categoryDao = categoryDao;
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    @RequestMapping("/categories")
    public String allCategories(Model model) {
//        List<Category> categories = categoryDao.all();// dao
        List<Category> categories = categoryRepository.findAll();// repository
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/category/add")
    public String addCategory(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "form-category";
    }

    @PostMapping("/category/add")
    public String addCategoryProcess(@Valid Category category, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "form-category";
        }
//        String actionResult = categoryDao.create(category) == null ? "nie udało się stworzyć kategorii" : "kategoria utworzona"; // dao
        String actionResult = categoryRepository.save(category) == null ? "nie udało się stworzyć kategorii" : "kategoria utworzona"; //repository
        model.addAttribute("actionResult", actionResult);
        return "result-prompt";
    }

    @GetMapping("/category/edit/{id}")
    public String editCategory(@PathVariable long id, Model model) {
//        Category category = categoryDao.retrieve(id);
        Category category = categoryRepository.findById(id).get();
        model.addAttribute("category", category);
        return "form-category";
    }

    @PostMapping("/category/edit/{id}")
    public String editCategoryProcess(@Valid Category category, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "form-category";
        }
//        categoryDao.update(category);//dao
        categoryRepository.save(category);
        model.addAttribute("actionResult", "edycja kategorii zakończona");
        return "result-prompt";
    }

    @RequestMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable long id, Model model) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        String deleteResult;
        if(categoryOptional.isPresent()){
            articleRepository.findByCategories(categoryOptional.get()).stream()
                    .forEach(article -> {
                        if(article.getCategories().size() == 1){
                            articleRepository.delete(article);
                        }else {
                            article.getCategories().remove(categoryOptional.get());
                            articleRepository.save(article);
                        }
                    });
            categoryRepository.delete(categoryOptional.get());
            deleteResult = "usunięto kategorię oraz artykuły, które były tylko w tej kategorii";
        }else{
            deleteResult = "nie odnaleziono kategorii do usunięcia";
        }
        model.addAttribute("actionResult", deleteResult);
        return "result-prompt";
    }
}
