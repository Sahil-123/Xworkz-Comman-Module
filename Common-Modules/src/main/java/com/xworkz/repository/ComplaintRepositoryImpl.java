package com.xworkz.repository;

import com.xworkz.dto.ComplaintDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.requestDto.RequestUpdateComplaintDTO;
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
    public Optional<List<ComplaintDTO>> searchComplaints(ComplaintDTO complaintDTO) {
        System.out.println("Complaint in repository processing " + complaintDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ComplaintDTO> query = cb.createQuery(ComplaintDTO.class);
            Root<ComplaintDTO> root = query.from(ComplaintDTO.class);

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
            if (complaintDTO.getDescription() != null && !complaintDTO.getDescription().isEmpty()) {
                predicates.add(cb.like(root.get("description"), "%" + complaintDTO.getDescription() + "%"));
            }
            if (complaintDTO.getCreatedDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate"), complaintDTO.getCreatedDate()));
            }

            predicates.add(cb.equal(root.get("userId"), complaintDTO.getUserId()));

            query.where(cb.and(predicates.toArray(new Predicate[0])));

            System.out.println("Query " + query);

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
    public Optional<List<ComplaintDTO>> searchAllComplaintsForAdmin(ComplaintDTO complaintDTO) {
        System.out.println("Complaint in repository processing for admin " + complaintDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ComplaintDTO> query = cb.createQuery(ComplaintDTO.class);
            Root<ComplaintDTO> root = query.from(ComplaintDTO.class);

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
            if (complaintDTO.getDescription() != null && !complaintDTO.getDescription().isEmpty()) {
                predicates.add(cb.like(root.get("description"), "%" + complaintDTO.getDescription() + "%"));
            }
            if (complaintDTO.getCreatedDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate"), complaintDTO.getCreatedDate()));
            }


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
    public Boolean updateComplaint(RequestUpdateComplaintDTO requestUpdateComplaintDTO) {

        System.out.println("Repository update complaint "+ requestUpdateComplaintDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
//            SELECT c FROM ComplaintDTO c WHERE c.id = :id
            Query query = entityManager.createQuery("update ComplaintDTO c set c.description = : description where c.id=:id");
            query.setParameter("description",requestUpdateComplaintDTO.getDescription());
            query.setParameter("id",requestUpdateComplaintDTO.getComplaintId());
            int count = query.executeUpdate();
            entityTransaction.commit();

            if(count>0){
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
}
