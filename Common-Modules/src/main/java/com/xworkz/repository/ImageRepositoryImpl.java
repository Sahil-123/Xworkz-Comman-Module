package com.xworkz.repository;

import com.xworkz.dto.ImageDTO;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;


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
}
