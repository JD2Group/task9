package org.example.lesson9.dao.impl;

import org.example.lesson9.dao.HouseDAO;
import org.example.lesson9.dto.HouseDTO;
import org.example.lesson9.utils.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.example.lesson9.utils.Constants.COLOR_VALUE;
import static org.example.lesson9.utils.Constants.GET_BY_COLOR_QUERY;

public class HouseDAOImpl extends DAOImpl<HouseDTO> implements HouseDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<HouseDTO> getByColor(String color) {
        EntityManager manager = HibernateUtil.getEntityManager();
        manager.getTransaction().begin();
        Query query = manager.createNativeQuery(GET_BY_COLOR_QUERY, HouseDTO.class);
        query.setParameter(COLOR_VALUE, color);
        List<HouseDTO> houseDTOList = query.getResultList();
        manager.getTransaction().commit();
        manager.close();
        return houseDTOList;
    }

    @Override
    protected Class<HouseDTO> getDTOClass() {
        return HouseDTO.class;
    }
}
