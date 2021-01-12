package it.unicam.cs.ids.DOIT.storage;

public abstract class FactoryStorage {
    private static ResourceHandler resourceHandler;

    public static IResourceHandler getResouceHandler() {
        if (resourceHandler == null)
            resourceHandler = new ResourceHandler();
        return resourceHandler;
    }
}
