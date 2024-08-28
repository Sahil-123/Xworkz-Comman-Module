package com.xworkz.repository;

import com.xworkz.entity.EmployeeImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeImageRepositoryImpl implements EmployeeImageRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean save(EmployeeImageDTO employeeImageDTO) {
        System.out.println("Employee Image Repository save process is initiated using dto." + employeeImageDTO);

        try {
            entityManager.persist(employeeImageDTO);
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<EmployeeImageDTO>> getImagesByEmployeeId(Long employeeId) {
        System.out.println("Employee Image Repository find by Employee ID process is initiated using employeeId." + employeeId);

        try {
            Query query = entityManager.createNamedQuery("findImagesByEmployeeID");
            query.setParameter("employeeId", employeeId.intValue());
            List<EmployeeImageDTO> employeeImageDTOList = (List<EmployeeImageDTO>) query.getResultList();
            System.out.println(employeeImageDTOList);
            return Optional.ofNullable(employeeImageDTOList);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean updateImageActiveByEmployeeId(List<EmployeeImageDTO> employeeImageDTOList) {
        System.out.println("Employee Image Repository update Image Active By Employee Id process is initiated using dto list." + employeeImageDTOList);

        try {
            for (EmployeeImageDTO employeeImageDTO : employeeImageDTOList) {
                Query query = entityManager.createNamedQuery("updateEmployeeImageActive");
                query.setParameter("active", employeeImageDTO.isActive());
                query.setParameter("employeeId", employeeImageDTO.getEmployeeId());
                query.executeUpdate();
            }
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
