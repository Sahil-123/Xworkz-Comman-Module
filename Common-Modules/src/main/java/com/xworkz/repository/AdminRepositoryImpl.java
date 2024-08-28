package com.xworkz.repository;

import com.xworkz.entity.AdminDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class AdminRepositoryImpl implements AdminRepository {

   @PersistenceContext
   private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<AdminDTO>> findByEmail(String email) {
        System.out.println("Admin Repository find by email process is initiated using mail."+ email);

        try {
            Query query = entityManager.createNamedQuery("findByAdminEmail");
            query.setParameter("email",email);
            List<AdminDTO> adminDTOList = (List<AdminDTO>) query.getResultList();
            return Optional.ofNullable(adminDTOList);

        }catch(PersistenceException e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean updateByDto(AdminDTO adminDTO) {
        System.out.println("Admin Repository update by dto process is initiated using dto."+ adminDTO);

        try {
            entityManager.merge(adminDTO);
            return true;

        }catch(PersistenceException e){
            e.printStackTrace();
            throw e;
        }
    }
}
