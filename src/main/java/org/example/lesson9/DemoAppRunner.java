package org.example.lesson9;

import org.example.lesson9.dao.DAO;
import org.example.lesson9.dao.DoorDAO;
import org.example.lesson9.dao.HouseDAO;
import org.example.lesson9.dao.impl.DoorDAOImpl;
import org.example.lesson9.dao.impl.HouseDAOImpl;
import org.example.lesson9.dto.DoorDTO;
import org.example.lesson9.dto.HouseDTO;
import org.example.lesson9.utils.GsonManager;
import org.example.lesson9.utils.HibernateUtil;
import org.example.lesson9.utils.ReflectionManager;
import org.example.lesson9.utils.wrappers.ThrowingConsumerWrapper;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.lesson9.utils.Constants.*;

public class DemoAppRunner<T extends Serializable> {
    private static final GsonManager GSON_MANAGER = new GsonManager();
    private static final DoorDAO DOORS_DAO = new DoorDAOImpl();
    private static final HouseDAO HOUSE_DAO = new HouseDAOImpl();


    public static void main(String[] args) throws NoSuchMethodException {
        DemoAppRunner<DoorDTO> doorDemo = new DemoAppRunner<>();
        DemoAppRunner<HouseDTO> houseDemo = new DemoAppRunner<>();
        Method doorsUniqueMethod = DoorDAO.class.getDeclaredMethod("getBySize", double.class, double.class);
        Method housesUniqueMethod = HouseDAO.class.getDeclaredMethod("getByColor", String.class);
        doorDemo.runner(DOORS_IN_FILE_PATH, DOORS_OUT_FILE_PATH, DoorDTO.class, DOORS_DAO, doorsUniqueMethod, 900D, 1300D);
        houseDemo.runner(HOUSES_IN_FILE_PATH, HOUSES_OUT_FILE_PATH, HouseDTO.class, HOUSE_DAO, housesUniqueMethod, "черный");
        HibernateUtil.close();
    }

    private void runner(String inFilePath, String outFilePath, Class<T> clazz, DAO<T> dao, Method method, Object... methodParameters) {
        try {
            List<T> dtoList = GSON_MANAGER.readDTOList(inFilePath, clazz);
            System.out.println("\nList before save:");
            dtoList.forEach(System.out::println);

            List<T> dtoAfterSave = new ArrayList<>();
            dtoList.forEach(ThrowingConsumerWrapper.accept(dto -> dtoAfterSave.add(dao.save(dto, clazz)), SQLException.class));

            if (!dtoAfterSave.isEmpty()) {
                System.out.println("\nList after save: ");
                dtoAfterSave.forEach(System.out::println);
            }

            int randomObject = RANDOM.nextInt(dtoAfterSave.size());
            Object id = ReflectionManager.getId(dtoAfterSave.get(randomObject));
            int randomObjectId = id != null ? (int) id : 1;

            T objectForUpdate = dao.get(randomObjectId, clazz);

            System.out.println("Get object by id " + randomObjectId + ": " + objectForUpdate);
            System.out.println("Updated object: " + dao.update(objectForUpdate));
            dao.delete(randomObjectId, clazz);
            T deleteResult = dao.get(randomObjectId, clazz);
            if (deleteResult == null) {
                System.out.println("Object with id: " + randomObjectId + " deleted");
            } else {
                System.out.println("Removal failed");
            }

            if (method.getParameterCount() != 0) {
                var result = method.invoke(dao, methodParameters);
                System.out.println("Unique method: " + method.getName() + "(" + Arrays.toString(methodParameters) + ")");
                if (result != null) {
                    System.out.println(result);
                } else {
                    System.out.println("Sorry, nothing found...\n");
                }
                GSON_MANAGER.writeDTOList(outFilePath, result);
            }
        } catch (InvocationTargetException | IOException | IllegalAccessException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

    }
}