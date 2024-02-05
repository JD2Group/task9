package org.example.lesson9.utils;

import org.example.lesson9.dto.DoorDTO;
import org.example.lesson9.dto.HouseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.stream.Stream;

import static org.example.lesson9.utils_src.MockConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class GsonManagerTest {

    @Test
    public void writeDTOListTest(String filePath, Object dtoList) {

    }

    @ParameterizedTest()
    @MethodSource("readCases")
    public <T> void readDTOListTest(String filePath, Class<T> dtoClass, int expectedSize) {
        try {
            if ((DOORS_IN_FILE_PATH.equals(filePath)
                    || HOUSES_IN_FILE_PATH.equals(filePath))
                    && dtoClass != null) {
                List<T> list = GsonManager.readDTOList(filePath, dtoClass);
                assertEquals(expectedSize, list.size(), "Expected list size: " + expectedSize + ", actual: " + list.size());
            } else if (JSON_FILE_NOT_EXIST.equals(filePath) && dtoClass != null) {
                assertThrows(NoSuchFileException.class, () -> GsonManager.readDTOList(filePath, dtoClass));
            } else if (filePath == null && dtoClass != null) {
                assertThrows(NullPointerException.class, () -> GsonManager.readDTOList(filePath, dtoClass));
            } else if (filePath != null && dtoClass == null) {
                assertThrows(IllegalArgumentException.class, () -> GsonManager.readDTOList(filePath, dtoClass));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Stream<Arguments> readCases() {
        return Stream.of(
                Arguments.of(DOORS_IN_FILE_PATH, DoorDTO.class, EXPECTED_SIZE),
                Arguments.of(HOUSES_IN_FILE_PATH, HouseDTO.class, EXPECTED_SIZE),
                Arguments.of(JSON_FILE_NOT_EXIST, HouseDTO.class, EXPECTED_SIZE),
                Arguments.of(null, HouseDTO.class, EXPECTED_SIZE),
                Arguments.of(null, null, EXPECTED_SIZE)
        );
    }

}