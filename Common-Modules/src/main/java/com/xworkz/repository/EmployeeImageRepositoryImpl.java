package com.xworkz.repository;

import com.xworkz.entity.EmployeeImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeImageRepositoryImpl implements EmployeeImageRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public boolean save(EmployeeImageDTO employeeImageDTO) {
        System.out.println("Employee Image Repository save process is initiated using dto." + employeeImageDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(employeeImageDTO);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        return false;
    }

    @Override
    public Optional<List<EmployeeImageDTO>> getImagesByEmployeeId(Long employeeId) {
        System.out.println("Employee Image Repository find by Employee ID process is initiated using employeeId." + employeeId);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            Query query = entityManager.createNamedQuery("findImagesByEmployeeID");
            query.setParameter("employeeId", employeeId.intValue());
            List<EmployeeImageDTO> employeeImageDTOList = (List<EmployeeImageDTO>) query.getResultList();
            System.out.println(employeeImageDTOList);
            return Optional.ofNullable(employeeImageDTOList);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return Optional.empty();
    }

    @Override
    public boolean updateImageActiveByEmployeeId(List<EmployeeImageDTO> employeeImageDTOList) {
        System.out.println("Employee Image Repository update Image Active By Employee Id process is initiated using dto list." + employeeImageDTOList);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            for (EmployeeImageDTO employeeImageDTO : employeeImageDTOList) {
                Query query = entityManager.createNamedQuery("updateEmployeeImageActive");
                query.setParameter("active", employeeImageDTO.isActive());
                query.setParameter("employeeId", employeeImageDTO.getEmployeeId());
                query.executeUpdate();
            }
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        return false;
    }
}
