package org.example.lesson9.dao.impl;

import org.example.lesson9.dao.HouseDAO;
import org.example.lesson9.dto.HouseDTO;

import javax.persistence.TypedQuery;
import java.util.List;

public class HouseDAOImpl extends DAOImpl<HouseDTO> implements HouseDAO {

    @Override
    public List<HouseDTO> getByColor(String color) {
        startTransaction();
        TypedQuery<HouseDTO> query = getManager().createNamedQuery("getByColor", HouseDTO.class);
        query.setParameter("color", color);
        List<HouseDTO> houseDTOList = query.getResultList();
        commit();
        return houseDTOList;
    }
}
