package com.xworkz.repository;

import com.xworkz.entity.ImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;


@Repository
public class ImageRepositoryImpl implements ImageRepository{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean save(ImageDTO imageDTO) {
        System.out.println("Image Repository save process is initiated using dto."+ imageDTO);

        try {
            entityManager.persist(imageDTO);
            return true;

        }catch(PersistenceException e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ImageDTO>> getImagesByUserId(Long userId) {
        System.out.println("Image Repository find by User ID process is initiated using userid."+ userId);

        try {

            Query query = entityManager.createNamedQuery("findImagesByUserID");
            query.setParameter("userId",userId.intValue());
            List<ImageDTO> imageDTOList = (List<ImageDTO>) query.getResultList();
            return Optional.ofNullable(imageDTOList);

        }catch(PersistenceException e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean updateImageActiveByUserId(List<ImageDTO> imageDTOList) {
        System.out.println("Image Repository update Image Active By User Id process is initiated using dto list."+ imageDTOList);

        try {
            for (ImageDTO imageDTO: imageDTOList){
                Query query = entityManager.createNamedQuery("updateImageActive");
                query.setParameter("active",imageDTO.isActive());
                query.setParameter("userId",imageDTO.getUserId());
                query.executeUpdate();
            }
            return true;

        }catch(PersistenceException e){
            e.printStackTrace();
            throw e;
        }
    }
}
