package com.xworkz.repository;

import com.xworkz.dto.DTOListPage;
import com.xworkz.entity.UserDTO;
import com.xworkz.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private Long getCount(){
        System.out.println("User Repository getting count.");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Query queryTotal = entityManager.createQuery
                    ("Select count(f.id) from UserDTO f");

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

    @Override
    public DTOListPage<UserDTO> getAllUsers(Integer offset, Integer pageSize) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            Long count = getCount();
            if(offset < 1) offset = 1;
            Query query = entityManager.createQuery("select user from UserDTO user",UserDTO.class);
            query.setFirstResult((offset-1)* pageSize);
            query.setMaxResults(pageSize);

            List<UserDTO> userDTOList = query.getResultList();

            return new DTOListPage<UserDTO>(count,Optional.ofNullable(userDTOList));
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        finally {
            entityManager.close();
        }

        return new DTOListPage<UserDTO>(0L,Optional.empty());
    }

    @Override
    public boolean merge(UserDTO userDTO) {
        System.out.println("User Repository merge process is initiated using dto."+ userDTO);

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

    @Override
    public boolean checkMobile(String mobile) {
        System.out.println("User Repository check mobile number process is initiated using number."+ mobile);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            String query = "SELECT COUNT(u) FROM UserDTO u WHERE u.mobile = :mobile";
            Long count = (Long) entityManager.createQuery(query)
                    .setParameter("mobile", mobile)
                    .getSingleResult();

            return count > 0;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        finally {
            entityManager.close();
        }
        return true;
    }

    @Override
    public boolean checkEmail(String email) {

        System.out.println("User Repository check email process is initiated using email."+ email);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            String query = "SELECT COUNT(u) FROM UserDTO u WHERE u.email = :email";
            Long count = (Long) entityManager.createQuery(query)
                    .setParameter("email", email)
                    .getSingleResult();

            return count > 0;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        finally {
            entityManager.close();
        }
        return true;
    }


    @Override
    public Optional<UserDTO> findById(Long id) {
        System.out.println("User Repository find by ID process is initiated using ID." + id);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            UserDTO userDTO = entityManager.find(UserDTO.class, id);
            return Optional.ofNullable(userDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return Optional.empty();
    }
}
