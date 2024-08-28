package com.xworkz.repository;

import com.xworkz.dto.DTOListPage;
import com.xworkz.entity.EmployeeDTO;
import com.xworkz.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<EmployeeDTO>> findAll() {
        try {
            Query query = entityManager.createQuery("SELECT e FROM EmployeeDTO e order by e.createdDate desc", EmployeeDTO.class);
            List<EmployeeDTO> employeeDTOList = query.getResultList();
            return Optional.ofNullable(employeeDTOList);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findById(Long id) {
        try {
            EmployeeDTO employeeDTO = entityManager.find(EmployeeDTO.class, id);
            return Optional.ofNullable(employeeDTO);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public Boolean save(EmployeeDTO employeeDTO) {
        try {
            entityManager.persist(employeeDTO);
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public Boolean update(EmployeeDTO employeeDTO) {
        try {
            entityManager.merge(employeeDTO);
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        try {
            EmployeeDTO employeeDTO = entityManager.find(EmployeeDTO.class, id);
            if (employeeDTO != null) {
                entityManager.remove(employeeDTO);
                return true;
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }

        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findByEmail(String email) {
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
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<EmployeeDTO>> findByEmployeeMobile(String mobile) {
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
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkMobile(String mobile) {
        try {
            Query query = entityManager.createQuery("SELECT COUNT(e) FROM EmployeeDTO e WHERE e.mobile = :mobile");
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
            Query query = entityManager.createQuery("SELECT COUNT(e) FROM EmployeeDTO e WHERE e.email = :email");
            query.setParameter("email", email);
            Long count = (Long) query.getSingleResult();
            return count > 0;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<EmployeeDTO>> searchAllEmployees(EmployeeDTO employeeDTO) {
        System.out.println("Employee in repository processing " + employeeDTO);

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<EmployeeDTO> query = cb.createQuery(EmployeeDTO.class);
            Root<EmployeeDTO> root = query.from(EmployeeDTO.class);

            List<Predicate> predicates = getPredicates(employeeDTO, cb, root);;


//            if (employeeDTO.getLoginCount() != 0) {
//                predicates.add(cb.equal(root.get("loginCount"), employeeDTO.getLoginCount()));
//            }
//            if (employeeDTO.getFailedAttempts() != 0) {
//                predicates.add(cb.equal(root.get("failedAttempts"), employeeDTO.getFailedAttempts()));
//            }
//            if (employeeDTO.getFailedAttemptsDateTime() != null) {
//                predicates.add(cb.equal(root.get("failedAttemptsDateTime"), employeeDTO.getFailedAttemptsDateTime()));
//            }
//            if (employeeDTO.getCreatedBy() != null && !employeeDTO.getCreatedBy().isEmpty()) {
//                predicates.add(cb.equal(root.get("createdBy"), employeeDTO.getCreatedBy()));
//            }
//            if (employeeDTO.getCreatedDate() != null) {
//                predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate"), employeeDTO.getCreatedDate()));
//            }
//            if (employeeDTO.getUpdatedBy() != null && !employeeDTO.getUpdatedBy().isEmpty()) {
//                predicates.add(cb.equal(root.get("updatedBy"), employeeDTO.getUpdatedBy()));
//            }
//            if (employeeDTO.getUpdatedDate() != null) {
//                predicates.add(cb.greaterThanOrEqualTo(root.get("updatedDate"), employeeDTO.getUpdatedDate()));
//            }
//            if (employeeDTO.isLock()) {
//                predicates.add(cb.equal(root.get("employee_lock"), employeeDTO.isLock()));
//            }

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            query.orderBy(cb.desc(root.get("createdDate")));

            List<EmployeeDTO> results = entityManager.createQuery(query).getResultList();
            entityManager.close();

            System.out.println(results);

            return Optional.ofNullable(results);

        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static  List<Predicate>  getPredicates(EmployeeDTO employeeDTO, CriteriaBuilder cb, Root<EmployeeDTO> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (employeeDTO.getId() != null) {
            predicates.add(cb.equal(root.get("id"), employeeDTO.getId()));
        }
        if (employeeDTO.getFname() != null && !employeeDTO.getFname().isEmpty()) {
            predicates.add(cb.equal(root.get("fname"), employeeDTO.getFname()));
        }
        if (employeeDTO.getLname() != null && !employeeDTO.getLname().isEmpty()) {
            predicates.add(cb.equal(root.get("lname"), employeeDTO.getLname()));
        }
        if (employeeDTO.getEmail() != null && !employeeDTO.getEmail().isEmpty()) {
            predicates.add(cb.equal(root.get("email"), employeeDTO.getEmail()));
        }
        if (employeeDTO.getPassword() != null && !employeeDTO.getPassword().isEmpty()) {
            predicates.add(cb.equal(root.get("password"), employeeDTO.getPassword()));
        }
        if (employeeDTO.getMobile() != null && !employeeDTO.getMobile().isEmpty()) {
            predicates.add(cb.equal(root.get("mobile"), employeeDTO.getMobile()));
        }
        if (employeeDTO.getDepartmentId() != null) {
            System.out.println("dept is "+ employeeDTO.getDepartmentId());
            predicates.add(cb.equal(root.get("departmentId"), employeeDTO.getDepartmentId()));
        }

        return predicates;
    }

    @Override
    @Transactional(readOnly = true)
    public DTOListPage<EmployeeDTO> searchAllEmployees(EmployeeDTO employeeDTO, Integer offset, Integer pageSize) {
        System.out.println("Employee in repository processing " + employeeDTO);

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<EmployeeDTO> query = cb.createQuery(EmployeeDTO.class);
            Root<EmployeeDTO> root = query.from(EmployeeDTO.class);

            List<Predicate> predicates = getPredicates(employeeDTO, cb, root);;

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            query.orderBy(cb.desc(root.get("createdDate")));


            Query query1 = entityManager.createQuery(query);
            query1.setFirstResult(CommonUtils.getFirstResultForPagination(offset,pageSize));
            query1.setMaxResults(pageSize);
            List<EmployeeDTO> results = query1.getResultList();

            Query query2 = entityManager.createQuery(query);
            long count = (long) query2.getResultList().size();

            entityManager.close();
            System.out.println(results);

            return new DTOListPage<>(count,Optional.ofNullable(results));

        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }


}
