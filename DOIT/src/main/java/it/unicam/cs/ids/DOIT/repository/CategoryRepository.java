package it.unicam.cs.ids.DOIT.repository;

import it.unicam.cs.ids.DOIT.model.category.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {

}
