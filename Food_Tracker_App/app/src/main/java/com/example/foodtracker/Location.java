package com.example.foodtracker;

import android.os.Build;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/*
Location enums pantry, fridge and freezer as String values
unmodifiable List of Food Strings is returned upon calling getLocationStrings()
 */
public enum Location {

    LOCATION1("pantry"),
    LOCATION2("fridge"),
    LOCATION3("freezer");

    private static final List<String> LOCATIONSTRINGS;
    private static final List<Location> LOCATIONS;
    private final String loc;


    static {
        //List of Location String values
        LOCATIONSTRINGS = new ArrayList<>();
        for (Location location : Location.values()) {
            LOCATIONSTRINGS.add(location.loc);
        }

        LOCATIONS = new ArrayList<Location>(EnumSet.allOf(Location.class));
    }

    Location(String value) {
        this.loc = value;
    }

    /**
     *
     * @return list contains Location String values
     */
    public static List<String> getLocationStrings() {
        return Collections.unmodifiableList(LOCATIONSTRINGS);
    }

    /**
     *
     * @return list contains Locations
     */
    public static List<Location> getLocations(){
        return Collections.unmodifiableList(LOCATIONS);
    }

    /**
     *
     * @param location
     * @return  String value of location
     */
    public static String getValue(Location location){
        return location.loc;
    }

    /**
     *checks if location exists
     * @param toBeChecked
     * @return true if exists
     */
    public static boolean checkLocation(String toBeChecked){
        for (String location : LOCATIONSTRINGS){
            if (toBeChecked.equals(location))
                return true;
        }

        return false;
    }

    /**
     * delete location
     */
    public static void deleteLocation(String toBeDeleted) throws IllegalArgumentException{
        if (checkLocation(toBeDeleted))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                LOCATIONS.removeIf(p -> Location.getValue(p).equals(toBeDeleted));
                LOCATIONSTRINGS.removeIf(q -> q.equals(toBeDeleted));
            }
            else{
                throw new IllegalArgumentException("Deletion not successful");
            }
    }
}