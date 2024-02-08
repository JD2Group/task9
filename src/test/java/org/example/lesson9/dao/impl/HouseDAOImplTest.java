package org.example.lesson9.dao.impl;

import org.example.lesson9.dao.HouseDAO;
import org.example.lesson9.dto.HouseDTO;
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
@Order(2)
class HouseDAOImplTest {
    private static final HouseDAO HOUSE_DAO = new HouseDAOImpl();
    private static final List<HouseDTO> houseDTOS = new ArrayList<>();

    @BeforeAll
    public static void createHouseList() {
        houseDTOS.add(MockUtils.buildHouse(HOUSES_SIZE.get(0), HOUSES_COLOR.get(0), HOUSES_ROOM.get(0)));
        houseDTOS.add(MockUtils.buildHouse(HOUSES_SIZE.get(0), HOUSES_COLOR.get(0), HOUSES_ROOM.get(0)));
        houseDTOS.add(MockUtils.buildHouse(HOUSES_SIZE.get(1), HOUSES_COLOR.get(1), HOUSES_ROOM.get(1)));
        houseDTOS.add(MockUtils.buildHouse(HOUSES_SIZE.get(1), HOUSES_COLOR.get(1), HOUSES_ROOM.get(1)));
        houseDTOS.add(MockUtils.buildHouse(HOUSES_SIZE.get(1), HOUSES_COLOR.get(1), HOUSES_ROOM.get(1)));
        houseDTOS.add(MockUtils.buildHouse(HOUSES_SIZE.get(2), HOUSES_COLOR.get(2), HOUSES_ROOM.get(2)));
        houseDTOS.add(MockUtils.buildHouse(HOUSES_SIZE.get(3), HOUSES_COLOR.get(3), HOUSES_ROOM.get(3)));
        houseDTOS.add(MockUtils.buildHouse(HOUSES_SIZE.get(3), HOUSES_COLOR.get(3), HOUSES_ROOM.get(3)));
        houseDTOS.add(MockUtils.buildHouse(HOUSES_SIZE.get(4), HOUSES_COLOR.get(4), HOUSES_ROOM.get(4)));
    }

    @ParameterizedTest()
    @MethodSource("cases")
    public void updateTest(HouseDTO dto) {
        if (dto != null) {
            String expected = dto.getColor();
            HOUSE_DAO.save(dto, HouseDTO.class);
            dto.setColor("another");
            HOUSE_DAO.update(dto);
            String actual = HOUSE_DAO.get(dto.getId(), HouseDTO.class).getColor();

            assertNotEquals(expected, actual, "Expected color: " + expected + ", actual: " + actual);
        } else {
            assertThrows(IllegalArgumentException.class, () -> HOUSE_DAO.update(dto));
        }
    }

    @Test
    public void getByColorTest() {
        houseDTOS.forEach(h -> {
            h.setColor(h.getColor() + COLOR_TEST);
            HOUSE_DAO.save(h, HouseDTO.class);
        });

        List<HouseDTO> whiteHouses = HOUSE_DAO.getByColor(HOUSES_COLOR.get(0) + COLOR_TEST);
        int whiteHousesExpectedSize = 2;
        List<HouseDTO> blackHouses = HOUSE_DAO.getByColor(HOUSES_COLOR.get(1) + COLOR_TEST);
        int blackHousesExpectedSize = 3;
        List<HouseDTO> greenHouses = HOUSE_DAO.getByColor(HOUSES_COLOR.get(2) + COLOR_TEST);
        int greenHousesExpectedSize = 1;
        List<HouseDTO> blackAndWhiteHouses = HOUSE_DAO.getByColor(HOUSES_COLOR.get(3) + COLOR_TEST);
        int blackAndWhiteHousesExpectedSize = 2;
        List<HouseDTO> redHouses = HOUSE_DAO.getByColor(HOUSES_COLOR.get(4) + COLOR_TEST);
        int redHousesExpectedSize = 1;
        List<HouseDTO> emptyList = HOUSE_DAO.getByColor(NULL_COLOR);

        assertEquals(whiteHouses.size(), whiteHousesExpectedSize, "White houses expected list size: " + whiteHousesExpectedSize);
        assertEquals(blackHouses.size(), blackHousesExpectedSize, "Black houses expected list size: " + blackHousesExpectedSize);
        assertEquals(greenHouses.size(), greenHousesExpectedSize, "Green houses expected list size: " + greenHousesExpectedSize);
        assertEquals(blackAndWhiteHouses.size(), blackAndWhiteHousesExpectedSize, "Black and white houses expected list size: "
                + blackAndWhiteHousesExpectedSize);
        assertEquals(redHouses.size(), redHousesExpectedSize, "Red houses expected list size: " + redHousesExpectedSize);
        assertTrue(emptyList.isEmpty());
    }

    static Stream<Arguments> cases() {
        return Stream.of(

                Arguments.of(MockUtils.buildHouse(HOUSES_SIZE.get(0), HOUSES_COLOR.get(0), HOUSES_ROOM.get(0))),

                Arguments.of(MockUtils.buildHouse(HOUSES_SIZE.get(1), HOUSES_COLOR.get(1), HOUSES_ROOM.get(1))),

                Arguments.of(MockUtils.buildHouse(HOUSES_SIZE.get(2), HOUSES_COLOR.get(2), HOUSES_ROOM.get(2))),

                Arguments.of(MockUtils.buildHouse(HOUSES_SIZE.get(3), HOUSES_COLOR.get(3), HOUSES_ROOM.get(3))),

                Arguments.of(MockUtils.buildHouse(HOUSES_SIZE.get(4), HOUSES_COLOR.get(4), HOUSES_ROOM.get(4))),

                Arguments.of((Object) null)
        );
    }

}
