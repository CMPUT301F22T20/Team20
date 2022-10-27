package com.example.foodtracker.utils;

import com.example.foodtracker.model.Documentable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Collection<T extends Documentable> {

    private final Class<T> typeParameterClass;
    private final CollectionReference collection;

    public Collection(Class<T> typeParameterClass, T instance) {
        this.typeParameterClass = typeParameterClass;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        collection = db.collection(instance.getCollectionName());
    }

    /**
     * Adds all task results to a passed list
     *
     * @param onComplete callback on complete
     */
    public void getAll(ListTask<T> onComplete) {
        collection.get().addOnCompleteListener(getObjectsFromQuery(onComplete));
    }

    /**
     * Adds all task results to a passed list
     *
     * @param onComplete     callback on complete
     * @param sortedByColumn column to sort the results by
     * @param direction      to sort in
     */
    public void getAll(ListTask<T> onComplete, String sortedByColumn, @Nullable Query.Direction direction) {
        Query.Direction sortingDirection = direction != null ? direction : Query.Direction.ASCENDING;
        collection.orderBy(sortedByColumn, sortingDirection).get().addOnCompleteListener(getObjectsFromQuery(onComplete));
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
     * Updates or creates all documents in the passed list, with the data from these documents
     *
     * @param documents to create or update
     */
    public void createOrUpdateMultiple(List<T> documents) {
        for (T document : documents) {
            createOrUpdate(document);
        }
    }

    /**
     * Deletes the document specified by this documents key, with this documents data
     */
    public void delete(T document) {
        collection.document(document.getKey()).delete();
    }

    private OnCompleteListener<QuerySnapshot> getObjectsFromQuery(ListTask<T> onTaskComplete) {
        return queryResult -> {
            List<T> list = new ArrayList<>(queryResult.getResult().toObjects(typeParameterClass));
            onTaskComplete.onComplete(list);
        };
    }

    public interface ListTask<T> {
        void onComplete(List<T> list);
    }

    public interface DocumentTask<T> {
        void onComplete(T document);
    }
}

