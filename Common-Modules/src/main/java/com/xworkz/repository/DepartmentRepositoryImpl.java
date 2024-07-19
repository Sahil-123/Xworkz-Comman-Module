package com.xworkz.repository;


import com.xworkz.dto.DTOListPage;
import com.xworkz.entity.DepartmentDTO;
import com.xworkz.entity.UserDTO;
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
    public DTOListPage<DepartmentDTO> findAll(Integer offset, Integer pageSize) {
        System.out.println("Repository fetching departments with pagination");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Long count = getCount();
            Query query = entityManager.createQuery("SELECT d FROM DepartmentDTO d", DepartmentDTO.class);
            query.setFirstResult(CommonUtils.getFirstResultForPagination(offset,pageSize));
            query.setMaxResults(pageSize);

            List<DepartmentDTO> departmentDTOList = query.getResultList();
            return new DTOListPage<DepartmentDTO>(count,Optional.ofNullable(departmentDTOList));
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return new DTOListPage<DepartmentDTO>(0L,Optional.empty());
    }

    private Long getCount(){
        System.out.println("Department Repository getting count.");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Query queryTotal = entityManager.createQuery
                    ("SELECT count(d) FROM DepartmentDTO d");
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

    @Override
    public boolean checkDepartmentName(String departmentName) {
        System.out.println("Department Repository check department name process is initiated using department name." + departmentName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String query = "SELECT count(d) FROM DepartmentDTO d where d.departmentName=:departmentName ";
            Long count = (Long) entityManager.createQuery(query)
                    .setParameter("departmentName", departmentName)
                    .getSingleResult();

            return count > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return true;
    }

    @Override
    public Boolean save(DepartmentDTO departmentDTO) {
        System.out.println("Department Save Process is initiated with dto "+departmentDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(departmentDTO);
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        return false;
    }

}
