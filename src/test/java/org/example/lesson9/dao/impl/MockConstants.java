package org.example.lesson9.dao.impl;

import java.util.List;

public class MockConstants {

    public static final double FROM_SIZE = 900;
    public static final double TO_SIZE = 1300;
    public static final double NULL_SIZE = 0;
    public static final List<Double> DOORS_SIZE = List.of(210.5, 1300.0, 950.0, 207.1, 1200.0);
    public static final List<String> DOORS_TYPE = List.of("одностворчатые", "двустворчатые", "полуторные", "другой тип", "test");

    public static final List<Double> HOUSES_SIZE = List.of(150D, 62D, 85D, 120D, 54D);
    public static final List<String> HOUSES_COLOR = List.of("белый", "черный", "зеленый", "черно-белый", "красный");
    public static final List<Integer> HOUSES_ROOM = List.of(5, 3, 4, 7, 3);
    public static final String NULL_COLOR = "";

    private MockConstants() {
    }
}
