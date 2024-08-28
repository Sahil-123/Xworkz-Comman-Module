package com.xworkz.repository;


import com.xworkz.dto.DTOListPage;
import com.xworkz.dto.DepartmentDTOListPage;
import com.xworkz.entity.DepartmentDTO;
import com.xworkz.entity.UserDTO;
import com.xworkz.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(cacheNames = "departments")
    public Optional<List<DepartmentDTO>> findAll() {
        System.out.println("Repository fetching departments");
        try {
            Query query = entityManager.createQuery("SELECT d FROM DepartmentDTO d order by d.createdDate desc", DepartmentDTO.class);
            List<DepartmentDTO> departmentDTOList = query.getResultList();
            return Optional.ofNullable(departmentDTOList);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(value = "departmentsList", key = "#offset")
    public DepartmentDTOListPage<DepartmentDTO> findAll(Integer offset, Integer pageSize) {
        System.out.println("Repository fetching departments with pagination");

        try {
            Long count = getCount();
            Query query = entityManager.createQuery("SELECT d FROM DepartmentDTO d order by d.createdDate desc", DepartmentDTO.class);
            query.setFirstResult(CommonUtils.getFirstResultForPagination(offset,pageSize));
            query.setMaxResults(pageSize);

            List<DepartmentDTO> departmentDTOList = query.getResultList();
            return new DepartmentDTOListPage<DepartmentDTO>(count,departmentDTOList);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

//    @Cacheable(cacheNames = "departmentsCount")
    private Long getCount(){
        System.out.println("Department Repository getting count.");
        try {
            Query queryTotal = entityManager.createQuery
                    ("SELECT count(d) FROM DepartmentDTO d");
            return (Long)queryTotal.getSingleResult();

        }catch(PersistenceException e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(cacheNames = "departmentId", key = "#id")
    public Optional<DepartmentDTO> findById(long id) {
        System.out.println("fetching department by id");
        try {
            DepartmentDTO departmentDTO = entityManager.find(DepartmentDTO.class, id);
            return Optional.ofNullable(departmentDTO);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(cacheNames = "departmentName", key = "#departmentName")
    public boolean checkDepartmentName(String departmentName) {
        System.out.println("Department Repository check department name process is initiated using department name." + departmentName);

        try {
            String query = "SELECT count(d) FROM DepartmentDTO d where d.departmentName=:departmentName ";
            Long count = (Long) entityManager.createQuery(query)
                    .setParameter("departmentName", departmentName)
                    .getSingleResult();

            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
//    @CachePut(cacheNames = {"departments","departmentsWithPagination"})
    public Boolean save(DepartmentDTO departmentDTO) {
        System.out.println("Department Save Process is initiated with dto "+departmentDTO);

        try {
            entityManager.persist(departmentDTO);
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
