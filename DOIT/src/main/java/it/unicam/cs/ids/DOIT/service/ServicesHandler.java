package it.unicam.cs.ids.DOIT.service;

import org.springframework.beans.factory.annotation.Autowired;

public class ServicesHandler {

    private static ServicesHandler servicesHandler;

    public static ServicesHandler getInstance() {
        if(servicesHandler == null)
            servicesHandler = new ServicesHandler();
        return servicesHandler;
    }

    private IFactoryModel factoryModel;
    private IResourceHandler resourceHandler;
    private IIdGenerator idGenerator;

    private ServicesHandler() {
        this.resourceHandler = new ResourceHandler();
        this.factoryModel = new FactoryModel(resourceHandler);
        this.idGenerator = new IdGenerator();
    }

    public IFactoryModel getFactoryModel() {
        return factoryModel;
    }

    public IResourceHandler getResourceHandler() {
        return resourceHandler;
    }

    public IIdGenerator getIdGenerator() {
        return idGenerator;
    }
}
