package it.unicam.cs.ids.DOIT.view.controllerspringboot;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.controller.ISearch;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.service.entity.CategoryEntity;
import it.unicam.cs.ids.DOIT.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private ISearch search;

    @Autowired
    public SearchController(@Qualifier("search") ISearch search) {
        this.search = search;
    }

    @GetMapping(value = "/categories")
    public List<CategoryEntity> getCategories() {
        return search.getAllCategories().stream().map(t -> {
            CategoryEntity ce = new CategoryEntity();
            ce.fromObject(t);
            return ce;
        }).collect(Collectors.toList());
    }
}
