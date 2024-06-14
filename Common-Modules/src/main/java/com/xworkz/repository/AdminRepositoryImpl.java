package com.xworkz.repository;

import com.xworkz.dto.AdminDTO;
import com.xworkz.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class AdminRepositoryImpl implements AdminRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<List<AdminDTO>> findByEmail(String email) {
        System.out.println("Admin Repository find by email process is initiated using mail."+ email);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {

            Query query = entityManager.createNamedQuery("findByAdminEmail");
            query.setParameter("email",email);
            List<AdminDTO> adminDTOList = (List<AdminDTO>) query.getResultList();
            return Optional.ofNullable(adminDTOList);

        }catch(PersistenceException e){
            e.printStackTrace();
        }
        finally {
            entityManager.close();
        }

        return Optional.empty();
    }
}
