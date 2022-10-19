package com.example.foodtracker.utils;

import com.example.foodtracker.models.Documentable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GenericCollection<T extends Documentable> {

    private final Class<T> typeParameterClass;
    private T instance;
    private final CollectionReference collection = FirebaseFirestore.getInstance().collection(instance.getCollectionName());

    public GenericCollection(Class<T> typeParameterClass, T instance) {
        this.typeParameterClass = typeParameterClass;
        this.instance = instance;
    }

    /**
     * Adds all task results to a passed list
     *
     * @param list to add all results to
     */
    public void getAll(List<T> list) {
        collection.get().addOnCompleteListener(getObjectsFromQuery(list));
    }

    /**
     * Adds all task results to a passed list
     *
     * @param list           to add all results to
     * @param sortedByColumn column to sort the results by
     * @param direction      to sort in
     */
    public void getAll(List<T> list, String sortedByColumn, @Nullable Query.Direction direction) {
        Query.Direction sortingDirection = direction != null ? direction : Query.Direction.ASCENDING;
        collection.orderBy(sortedByColumn, sortingDirection).get().addOnCompleteListener(getObjectsFromQuery(list));
    }

    /**
     * Updates or creates the document specified by this documents key, with this documents data
     *
     * @param document to create or update
     */
    public void createOrUpdate(T document) {
        collection.document(document.getKey()).set(document.getData());
    }

    /**
     * Deletes the document specified by this documents key, with this documents data
     */
    public void delete(T document) {
        collection.document(document.getKey()).delete();
    }

    private OnCompleteListener<QuerySnapshot> getObjectsFromQuery(List<T> list) {
        return queryResult -> {
            if (queryResult.isSuccessful()) {
                list.addAll(queryResult.getResult().toObjects(typeParameterClass));
            }
        };
    }
}
