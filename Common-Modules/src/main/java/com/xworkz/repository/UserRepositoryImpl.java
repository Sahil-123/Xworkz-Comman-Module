package com.xworkz.repository;

import com.xworkz.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Boolean save(UserDTO userDTO) {
        System.out.println("User Repository save process is initiated using dto."+ userDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(userDTO);
            transaction.commit();
            return true;

        }catch(PersistenceException e){
            e.printStackTrace();
            transaction.rollback();
        }
        finally {
            entityManager.close();
        }

        return false;
    }

    @Override
    public Optional<List<UserDTO>> findByUserMail(String mail) {
        System.out.println("User Repository find by email process is initiated using mail."+ mail);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {

           Query query = entityManager.createNamedQuery("findByUserEmail");
           query.setParameter("email",mail);
           List<UserDTO> userDTOList = (List<UserDTO>) query.getResultList();
           return Optional.ofNullable(userDTOList);

        }catch(PersistenceException e){
            e.printStackTrace();
        }
        finally {
            entityManager.close();
        }

        return Optional.empty();
    }

    @Override
    public Optional<List<UserDTO>> findByUserMobile(String mobile) {
        System.out.println("User Repository find by mobile process is initiated using mobile."+ mobile);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {

            Query query = entityManager.createNamedQuery("findByUserMobile");
            query.setParameter("mobile",mobile);
            List<UserDTO> userDTOList = (List<UserDTO>) query.getResultList();
            return Optional.ofNullable(userDTOList);

        }catch(PersistenceException e){
            e.printStackTrace();
        }
        finally {
            entityManager.close();
        }

        return Optional.empty();
    }

    @Override
    public boolean updatePassword(UserDTO userDTO,String password) {
        System.out.println("User Repository update password process is initiated using dto and Password."+userDTO+", "+ password);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Query query=entityManager.createNamedQuery("updateUserPassword");
            query.setParameter("email",userDTO.getEmail());
            query.setParameter("password",password);
            query.setParameter("updatedBy",userDTO.getFname()+" "+userDTO.getLname());
            query.setParameter("updatedDate", LocalDateTime.now());
            query.setParameter("loginCount",userDTO.getLoginCount()+1);

            query.executeUpdate();
            transaction.commit();
            return true;

        }catch(PersistenceException e){
            e.printStackTrace();
        }
        finally {
            entityManager.close();
        }

        return false;
    }

    @Override
    public boolean updateByDto(UserDTO userDTO) {
        System.out.println("User Repository update by dto process is initiated using dto."+ userDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(userDTO);
            transaction.commit();
            return true;

        }catch(PersistenceException e){
            e.printStackTrace();
            transaction.rollback();
        }
        finally {
            entityManager.close();
        }

        return false;
    }
}
