package it.unicam.cs.ids.DOIT.service;

public abstract class ServicesHandler {
    private static IFactoryModel factoryModel;
    private static IResourceHandler resourceHandler;

    public static IFactoryModel getFactoryModel() {
        if (factoryModel == null) {
            factoryModel = new FactoryModel();
        }
        return factoryModel;
    }

    public static IResourceHandler getResourceHandler() {
        if (resourceHandler == null)
            resourceHandler = new ResourceHandler();
        return resourceHandler;
    }
}
