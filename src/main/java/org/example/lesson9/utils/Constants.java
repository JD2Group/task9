package org.example.lesson9.utils;

import java.util.Random;

public final class Constants {

    //dao.DoorDAOImp;
    public static final String GET_BY_SIZE_QUERY = "select * from doors where size between :fromSize and :toSize";
    public static final String FROM_SIZE_VALUE = "fromSize";
    public static final String TO_SIZE_VALUE = "toSize";

    //dao.HouseDAOImpl
    public static final String GET_BY_COLOR_QUERY = "select * from houses where color = :color";
    public static final String COLOR_VALUE = "color";

    //DemoApp
    public static final String HOUSES_IN_FILE_PATH = "src\\main\\resources\\houses.json";
    public static final String HOUSES_OUT_FILE_PATH = "src\\main\\resources\\housesWithId.json";
    public static final String DOORS_IN_FILE_PATH = "src\\main\\resources\\doors.json";
    public static final String DOORS_OUT_FILE_PATH = "src\\main\\resources\\doorsWithId.json";
    public static final String DOOR_METHOD_NAME = "getBySize";
    public static final String HOUSE_METHOD_NAME = "getByColor";
    public static final double DOOR_FROM_SIZE = 900D;
    public static final double DOOR_TO_SIZE = 1300D;
    public static final String HOUSE_COLOR = "черный";

    //utils.HibernateUtil
    public static final String PERSISTENCE_UNIT_NAME = "lesson9";


    //All
    public static final Random RANDOM = new Random();

    private Constants() {
    }
}
