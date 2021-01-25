package it.unicam.cs.ids.DOIT.view.controllerspringboot;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.controller.SearchMVC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    private SearchMVC search;

    @GetMapping(value = "/categories")
    public List<Category> getCategories() {
        return List.copyOf(search.getAllCategories());
    }
}
