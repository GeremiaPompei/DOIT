package it.unicam.cs.ids.DOIT.service;

public class ServicesHandler {
    private static ServicesHandler servicesHandler;

    public static ServicesHandler getInstance() {
        if (servicesHandler == null)
            servicesHandler = new ServicesHandler();
        return servicesHandler;
    }

    private IFactoryModel factoryModel;
    private IResourceHandler resourceHandler;

    private ServicesHandler() {
        this.resourceHandler = new ResourceHandler();
        this.factoryModel = new FactoryModel(resourceHandler);
        this.factoryModel.createCategory("sport", "description sport");
        this.factoryModel.createCategory("informatica", "description informatica");
        this.factoryModel.createCategory("cucina", "description cucina");
        this.factoryModel.createProjectState(0L, "INITIAL", "description");
        this.factoryModel.createProjectState(1L, "IN PROGRESS", "description");
        this.factoryModel.createProjectState(2L, "TERMINAL", "description");
    }

    public IFactoryModel getFactoryModel() {
        return factoryModel;
    }

    public IResourceHandler getResourceHandler() {
        return resourceHandler;
    }
}
