package org.example.lesson9.dao.impl;


import org.example.lesson9.dao.DoorDAO;
import org.example.lesson9.dto.DoorDTO;
import org.example.lesson9.utils_src.MockUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.example.lesson9.utils_src.MockConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(1)
class DoorDAOImplTest {
    private static final DoorDAO DOOR_DAO = new DoorDAOImpl();
    private static final List<DoorDTO> doorDTOS = new ArrayList<>();

    @BeforeAll
    public static void createDoorList() {
        doorDTOS.add(MockUtils.buildDoor(DOORS_SIZE.get(0), DOORS_TYPE.get(0)));
        doorDTOS.add(MockUtils.buildDoor(DOORS_SIZE.get(1), DOORS_TYPE.get(1)));
        doorDTOS.add(MockUtils.buildDoor(DOORS_SIZE.get(2), DOORS_TYPE.get(2)));
        doorDTOS.add(MockUtils.buildDoor(DOORS_SIZE.get(3), DOORS_TYPE.get(3)));
        doorDTOS.add(MockUtils.buildDoor(DOORS_SIZE.get(4), DOORS_TYPE.get(4)));
    }

    @ParameterizedTest()
    @MethodSource("cases")
    public void updateTest(DoorDTO dto) {
        if (dto != null) {
            String expected = dto.getType();
            DOOR_DAO.save(dto, DoorDTO.class);
            dto.setType("another");
            DOOR_DAO.update(dto);
            String actual = DOOR_DAO.get(dto.getId(), DoorDTO.class).getType();

            assertNotEquals(expected, actual, "Expected type: " + expected + ", actual: " + actual);
        } else {
            assertThrows(IllegalArgumentException.class, () -> DOOR_DAO.update(dto));
        }
    }

    @Test
    public void getBySizeTest() {
        doorDTOS.forEach(d -> DOOR_DAO.save(d, DoorDTO.class));

        List<DoorDTO> doorDTOList = DOOR_DAO.getBySize(FROM_SIZE, TO_SIZE);
        int expected = 3;
        assertNotNull(doorDTOList);
        assertTrue(doorDTOList.size() >= expected, "Expected: " + expected + ", actual: " + doorDTOList.size());

        List<DoorDTO> emptyList = DOOR_DAO.getBySize(NULL_SIZE, NULL_SIZE);
        assertTrue(emptyList.isEmpty());
    }

    static Stream<Arguments> cases() {
        return Stream.of(

                Arguments.of(MockUtils.buildDoor(DOORS_SIZE.get(0), DOORS_TYPE.get(0))),

                Arguments.of(MockUtils.buildDoor(DOORS_SIZE.get(1), DOORS_TYPE.get(1))),

                Arguments.of(MockUtils.buildDoor(DOORS_SIZE.get(2), DOORS_TYPE.get(2))),

                Arguments.of(MockUtils.buildDoor(DOORS_SIZE.get(3), DOORS_TYPE.get(3))),

                Arguments.of(MockUtils.buildDoor(DOORS_SIZE.get(4), DOORS_TYPE.get(4))),

                Arguments.of((Object) null)

        );
    }

}