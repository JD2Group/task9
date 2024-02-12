package org.example.lesson9.utils_src;

import java.util.List;

public class MockConstants {

    public static final int EXPECTED_SIZE = 5;
    public static final String JSON_FILE_NOT_EXIST = "src\\test\\resources\\empty.json";

    public static final String DOORS_IN_FILE_PATH = "src\\test\\resources\\doors.json";
    public static final String DOORS_OUT_FILE_PATH = "src\\test\\resources\\doorsOut.json";
    public static final double FROM_SIZE = 3500D;
    public static final double TO_SIZE = 3500D;
    public static final double NULL_SIZE = 0;
    public static final List<Double> DOORS_SIZE = List.of(210.5, 1300.0, 950.0, 207.1, 1200.0);
    public static final List<String> DOORS_TYPE = List.of("одностворчатые", "двустворчатые", "полуторные", "другой тип", "test");
    public static final String DELETE_ALL_DOORS_QUERY = "delete from doors";

    public static final String HOUSES_IN_FILE_PATH = "src\\test\\resources\\houses.json";
    public static final List<Double> HOUSES_SIZE = List.of(150D, 62D, 85D, 120D, 54D);
    public static final List<String> HOUSES_COLOR = List.of("белый", "черный", "зеленый", "черно-белый", "красный");
    public static final List<Integer> HOUSES_ROOM = List.of(5, 3, 4, 7, 3);
    public static final String NULL_COLOR = "";
    public static final String COLOR_TEST = "ColorTest";
    public static final String DELETE_ALL_HOUSES_QUERY = "delete from houses";
    private MockConstants() {
    }
}
