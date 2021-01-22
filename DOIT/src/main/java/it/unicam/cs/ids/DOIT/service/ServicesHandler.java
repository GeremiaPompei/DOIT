package it.unicam.cs.ids.DOIT.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ServicesHandler {

    private IFactoryModel factoryModel;
    private IResourceHandler resourceHandler;

    private ServicesHandler(
            @Qualifier("resourceHandler") IResourceHandler resourceHandler,
            @Qualifier("factoryModel") IFactoryModel factoryModel) {
        this.resourceHandler = resourceHandler;
        this.factoryModel = factoryModel;
    }

    public IFactoryModel getFactoryModel() {
        return factoryModel;
    }

    public IResourceHandler getResourceHandler() {
        return resourceHandler;
    }
}
