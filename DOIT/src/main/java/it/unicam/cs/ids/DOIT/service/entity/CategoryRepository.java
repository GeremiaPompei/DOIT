package it.unicam.cs.ids.DOIT.service.entity;

import it.unicam.cs.ids.DOIT.category.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {

}
