package com.example.foodtracker.models;

import java.util.Map;

public interface Documentable {

    String getCollectionName();

    String getKey();

    Map<String, Object> getData();
}
