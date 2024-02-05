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
        houseDTOS.forEach(h -> HOUSE_DAO.save(h, HouseDTO.class));

        List<HouseDTO> whiteHouses = HOUSE_DAO.getByColor(HOUSES_COLOR.get(0));
        int whiteHousesExpectedSize = 2;
        List<HouseDTO> blackHouses = HOUSE_DAO.getByColor(HOUSES_COLOR.get(1));
        int blackHousesExpectedSize = 3;
        List<HouseDTO> greenHouses = HOUSE_DAO.getByColor(HOUSES_COLOR.get(2));
        int greenHousesExpectedSize = 1;
        List<HouseDTO> blackAndWhiteHouses = HOUSE_DAO.getByColor(HOUSES_COLOR.get(3));
        int blackAndWhiteHousesExpectedSize = 2;
        List<HouseDTO> redHouses = HOUSE_DAO.getByColor(HOUSES_COLOR.get(4));
        int redHousesExpectedSize = 1;
        List<HouseDTO> emptyList = HOUSE_DAO.getByColor(NULL_COLOR);

        assertTrue(whiteHouses.size() >= whiteHousesExpectedSize, "White houses expected list size: " + whiteHousesExpectedSize);
        assertTrue(blackHouses.size() >= blackHousesExpectedSize, "Black houses expected list size: " + blackHousesExpectedSize);
        assertTrue(greenHouses.size() >= greenHousesExpectedSize, "Green houses expected list size: " + greenHousesExpectedSize);
        assertTrue(blackAndWhiteHouses.size() >= blackAndWhiteHousesExpectedSize, "Black and white houses expected list size: "
                + blackAndWhiteHousesExpectedSize);
        assertTrue(redHouses.size() >= redHousesExpectedSize, "Red houses expected list size: " + redHousesExpectedSize);
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
