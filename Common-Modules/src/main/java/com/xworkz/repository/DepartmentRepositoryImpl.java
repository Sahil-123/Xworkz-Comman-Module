package com.xworkz.repository;


import com.xworkz.dto.DepartmentAdminDTO;
import com.xworkz.dto.DepartmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<List<DepartmentDTO>> findAll() {
        System.out.println("Repository fetching departments");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT d FROM DepartmentDTO d", DepartmentDTO.class);
            List<DepartmentDTO> departmentDTOList = query.getResultList();
            return Optional.ofNullable(departmentDTOList);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return Optional.empty();
    }

    @Override
    public Optional<DepartmentDTO> findById(long id) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            DepartmentDTO departmentDTO = entityManager.find(DepartmentDTO.class, id);
            return Optional.ofNullable(departmentDTO);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Optional.empty();
    }

}
