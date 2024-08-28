package com.xworkz.repository;

import com.xworkz.dto.DTOListPage;
import com.xworkz.entity.ComplaintDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.requestDto.RequestUpdateComplaintDTO;
import com.xworkz.utils.CommonUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ComplaintRepositoryImpl implements ComplaintRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Boolean save(ComplaintDTO complaintDTO) {
        System.out.println("Complaint Repository save process is initiated using dto." + complaintDTO);

        try {
            entityManager.persist(complaintDTO);
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw new InfoException("Something went wrong data not saved successfully.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ComplaintDTO>> findAll() {

        try {
            Query query = entityManager.createQuery("SELECT c FROM ComplaintDTO c order by c.createdDate desc", ComplaintDTO.class);
            List<ComplaintDTO> complaintList = query.getResultList();
            return Optional.ofNullable(complaintList);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ComplaintDTO> findById(Long id) {

        try {
            ComplaintDTO complaintDTO = entityManager.find(ComplaintDTO.class, id);
            return Optional.ofNullable(complaintDTO);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public Boolean update(ComplaintDTO complaintDTO) {
        System.out.println("Complaint Repository update process is initiated using dto." + complaintDTO);

        try {
            entityManager.merge(complaintDTO);
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        System.out.println("Complaint Repository delete process is initiated using id." + id);

        try {
            ComplaintDTO complaintDTO = entityManager.find(ComplaintDTO.class, id);
            if (complaintDTO != null) {
                entityManager.remove(complaintDTO);
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
    public DTOListPage<ComplaintDTO> searchComplaints(ComplaintDTO complaintDTO, Integer offset, Integer pageSize) {
        System.out.println("Search Complaint in repository processing " + complaintDTO);

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

            return new DTOListPage<ComplaintDTO>(count, Optional.ofNullable(results));

        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
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
    @Transactional(readOnly = true)
    public DTOListPage<ComplaintDTO> searchAllComplaintsForAdmin(ComplaintDTO complaintDTO, Integer offset, Integer pageSize) {
        System.out.println("Complaint in repository processing for admin " + complaintDTO);

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ComplaintDTO> query = cb.createQuery(ComplaintDTO.class);
            Root<ComplaintDTO> root = query.from(ComplaintDTO.class);

            List<Predicate> predicates = getPredicatesList(complaintDTO, cb, root);

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            query.orderBy(cb.desc(root.get("createdDate")));


            Query query1 = entityManager.createQuery(query);
            query1.setFirstResult(CommonUtils.getFirstResultForPagination(offset, pageSize));
            query1.setMaxResults(pageSize);
            List<ComplaintDTO> results = query1.getResultList();

            Query query2 = entityManager.createQuery(query);
            Long count = (long) query2.getResultList().size();

            return new DTOListPage<ComplaintDTO>(count, Optional.ofNullable(results));

        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ComplaintDTO>> searchAllComplaintsForAdmin(ComplaintDTO complaintDTO) {
        System.out.println("Complaint in repository processing for  " + complaintDTO);

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ComplaintDTO> query = cb.createQuery(ComplaintDTO.class);
            Root<ComplaintDTO> root = query.from(ComplaintDTO.class);

            List<Predicate> predicates = getPredicatesList(complaintDTO, cb, root);

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            query.orderBy(cb.desc(root.get("createdDate")));

            Query query1 = entityManager.createQuery(query);
            List<ComplaintDTO> results = query1.getResultList();

            return Optional.ofNullable(results);

        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Optional<List<ComplaintDTO>> searchAllComplaintsForNotResolved(ComplaintDTO complaintDTO) {
        System.out.println("Complaint in repository processing for admin for not resolved complaints " + complaintDTO);

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
            query.orderBy(cb.desc(root.get("createdDate")));

            List<ComplaintDTO> results = entityManager.createQuery(query).getResultList();
            return Optional.ofNullable(results);

        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DTOListPage<ComplaintDTO> searchAllComplaintsForNotResolved(ComplaintDTO complaintDTO, Integer offset, Integer pageSize) {
        System.out.println("Complaint in repository processing for admin for not resolved complaints " + complaintDTO);

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
            query.orderBy(cb.desc(root.get("createdDate")));

            Query query1 = entityManager.createQuery(query);
            query1.setFirstResult(CommonUtils.getFirstResultForPagination(offset, pageSize));
            query1.setMaxResults(pageSize);
            List<ComplaintDTO> results = query1.getResultList();

            Query query2 = entityManager.createQuery(query);
            Long count = (long) query2.getResultList().size();

            return new DTOListPage<ComplaintDTO>(count, Optional.ofNullable(results));

        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Optional<List<ComplaintDTO>> searchAllComplaintsForResolved(ComplaintDTO complaintDTO) {
        System.out.println("Complaint in repository processing for admin for resolved complaints " + complaintDTO);

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
            query.orderBy(cb.desc(root.get("createdDate")));

            List<ComplaintDTO> results = entityManager.createQuery(query).getResultList();
            return Optional.ofNullable(results);

        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DTOListPage<ComplaintDTO> searchAllComplaintsForResolved(ComplaintDTO complaintDTO, Integer offset, Integer pageSize) {
        System.out.println("Complaint in repository processing for admin for resolved complaints " + complaintDTO);

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
            query.orderBy(cb.desc(root.get("createdDate")));

            Query query1 = entityManager.createQuery(query);
            query1.setFirstResult(CommonUtils.getFirstResultForPagination(offset, pageSize));
            query1.setMaxResults(pageSize);
            List<ComplaintDTO> results = query1.getResultList();

            Query query2 = entityManager.createQuery(query);
            Long count = (long) query2.getResultList().size();

            return new DTOListPage<>(count, Optional.ofNullable(results));

        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ComplaintDTO>> findAdminComplaintsInNotification() {

        System.out.println("Complaint repo getting admin complaints in repo.");

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ComplaintDTO> query = cb.createQuery(ComplaintDTO.class);
            Root<ComplaintDTO> root = query.from(ComplaintDTO.class);

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isNull(root.get("deptId")));
            predicates.add(cb.isNull(root.get("empId")));

            query.where(cb.and(predicates.toArray(new Predicate[0])));

            List<ComplaintDTO> complaintDTOList = entityManager.createQuery(query).getResultList();

            return Optional.ofNullable(complaintDTOList);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ComplaintDTO>> findDeptAdminComplaintsInNotification(Long deptId) {
        System.out.println("Complaint repo getting dept admin complaints in repo. "+deptId);

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ComplaintDTO> query = cb.createQuery(ComplaintDTO.class);
            Root<ComplaintDTO> root = query.from(ComplaintDTO.class);

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("deptId"), deptId));
            predicates.add(cb.isNull(root.get("empId")));

            query.where(cb.and(predicates.toArray(new Predicate[0])));

            List<ComplaintDTO> complaintDTOList = entityManager.createQuery(query).getResultList();

            return Optional.ofNullable(complaintDTOList);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ComplaintDTO>> findUserComplaintsInNotification(Long empId, Long deptId) {
        System.out.println("Complaint repo getting employee complaints in repo. "+empId+" "+deptId);

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ComplaintDTO> query = cb.createQuery(ComplaintDTO.class);
            Root<ComplaintDTO> root = query.from(ComplaintDTO.class);

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("deptId"), deptId));
            predicates.add(cb.equal(root.get("empId"), empId));
            predicates.add(cb.like(root.get("status"), "%"+CommonUtils.IN_PROGRESS+"%"));

//            predicates.add(cb.isNull(root.get("empId")));

            query.where(cb.and(predicates.toArray(new Predicate[0])));

            List<ComplaintDTO> complaintDTOList = entityManager.createQuery(query).getResultList();

            return Optional.ofNullable(complaintDTOList);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public List<ComplaintDTO> getAllOTPClearingComplaint() {
        try{
            Query query= entityManager.createQuery("select complaint from ComplaintDTO complaint where complaint.otp is not null or complaint.otptime is not null");
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    @Transactional
    public Boolean updateComplaint(RequestUpdateComplaintDTO requestUpdateComplaintDTO) {

        System.out.println("Repository update complaint " + requestUpdateComplaintDTO);

        try {
//            SELECT c FROM ComplaintDTO c WHERE c.id = :id
            Query query = entityManager.createQuery("update ComplaintDTO c set c.description = : description where c.id=:id");
            query.setParameter("description", requestUpdateComplaintDTO.getDescription());
            query.setParameter("id", requestUpdateComplaintDTO.getComplaintId());
            int count = query.executeUpdate();

            if (count > 0) {
                return true;
            }

            return false;

        } catch (PersistenceException e) {
            e.printStackTrace();
            throw new InfoException("Update Operation failed.");
        }

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
