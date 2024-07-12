package com.xworkz.repository;

import com.xworkz.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<List<EmployeeDTO>> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT e FROM EmployeeDTO e", EmployeeDTO.class);
            List<EmployeeDTO> employeeDTOList = query.getResultList();
            return Optional.ofNullable(employeeDTOList);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Optional.empty();
    }

    @Override
    public Optional<EmployeeDTO> findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EmployeeDTO employeeDTO = entityManager.find(EmployeeDTO.class, id);
            return Optional.ofNullable(employeeDTO);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Optional.empty();
    }

    @Override
    public Boolean save(EmployeeDTO employeeDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(employeeDTO);
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.close();
        }
        return false;
    }

    @Override
    public Boolean update(EmployeeDTO employeeDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(employeeDTO);
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.close();
        }
        return false;
    }

    @Override
    public Boolean deleteById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            EmployeeDTO employeeDTO = entityManager.find(EmployeeDTO.class, id);
            if (employeeDTO != null) {
                entityManager.remove(employeeDTO);
                entityManager.getTransaction().commit();
                return true;
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.close();
        }
        return false;
    }

    @Override
    public Optional<EmployeeDTO> findByEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT e FROM EmployeeDTO e WHERE e.email = :email", EmployeeDTO.class);
            query.setParameter("email", email);
            EmployeeDTO employeeDTO = (EmployeeDTO) query.getSingleResult();
            return Optional.ofNullable(employeeDTO);
        }
        catch (NoResultException noResultException){
            return Optional.empty();
        }
        catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<EmployeeDTO>> findByEmployeeMobile(String mobile) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT e FROM EmployeeDTO e WHERE e.mobile = :mobile", EmployeeDTO.class);
            query.setParameter("mobile", mobile);
            List<EmployeeDTO> employeeDTOList = query.getResultList();
            return Optional.ofNullable(employeeDTOList);
        }
        catch (NoResultException noResultException){
            return Optional.empty();
        }
        catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Optional.empty();
    }

    @Override
    public boolean checkMobile(String mobile) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT COUNT(e) FROM EmployeeDTO e WHERE e.mobile = :mobile");
            query.setParameter("mobile", mobile);
            Long count = (Long) query.getSingleResult();
            return count > 0;
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    @Override
    public boolean checkEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT COUNT(e) FROM EmployeeDTO e WHERE e.email = :email");
            query.setParameter("email", email);
            Long count = (Long) query.getSingleResult();
            return count > 0;
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }
}
