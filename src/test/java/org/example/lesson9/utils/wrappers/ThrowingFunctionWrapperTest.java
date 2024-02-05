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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(3)
class ThrowingFunctionWrapperTest {

    @Test
    public void applyTest() throws NoSuchFieldException {
        DoorDTO doorDTO = MockUtils.buildDoor(DOORS_SIZE.get(0), DOORS_TYPE.get(0));
        Field field = DoorDTO.class.getDeclaredField("id");
        Object actual = ThrowingFunctionWrapper.apply(f -> field.get(doorDTO), IllegalAccessException.class).apply(doorDTO);

        assertNull(actual);

        assertThrows(RuntimeException.class,
                () -> ThrowingFunctionWrapper.apply(f -> field.get(null), IllegalAccessException.class).apply(doorDTO));
    }

}