package pl.coderslab.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.coderslab.entity.Category;
import pl.coderslab.repository.Dao;
import pl.coderslab.repository.DaoType;

public class CategoryConverter implements Converter<String, Category> {

    @Autowired
    @DaoType(type = DaoType.Type.CATEGORY)
    Dao<Category, Long> categoryDao;

    @Override
    public Category convert(String s) {
        Category category = categoryDao.retrieve(Long.parseLong(s));
        return category;
    }
}
