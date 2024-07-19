package com.xworkz.repository;

import com.xworkz.dto.DTOListPage;
import com.xworkz.entity.DepartmentAdminDTO;
import com.xworkz.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentAdminRepositoryImpl implements DepartmentAdminRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<List<DepartmentAdminDTO>> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT d FROM DepartmentAdminDTO d", DepartmentAdminDTO.class);
            List<DepartmentAdminDTO> departmentAdminDTOList = query.getResultList();
            return Optional.ofNullable(departmentAdminDTOList);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Optional.empty();
    }

    @Override
    public DTOListPage<DepartmentAdminDTO> findAllByPagination(Integer offset, Integer pageSize) {
        System.out.println("Find all department admin by pagination "+offset+" "+pageSize);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Long count = getCount();
            Query query = entityManager.createQuery("SELECT d FROM DepartmentAdminDTO d", DepartmentAdminDTO.class);
            query.setFirstResult(CommonUtils.getFirstResultForPagination(offset,pageSize));
            query.setMaxResults(pageSize);

            List<DepartmentAdminDTO> departmentAdminDTOList = query.getResultList();
            return new DTOListPage<>(count,Optional.ofNullable(departmentAdminDTOList));
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return new DTOListPage<DepartmentAdminDTO>(0L,Optional.empty());
    }

    private Long getCount(){
        System.out.println("Department admin Repository getting count.");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Query queryTotal = entityManager.createQuery
                    ("SELECT count(d) FROM DepartmentAdminDTO d");
            return (Long)queryTotal.getSingleResult();

        }catch(PersistenceException e){
            e.printStackTrace();
        }
        finally {
            entityManager.close();
        }

        return 0L;
    }

    @Override
    public Optional<DepartmentAdminDTO> findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            DepartmentAdminDTO departmentAdminDTO = entityManager.find(DepartmentAdminDTO.class, id);
            return Optional.ofNullable(departmentAdminDTO);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Optional.empty();
    }

    @Override
    public boolean save(DepartmentAdminDTO departmentAdminDTO) {
        System.out.println("Department admin save process for dto "+departmentAdminDTO);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(departmentAdminDTO);
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean update(DepartmentAdminDTO departmentAdminDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(departmentAdminDTO);
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean deleteById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            DepartmentAdminDTO departmentAdminDTO = entityManager.find(DepartmentAdminDTO.class, id);
            if (departmentAdminDTO != null) {
                entityManager.remove(departmentAdminDTO);
                entityManager.getTransaction().commit();
                return true;
            } else {
                entityManager.getTransaction().rollback();
                return false;
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<DepartmentAdminDTO> findByEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT d FROM DepartmentAdminDTO d WHERE d.email = :email", DepartmentAdminDTO.class);
            query.setParameter("email", email);
            DepartmentAdminDTO departmentAdminDTO = (DepartmentAdminDTO) query.getSingleResult();
            return Optional.ofNullable(departmentAdminDTO);
        } catch (PersistenceException e) {
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
            Query query = entityManager.createQuery("SELECT COUNT(e) FROM DepartmentAdminDTO e WHERE e.mobile = :mobile");
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
            Query query = entityManager.createQuery("SELECT COUNT(e) FROM DepartmentAdminDTO e WHERE e.email = :email");
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
