package it.unicam.cs.ids.DOIT.service;

public class ServicesHandler {

    private static ServicesHandler servicesHandler;

    public static ServicesHandler getInstance() {
        if(servicesHandler == null)
            servicesHandler = new ServicesHandler();
        return servicesHandler;
    }

    private ServicesHandler() {
        this.resourceHandler = new ResourceHandler();
        this.factoryModel = new FactoryModel(resourceHandler);
    }

    private IFactoryModel factoryModel;
    private IResourceHandler resourceHandler;

    public IFactoryModel getFactoryModel() {
        return factoryModel;
    }

    public IResourceHandler getResourceHandler() {
        return resourceHandler;
    }
}
