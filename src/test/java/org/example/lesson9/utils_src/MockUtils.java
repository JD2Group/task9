package org.example.lesson9.utils_src;

import org.example.lesson9.dto.DoorDTO;
import org.example.lesson9.dto.HouseDTO;

public class MockUtils {
    private MockUtils() {
    }

    public static DoorDTO buildDoor(double size, String type) {
        return DoorDTO.builder()
                .size(size)
                .type(type)
                .build();
    }

    public static HouseDTO buildHouse(double size, String color, int rooms) {
        return HouseDTO.builder()
                .size(size)
                .color(color)
                .roomCount(rooms)
                .build();
    }
}
