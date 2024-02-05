package org.example.lesson9.dao.impl;

import org.example.lesson9.dao.DoorDAO;
import org.example.lesson9.dto.DoorDTO;

import javax.persistence.TypedQuery;
import java.util.List;


public class DoorDAOImpl extends DAOImpl<DoorDTO> implements DoorDAO {
    @Override
    public List<DoorDTO> getBySize(double fromSize, double toSize) {
        startTransaction();
        TypedQuery<DoorDTO> query = getManager()
                .createNamedQuery("getBySize", DoorDTO.class);
        query.setParameter("fromSize", fromSize);
        query.setParameter("toSize", toSize);
        List<DoorDTO> doorDTOList = query.getResultList();
        commit();
        return doorDTOList;
    }
}
