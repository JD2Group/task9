package org.example.lesson9.utils.wrappers;

import org.example.lesson9.utils_src.MockUtils;
import org.example.lesson9.dto.DoorDTO;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;

import java.lang.reflect.Field;

import static org.example.lesson9.utils_src.MockConstants.DOORS_SIZE;
import static org.example.lesson9.utils_src.MockConstants.DOORS_TYPE;
import static org.junit.jupiter.api.Assertions.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(2)
class ThrowingFunctionWrapperTest {

    @Test
    public void applyTest() throws NoSuchFieldException {
        DoorDTO doorDTO = MockUtils.buildDoor(DOORS_SIZE.get(0), DOORS_TYPE.get(0));
        Field field = DoorDTO.class.getDeclaredField("id");
        field.setAccessible(true);
        Object actual = ThrowingFunctionWrapper.apply(f -> field.get(doorDTO)).apply(doorDTO);

        assertNotNull(actual);

        field.setAccessible(false);
        assertThrows(RuntimeException.class,
                () -> ThrowingFunctionWrapper.apply(f -> field.get(null)).apply(doorDTO));
    }

}