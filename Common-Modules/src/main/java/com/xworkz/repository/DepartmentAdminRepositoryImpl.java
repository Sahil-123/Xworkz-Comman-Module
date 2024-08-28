package com.xworkz.repository;

import com.xworkz.dto.DTOListPage;
import com.xworkz.entity.DepartmentAdminDTO;
import com.xworkz.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentAdminRepositoryImpl implements DepartmentAdminRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<DepartmentAdminDTO>> findAll() {
        try {
            Query query = entityManager.createQuery("SELECT d FROM DepartmentAdminDTO d order by d.createdDate desc", DepartmentAdminDTO.class);
            List<DepartmentAdminDTO> departmentAdminDTOList = query.getResultList();
            return Optional.ofNullable(departmentAdminDTOList);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DTOListPage<DepartmentAdminDTO> findAllByPagination(Integer offset, Integer pageSize) {
        System.out.println("Find all department admin by pagination "+offset+" "+pageSize);

        try {
            Long count = getCount();
            Query query = entityManager.createQuery("SELECT d FROM DepartmentAdminDTO d order by d.createdDate desc", DepartmentAdminDTO.class);
            query.setFirstResult(CommonUtils.getFirstResultForPagination(offset,pageSize));
            query.setMaxResults(pageSize);

            List<DepartmentAdminDTO> departmentAdminDTOList = query.getResultList();
            return new DTOListPage<>(count,Optional.ofNullable(departmentAdminDTOList));
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Long getCount(){
        System.out.println("Department admin Repository getting count.");

        try {
            Query queryTotal = entityManager.createQuery
                    ("SELECT count(d) FROM DepartmentAdminDTO d");
            return (Long)queryTotal.getSingleResult();

        }catch(PersistenceException e){
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentAdminDTO> findById(Long id) {
        try {
            DepartmentAdminDTO departmentAdminDTO = entityManager.find(DepartmentAdminDTO.class, id);
            return Optional.ofNullable(departmentAdminDTO);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean save(DepartmentAdminDTO departmentAdminDTO) {
        System.out.println("Department admin save process for dto "+departmentAdminDTO);

        try {
            entityManager.persist(departmentAdminDTO);
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean update(DepartmentAdminDTO departmentAdminDTO) {
        try {
            entityManager.merge(departmentAdminDTO);
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        try {
            DepartmentAdminDTO departmentAdminDTO = entityManager.find(DepartmentAdminDTO.class, id);
            if (departmentAdminDTO != null) {
                entityManager.remove(departmentAdminDTO);
                return true;
            }

            return false;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentAdminDTO> findByEmail(String email) {
        try {
            Query query = entityManager.createQuery("SELECT d FROM DepartmentAdminDTO d WHERE d.email = :email", DepartmentAdminDTO.class);
            query.setParameter("email", email);
            DepartmentAdminDTO departmentAdminDTO = (DepartmentAdminDTO) query.getSingleResult();
            return Optional.ofNullable(departmentAdminDTO);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkMobile(String mobile) {
        try {
            Query query = entityManager.createQuery("SELECT COUNT(e) FROM DepartmentAdminDTO e WHERE e.mobile = :mobile");
            query.setParameter("mobile", mobile);
            Long count = (Long) query.getSingleResult();
            return count > 0;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkEmail(String email) {
        try {
            Query query = entityManager.createQuery("SELECT COUNT(e) FROM DepartmentAdminDTO e WHERE e.email = :email");
            query.setParameter("email", email);
            Long count = (Long) query.getSingleResult();
            return count > 0;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
