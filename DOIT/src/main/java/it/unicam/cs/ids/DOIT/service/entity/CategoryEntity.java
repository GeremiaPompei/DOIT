package it.unicam.cs.ids.DOIT.service.entity;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class CategoryEntity implements ResourceEntity<ICategory> {
    @Transient
    private ServicesHandler servicesHandler = ServicesHandler.getInstance();
    @Id
    private String name;
    private String description;

    @Override
    public void fromObject(ICategory category) {
        name = category.getName();
        description = category.getDescription();
    }

    @Override
    public ICategory toObject() {
        return servicesHandler.getFactoryModel().createCategory(this.name, this.description);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
