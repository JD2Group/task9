package org.example.lesson9.utils;

import org.example.lesson9.dto.DoorDTO;
import org.example.lesson9.dto.HouseDTO;
import org.example.lesson9.utils_src.MockUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.example.lesson9.utils_src.MockConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(4)
class GsonManagerTest {
    private static final List<DoorDTO> doorDTOS = new ArrayList<>();

    @BeforeAll
    public static void createDoorList() {
        doorDTOS.add(MockUtils.buildDoor(DOORS_SIZE.get(0), DOORS_TYPE.get(0)));
        doorDTOS.add(MockUtils.buildDoor(DOORS_SIZE.get(1), DOORS_TYPE.get(1)));
        doorDTOS.add(MockUtils.buildDoor(DOORS_SIZE.get(2), DOORS_TYPE.get(2)));
        doorDTOS.add(MockUtils.buildDoor(DOORS_SIZE.get(3), DOORS_TYPE.get(3)));
        doorDTOS.add(MockUtils.buildDoor(DOORS_SIZE.get(4), DOORS_TYPE.get(4)));
    }

    @Test
    public void writeDTOListTest() {
        try {
            GsonManager.writeDTOList(DOORS_OUT_FILE_PATH, doorDTOS);
            assertTrue(Files.exists(Paths.get(DOORS_OUT_FILE_PATH)));

            assertThrows(NullPointerException.class,
                    () -> GsonManager.writeDTOList(null, doorDTOS));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    @AfterAll
    public static void deleteFile() {
        try {
            Files.delete(Path.of(DOORS_OUT_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}