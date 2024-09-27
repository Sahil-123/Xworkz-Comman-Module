package com.xworkz.repository;

import com.xworkz.entity.ComplaintHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class ComplaintHistoryRepositoryImpl implements ComplaintHistoryRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean save(ComplaintHistoryDTO complaintHistoryDTO) {
        System.out.println("Complaint History Repository save process initiated with: " + complaintHistoryDTO);
        try {
            entityManager.persist(complaintHistoryDTO);
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ComplaintHistoryDTO>> getHistoryByComplaintId(Integer complaintID) {
        System.out.println("Complaint History Repository retrieving by complaintID: " + complaintID);
        try {
            Query query = entityManager.createNamedQuery("findHistoryByComplaintID");
            query.setParameter("complaintID", complaintID.longValue());
            List<ComplaintHistoryDTO> complaintHistoryDTOList = (List<ComplaintHistoryDTO>) query.getResultList();
            return Optional.ofNullable(complaintHistoryDTOList);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
