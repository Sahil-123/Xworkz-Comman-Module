package com.xworkz.repository;

import com.xworkz.dto.DTOListPage;
import com.xworkz.entity.ComplaintDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.requestDto.RequestUpdateComplaintDTO;
import com.xworkz.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ComplaintRepositoryImpl implements ComplaintRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Boolean save(ComplaintDTO complaintDTO) {
        System.out.println("Complaint Repository save process is initiated using dto." + complaintDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(complaintDTO);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            transaction.rollback();

            throw new InfoException("Something went wrong data not saved successfully.");
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<List<ComplaintDTO>> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT c FROM ComplaintDTO c", ComplaintDTO.class);
            List<ComplaintDTO> complaintList = query.getResultList();
            return Optional.ofNullable(complaintList);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return Optional.empty();
    }

    @Override
    public Optional<ComplaintDTO> findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            ComplaintDTO complaintDTO = entityManager.find(ComplaintDTO.class, id);
            return Optional.ofNullable(complaintDTO);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return Optional.empty();
    }

    @Override
    public Boolean update(ComplaintDTO complaintDTO) {
        System.out.println("Complaint Repository update process is initiated using dto." + complaintDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(complaintDTO);
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
    public Boolean deleteById(Long id) {
        System.out.println("Complaint Repository delete process is initiated using id." + id);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            ComplaintDTO complaintDTO = entityManager.find(ComplaintDTO.class, id);
            if (complaintDTO != null) {
                entityManager.remove(complaintDTO);
                transaction.commit();
                return true;
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        return false;
    }

    @Override
    public DTOListPage<ComplaintDTO> searchComplaints(ComplaintDTO complaintDTO, Integer offset, Integer pageSize) {
        System.out.println("Search Complaint in repository processing " + complaintDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ComplaintDTO> query = cb.createQuery(ComplaintDTO.class);
            Root<ComplaintDTO> root = query.from(ComplaintDTO.class);

            List<Predicate> predicates = getPredicates(complaintDTO, cb, root);

            predicates.add(cb.equal(root.get("userId"), complaintDTO.getUserId()));

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            query.orderBy(cb.desc(root.get("createdDate")));

            System.out.println("Query " + query);

//            Fetching data
            Query query1 = entityManager.createQuery(query);
            query1.setFirstResult(CommonUtils.getFirstResultForPagination(offset, pageSize));
            query1.setMaxResults(pageSize);
            List<ComplaintDTO> results = query1.getResultList();

//            Getting count
            Query query2 = entityManager.createQuery(query);
            Long count = (long) query2.getResultList().size();

            entityManager.close();
            return new DTOListPage<ComplaintDTO>(count, Optional.ofNullable(results));

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return new DTOListPage<>(0L, Optional.empty());
    }

//    private Long getCount(){
//        System.out.println("Complaint Repository getting count.");
//
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//        try {
//            Query queryTotal = entityManager.createQuery
//                    ("Select count(f.id) from ComplaintDTO f");
//
//            return (Long)queryTotal.getSingleResult();
//
//        }catch(PersistenceException e){
//            e.printStackTrace();
//        }
//        finally {
//            entityManager.close();
//        }
//
//        return 0L;
//    }


    @Override
    public DTOListPage<ComplaintDTO> searchAllComplaintsForAdmin(ComplaintDTO complaintDTO, Integer offset, Integer pageSize) {
        System.out.println("Complaint in repository processing for admin " + complaintDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ComplaintDTO> query = cb.createQuery(ComplaintDTO.class);
            Root<ComplaintDTO> root = query.from(ComplaintDTO.class);

            List<Predicate> predicates = getPredicatesList(complaintDTO, cb, root);

            query.where(cb.and(predicates.toArray(new Predicate[0])));

            Query query1 = entityManager.createQuery(query);
            query1.setFirstResult(CommonUtils.getFirstResultForPagination(offset,pageSize));
            query1.setMaxResults(pageSize);
            List<ComplaintDTO> results = query1.getResultList();

            Query query2 = entityManager.createQuery(query);
            Long count = (long) query2.getResultList().size();

            entityManager.close();
            return new DTOListPage<ComplaintDTO>(count, Optional.ofNullable(results));

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return new DTOListPage<ComplaintDTO>(0L, Optional.empty());
    }

    @Override
    public Optional<List<ComplaintDTO>> searchAllComplaintsForAdmin(ComplaintDTO complaintDTO) {
        System.out.println("Complaint in repository processing for  " + complaintDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ComplaintDTO> query = cb.createQuery(ComplaintDTO.class);
            Root<ComplaintDTO> root = query.from(ComplaintDTO.class);

            List<Predicate> predicates = getPredicatesList(complaintDTO, cb, root);

            query.where(cb.and(predicates.toArray(new Predicate[0])));

            Query query1 = entityManager.createQuery(query);
            List<ComplaintDTO> results = query1.getResultList();

            entityManager.close();
            return Optional.ofNullable(results);

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Optional.empty();
    }

    public Optional<List<ComplaintDTO>> searchAllComplaintsForNotResolved(ComplaintDTO complaintDTO) {
        System.out.println("Complaint in repository processing for admin for not resolved complaints " + complaintDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ComplaintDTO> query = cb.createQuery(ComplaintDTO.class);
            Root<ComplaintDTO> root = query.from(ComplaintDTO.class);

            List<Predicate> predicates = addPredicates(complaintDTO, cb, root);

//            predicates.add(cb.notLike(root.get("status"), "%" + CommonUtils.RESOLVED + "%"));

            Predicate resolvedStatus = cb.notLike(root.get("status"), "%" + CommonUtils.RESOLVED + "%");
            Predicate notResolvedStatus = cb.notLike(root.get("status"), "%" + CommonUtils.NOT_RESOLVED + "%");
            predicates.add(cb.and(resolvedStatus, notResolvedStatus));

            query.where(cb.and(predicates.toArray(new Predicate[0])));

            List<ComplaintDTO> results = entityManager.createQuery(query).getResultList();
            entityManager.close();
            return Optional.ofNullable(results);

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Optional.empty();
    }

    @Override
    public DTOListPage<ComplaintDTO> searchAllComplaintsForNotResolved(ComplaintDTO complaintDTO, Integer offset, Integer pageSize) {
        System.out.println("Complaint in repository processing for admin for not resolved complaints " + complaintDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ComplaintDTO> query = cb.createQuery(ComplaintDTO.class);
            Root<ComplaintDTO> root = query.from(ComplaintDTO.class);

            List<Predicate> predicates = addPredicates(complaintDTO, cb, root);

//            predicates.add(cb.notLike(root.get("status"), "%" + CommonUtils.RESOLVED + "%"));

            Predicate resolvedStatus = cb.notLike(root.get("status"), "%" + CommonUtils.RESOLVED + "%");
            Predicate notResolvedStatus = cb.notLike(root.get("status"), "%" + CommonUtils.NOT_RESOLVED + "%");
            predicates.add(cb.and(resolvedStatus, notResolvedStatus));

            query.where(cb.and(predicates.toArray(new Predicate[0])));

            Query query1 = entityManager.createQuery(query);
            query1.setFirstResult(CommonUtils.getFirstResultForPagination(offset,pageSize));
            query1.setMaxResults(pageSize);
            List<ComplaintDTO> results = query1.getResultList();

            Query query2 = entityManager.createQuery(query);
            Long count = (long) query2.getResultList().size();

            entityManager.close();
            return new DTOListPage<ComplaintDTO>(count, Optional.ofNullable(results));

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return new DTOListPage<ComplaintDTO>(0L, Optional.empty());
    }

    public Optional<List<ComplaintDTO>> searchAllComplaintsForResolved(ComplaintDTO complaintDTO) {
        System.out.println("Complaint in repository processing for admin for resolved complaints " + complaintDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ComplaintDTO> query = cb.createQuery(ComplaintDTO.class);
            Root<ComplaintDTO> root = query.from(ComplaintDTO.class);

            List<Predicate> predicates = addPredicates(complaintDTO, cb, root);

//            predicates.add(cb.like(root.get("status"), "%" + CommonUtils.RESOLVED + "%"));

            Predicate resolvedStatus = cb.like(root.get("status"), "%" + CommonUtils.RESOLVED + "%");
            Predicate notResolvedStatus = cb.like(root.get("status"), "%" + CommonUtils.NOT_RESOLVED + "%");
            predicates.add(cb.or(resolvedStatus, notResolvedStatus));

            query.where(cb.and(predicates.toArray(new Predicate[0])));

            List<ComplaintDTO> results = entityManager.createQuery(query).getResultList();
            entityManager.close();
            return Optional.ofNullable(results);

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Optional.empty();
    }

    @Override
    public DTOListPage<ComplaintDTO> searchAllComplaintsForResolved(ComplaintDTO complaintDTO, Integer offset, Integer pageSize) {
        System.out.println("Complaint in repository processing for admin for resolved complaints " + complaintDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ComplaintDTO> query = cb.createQuery(ComplaintDTO.class);
            Root<ComplaintDTO> root = query.from(ComplaintDTO.class);

            List<Predicate> predicates = addPredicates(complaintDTO, cb, root);

//            predicates.add(cb.like(root.get("status"), "%" + CommonUtils.RESOLVED + "%"));

            Predicate resolvedStatus = cb.like(root.get("status"), "%" + CommonUtils.RESOLVED + "%");
            Predicate notResolvedStatus = cb.like(root.get("status"), "%" + CommonUtils.NOT_RESOLVED + "%");
            predicates.add(cb.or(resolvedStatus, notResolvedStatus));

            query.where(cb.and(predicates.toArray(new Predicate[0])));

            Query query1 = entityManager.createQuery(query);
            query1.setFirstResult(CommonUtils.getFirstResultForPagination(offset,pageSize));
            query1.setMaxResults(pageSize);
            List<ComplaintDTO> results = query1.getResultList();

            Query query2 = entityManager.createQuery(query);
            Long count = (long) query2.getResultList().size();

            entityManager.close();
            return new DTOListPage<>(count, Optional.ofNullable(results));

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return new DTOListPage<ComplaintDTO>(0L, Optional.empty());
    }


    @Override
    public Boolean updateComplaint(RequestUpdateComplaintDTO requestUpdateComplaintDTO) {

        System.out.println("Repository update complaint " + requestUpdateComplaintDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
//            SELECT c FROM ComplaintDTO c WHERE c.id = :id
            Query query = entityManager.createQuery("update ComplaintDTO c set c.description = : description where c.id=:id");
            query.setParameter("description", requestUpdateComplaintDTO.getDescription());
            query.setParameter("id", requestUpdateComplaintDTO.getComplaintId());
            int count = query.executeUpdate();
            entityTransaction.commit();

            if (count > 0) {
                return true;
            }

        } catch (PersistenceException e) {
            entityTransaction.rollback();
            e.printStackTrace();
            throw new InfoException("Update Operation failed.");

        } finally {
            entityManager.close();
        }

        return false;
    }

    private List<Predicate> addPredicates(ComplaintDTO complaintDTO, CriteriaBuilder cb, Root<ComplaintDTO> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (complaintDTO.getId() != null) {
            predicates.add(cb.equal(root.get("id"), complaintDTO.getId()));
        }
        if (complaintDTO.getComplaintType() != null && !complaintDTO.getComplaintType().isEmpty()) {
            predicates.add(cb.equal(root.get("complaintType"), complaintDTO.getComplaintType()));
        }
        if (complaintDTO.getCity() != null && !complaintDTO.getCity().isEmpty()) {
            predicates.add(cb.equal(root.get("city"), complaintDTO.getCity()));
        }
        if (complaintDTO.getStatus() != null && !complaintDTO.getStatus().isEmpty()) {
            predicates.add(cb.equal(root.get("status"), complaintDTO.getStatus()));
        }
        if (complaintDTO.getCountry() != null && !complaintDTO.getCountry().isEmpty()) {
            predicates.add(cb.equal(root.get("country"), complaintDTO.getCountry()));
        }
        if (complaintDTO.getState() != null && !complaintDTO.getState().isEmpty()) {
            predicates.add(cb.equal(root.get("state"), complaintDTO.getState()));
        }
        if (complaintDTO.getAddress() != null && !complaintDTO.getAddress().isEmpty()) {
            predicates.add(cb.like(root.get("address"), "%" + complaintDTO.getAddress() + "%"));
        }
        if (complaintDTO.getDeptId() != null) {
            predicates.add(cb.equal(root.get("deptId"), complaintDTO.getDeptId()));
        }
        if (complaintDTO.getEmpId() != null) {
            predicates.add(cb.equal(root.get("empId"), complaintDTO.getEmpId()));
        }
        if (complaintDTO.getDescription() != null && !complaintDTO.getDescription().isEmpty()) {
            predicates.add(cb.like(root.get("description"), "%" + complaintDTO.getDescription() + "%"));
        }
        if (complaintDTO.getCreatedDate() != null) {
            predicates.add(cb.equal(root.get("createdDate"), complaintDTO.getCreatedDate()));
        }
        if (complaintDTO.getUpdatedBy() != null && !complaintDTO.getUpdatedBy().isEmpty()) {
            predicates.add(cb.equal(root.get("updatedBy"), complaintDTO.getUpdatedBy()));
        }
        if (complaintDTO.getUpdatedDate() != null) {
            predicates.add(cb.equal(root.get("updatedDate"), complaintDTO.getUpdatedDate()));
        }
        if (complaintDTO.getUserId() != null) {
            predicates.add(cb.equal(root.get("userId"), complaintDTO.getUserId()));
        }
        if (complaintDTO.getOtp() != null && !complaintDTO.getOtp().isEmpty()) {
            predicates.add(cb.equal(root.get("otp"), complaintDTO.getOtp()));
        }
        if (complaintDTO.getOtptime() != null) {
            predicates.add(cb.equal(root.get("otptime"), complaintDTO.getOtptime()));
        }
        if (complaintDTO.getComment() != null && !complaintDTO.getComment().isEmpty()) {
            predicates.add(cb.like(root.get("comment"), "%" + complaintDTO.getComment() + "%"));
        }

        return predicates;
    }

    private List<Predicate> getPredicatesList(ComplaintDTO complaintDTO, CriteriaBuilder cb, Root<ComplaintDTO> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (complaintDTO.getId() != null) {
            predicates.add(cb.equal(root.get("id"), complaintDTO.getId()));
        }
        if (complaintDTO.getComplaintType() != null && !complaintDTO.getComplaintType().isEmpty()) {
            predicates.add(cb.equal(root.get("complaintType"), complaintDTO.getComplaintType()));
        }
        if (complaintDTO.getCity() != null && !complaintDTO.getCity().isEmpty()) {
            predicates.add(cb.equal(root.get("city"), complaintDTO.getCity()));
        }
        if (complaintDTO.getStatus() != null && !complaintDTO.getStatus().isEmpty()) {
            predicates.add(cb.equal(root.get("status"), complaintDTO.getStatus()));
        }
        if (complaintDTO.getCountry() != null && !complaintDTO.getCountry().isEmpty()) {
            predicates.add(cb.equal(root.get("country"), complaintDTO.getCountry()));
        }
        if (complaintDTO.getState() != null && !complaintDTO.getState().isEmpty()) {
            predicates.add(cb.equal(root.get("state"), complaintDTO.getState()));
        }
        if (complaintDTO.getAddress() != null && !complaintDTO.getAddress().isEmpty()) {
            predicates.add(cb.like(root.get("address"), "%" + complaintDTO.getAddress() + "%"));
        }
        if (complaintDTO.getDeptId() != null) {
            predicates.add(cb.equal(root.get("deptId"), complaintDTO.getDeptId()));
        }
        if (complaintDTO.getEmpId() != null) {
            predicates.add(cb.equal(root.get("empId"), complaintDTO.getEmpId()));
        }
        if (complaintDTO.getDescription() != null && !complaintDTO.getDescription().isEmpty()) {
            predicates.add(cb.like(root.get("description"), "%" + complaintDTO.getDescription() + "%"));
        }
        if (complaintDTO.getCreatedDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate"), complaintDTO.getCreatedDate()));
        }
        return predicates;
    }

    private List<Predicate> getPredicates(ComplaintDTO complaintDTO, CriteriaBuilder cb, Root<ComplaintDTO> root) {

        List<Predicate> predicates = new ArrayList<>();

        if (complaintDTO.getId() != null) {
            predicates.add(cb.equal(root.get("id"), complaintDTO.getId()));
        }
        if (complaintDTO.getComplaintType() != null && !complaintDTO.getComplaintType().isEmpty()) {
            predicates.add(cb.equal(root.get("complaintType"), complaintDTO.getComplaintType()));
        }
        if (complaintDTO.getCity() != null && !complaintDTO.getCity().isEmpty()) {
            predicates.add(cb.equal(root.get("city"), complaintDTO.getCity()));
        }
        if (complaintDTO.getStatus() != null && !complaintDTO.getStatus().isEmpty()) {
            predicates.add(cb.equal(root.get("status"), complaintDTO.getStatus()));
        }
        if (complaintDTO.getCountry() != null && !complaintDTO.getCountry().isEmpty()) {
            predicates.add(cb.equal(root.get("country"), complaintDTO.getCountry()));
        }
        if (complaintDTO.getState() != null && !complaintDTO.getState().isEmpty()) {
            predicates.add(cb.equal(root.get("state"), complaintDTO.getState()));
        }
        if (complaintDTO.getAddress() != null && !complaintDTO.getAddress().isEmpty()) {
            predicates.add(cb.like(root.get("address"), "%" + complaintDTO.getAddress() + "%"));
        }
        if (complaintDTO.getEmpId() != null) {
            predicates.add(cb.equal(root.get("empId"), complaintDTO.getEmpId()));
        }
        if (complaintDTO.getDescription() != null && !complaintDTO.getDescription().isEmpty()) {
            predicates.add(cb.like(root.get("description"), "%" + complaintDTO.getDescription() + "%"));
        }
        if (complaintDTO.getCreatedDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate"), complaintDTO.getCreatedDate()));
        }

        return predicates;
    }


}
