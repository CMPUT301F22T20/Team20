package com.example.foodtracker.model;

import java.util.Map;

/**
 * Objects that implement the {@link Documentable} interface are able to be
 * represented as {@link com.google.firebase.firestore.FirebaseFirestore} instances
 */
public interface Documentable {

    /**
     * The name of the collection that stores objects of this type
     */
    String getCollectionName();

    /**
     * The key for the objects in the collection
     */
    String getKey();

    /**
     * The data in this object that will be saved in the collection
     */
    Map<String, Object> getData();
}
