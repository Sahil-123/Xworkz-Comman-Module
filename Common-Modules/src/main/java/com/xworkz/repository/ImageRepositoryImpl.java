package com.xworkz.repository;

import com.xworkz.dto.ImageDTO;
import com.xworkz.dto.UserDTO;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;


@Repository
public class ImageRepositoryImpl implements ImageRepository{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public boolean save(ImageDTO imageDTO) {
        System.out.println("Image Repository save process is initiated using dto."+ imageDTO);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(imageDTO);
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
    public Optional<List<ImageDTO>> getImagesByUserId(Long userId) {
        System.out.println("Image Repository find by User ID process is initiated using userid."+ userId);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {

            Query query = entityManager.createNamedQuery("findImagesByUserID");
            query.setParameter("userId",userId.intValue());
            List<ImageDTO> imageDTOList = (List<ImageDTO>) query.getResultList();
            return Optional.ofNullable(imageDTOList);

        }catch(PersistenceException e){
            e.printStackTrace();
        }
        finally {
            entityManager.close();
        }

        return Optional.empty();
    }

    @Override
    public boolean updateImageActiveByUserId(List<ImageDTO> imageDTOList) {
        System.out.println("Image Repository update Image Active By User Id process is initiated using dto list."+ imageDTOList);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            for (ImageDTO imageDTO: imageDTOList){
                Query query = entityManager.createNamedQuery("updateImageActive");
                query.setParameter("active",imageDTO.isActive());
                query.setParameter("userId",imageDTO.getUserId());
                query.executeUpdate();
            }
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
