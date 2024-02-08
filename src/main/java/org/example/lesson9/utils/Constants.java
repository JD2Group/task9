package org.example.lesson9.utils;

import java.util.Random;

public final class Constants {

    //dao.DAOImpl
    public static final String OBJECT_IS_NULL = "Object should not be null";

    //HouseApp
    public static final String HOUSES_IN_FILE_PATH = "src\\main\\resources\\houses.json";
    public static final String HOUSES_OUT_FILE_PATH = "src\\main\\resources\\housesWithId.json";

    //DoorApp
    public static final String DOORS_IN_FILE_PATH = "src\\main\\resources\\doors.json";
    public static final String DOORS_OUT_FILE_PATH = "src\\main\\resources\\doorsWithId.json";

    //All
    public static final Random RANDOM = new Random();

    private Constants() {
    }
}
