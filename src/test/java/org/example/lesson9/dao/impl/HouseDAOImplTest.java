package org.example.lesson9.dao.impl;

import org.example.lesson9.dao.HouseDAO;
import org.example.lesson9.dto.HouseDTO;
import org.example.lesson9.utils.HibernateUtil;
import org.example.lesson9.utils.ReflectionManager;
import org.example.lesson9.utils_src.MockUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.example.lesson9.utils_src.MockConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(1)
class HouseDAOImplTest {
    private final HouseDAO houseDAO = new HouseDAOImpl();
    private final List<HouseDTO> houseDTOS = new ArrayList<>();

    @BeforeEach
    public void createHouseList() {
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
    public void saveTest(HouseDTO houseDTO) {
        if (houseDTO != null) {
            int expectedId = 0;
            houseDAO.save(houseDTO);
            int actualId = houseDTO.getId();
            assertNotEquals(expectedId, actualId);
        } else {
            assertThrows(IllegalArgumentException.class, () -> houseDAO.save(houseDTO));
        }
    }

    @ParameterizedTest()
    @MethodSource("cases")
    public void deleteTest(HouseDTO houseDTO) {
        if (houseDTO != null) {
            int idBeforeSave = houseDTO.getId();
            houseDAO.delete(idBeforeSave);
            boolean isManagerClosed = HibernateUtil.getEntityManager().isOpen();
            assertTrue(isManagerClosed);

            houseDAO.save(houseDTO);
            int id = (int) ReflectionManager.getId(houseDTO);
            houseDAO.delete(id);
            HouseDTO result = houseDAO.get(id);
            assertNull(result, "Expected: null" + ", actual: " + result);
        }
    }


    @ParameterizedTest()
    @MethodSource("cases")
    public void updateTest(HouseDTO dto) {
        if (dto != null) {
            String expected = dto.getColor();
            houseDAO.save(dto);
            dto.setColor("another");
            houseDAO.update(dto);
            String actual = houseDAO.get(dto.getId()).getColor();

            assertNotEquals(expected, actual, "Expected color: " + expected + ", actual: " + actual);
        } else {
            assertThrows(IllegalArgumentException.class, () -> houseDAO.update(dto));
        }
    }

    @Test
    public void getByColorTest() {
        houseDTOS.forEach(h -> {
            h.setColor(h.getColor() + COLOR_TEST);
            houseDAO.save(h);
        });

        List<HouseDTO> whiteHouses = houseDAO.getByColor(HOUSES_COLOR.get(0) + COLOR_TEST);
        int whiteHousesExpectedSize = 2;
        List<HouseDTO> blackHouses = houseDAO.getByColor(HOUSES_COLOR.get(1) + COLOR_TEST);
        int blackHousesExpectedSize = 3;
        List<HouseDTO> greenHouses = houseDAO.getByColor(HOUSES_COLOR.get(2) + COLOR_TEST);
        int greenHousesExpectedSize = 1;
        List<HouseDTO> blackAndWhiteHouses = houseDAO.getByColor(HOUSES_COLOR.get(3) + COLOR_TEST);
        int blackAndWhiteHousesExpectedSize = 2;
        List<HouseDTO> redHouses = houseDAO.getByColor(HOUSES_COLOR.get(4) + COLOR_TEST);
        int redHousesExpectedSize = 1;
        List<HouseDTO> emptyList = houseDAO.getByColor(NULL_COLOR);

        assertEquals(whiteHouses.size(), whiteHousesExpectedSize, "White houses expected list size: " + whiteHousesExpectedSize);
        assertEquals(blackHouses.size(), blackHousesExpectedSize, "Black houses expected list size: " + blackHousesExpectedSize);
        assertEquals(greenHouses.size(), greenHousesExpectedSize, "Green houses expected list size: " + greenHousesExpectedSize);
        assertEquals(blackAndWhiteHouses.size(), blackAndWhiteHousesExpectedSize, "Black and white houses expected list size: "
                + blackAndWhiteHousesExpectedSize);
        assertEquals(redHouses.size(), redHousesExpectedSize, "Red houses expected list size: " + redHousesExpectedSize);
        assertTrue(emptyList.isEmpty());
    }

    @AfterEach
    public void deleteAllHouses() {
        EntityManager manager = HibernateUtil.getEntityManager();
        manager.getTransaction().begin();
        Query deleteAll = manager.createNativeQuery(DELETE_ALL_HOUSES_QUERY);
        deleteAll.executeUpdate();
        manager.getTransaction().commit();
        manager.close();
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
