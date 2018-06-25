package uk.ac.uea.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * Class designed to link generic objects in a data structure in a way that makes it extensible and usable for any app.
 * Allows a new object to be added, size allowing, as well as previous specific objects to be deleted by reference.
 * Acts as a first-in, last-out data structure.
 * @param <T> Generic object
 */
public class Pool<T> {
    /**
     * Abstract factory, allows object-specific insantiations.
     * @param <T>
     */
    public interface PoolObjectFactory<T> {
        public T createObject();
    }
    /**List of objects */
    private final List<T> freeObjects;
    /**Factory instantiation as a field */
    private final PoolObjectFactory<T> factory;
    /**Max size of the pool */
    private final int maxSize;

    /**
     * Constructor that sets up the factory, using an arraylist structure as a basis for the free object list
     * @param factory
     * @param maxSize
     */
    public Pool(PoolObjectFactory<T> factory, int maxSize) {
        this.factory = factory;
        this.maxSize = maxSize;
        this.freeObjects = new ArrayList<T>(maxSize);
    }

    /**
     * Adds a new object to the pool, if the pool is full, the first object in the pool is removed and the new one is added.
     * @return the object.
     */
    public T newObject() {
        T object = null;

        if (freeObjects.size() == 0)
            object = factory.createObject();
        else
            object = freeObjects.remove(freeObjects.size() - 1);

        return object;
    }

    /**
     * Adds the specifide object to the list of free objects, assuming the size does not exceed maximum.
     * @param object specific object to free
     */
    public void free(T object) {
        if (freeObjects.size() < maxSize)
            freeObjects.add(object);
    }
}
