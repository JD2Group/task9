package org.example.lesson9.dao.impl;

import org.example.lesson9.dao.DoorDAO;
import org.example.lesson9.dto.DoorDTO;
import org.example.lesson9.utils.HibernateUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.example.lesson9.utils.Constants.*;


public class DoorDAOImpl extends DAOImpl<DoorDTO> implements DoorDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<DoorDTO> getBySize(double fromSize, double toSize) {
        EntityManager manager = HibernateUtil.getEntityManager();
        manager.getTransaction().begin();
        Query query = manager.createNativeQuery(GET_BY_SIZE_QUERY, DoorDTO.class);
        query.setParameter(FROM_SIZE_VALUE, fromSize);
        query.setParameter(TO_SIZE_VALUE, toSize);
        List<DoorDTO> doorDTOList = query.getResultList();
        manager.getTransaction().commit();
        manager.close();
        return doorDTOList;
    }

    @Override
    protected Class<DoorDTO> getDTOClass() {
        return DoorDTO.class;
    }
}
