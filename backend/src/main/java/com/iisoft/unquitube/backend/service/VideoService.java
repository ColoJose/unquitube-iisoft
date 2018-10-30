package com.iisoft.unquitube.backend.service;

import com.iisoft.unquitube.backend.converter.VideoConverter;
import com.iisoft.unquitube.backend.dto.VideoDTO;
import com.iisoft.unquitube.backend.model.Video;
import com.iisoft.unquitube.backend.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

@Service("VideoService")
public class VideoService {
    @Autowired
    private VideoRepository repository;

    @Autowired
    private VideoConverter converter;

    private static final Log logger = LogFactory.getLog(VideoService.class);

    public boolean create(VideoDTO videoDTO){
        logger.info("Save video");
        try {
            repository.save(videoDTO);
            logger.info("Complete!");
            return true;
        }catch (Exception e){
            logger.info("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean update(VideoDTO user){
        logger.info("Updating video");
        try{
            repository.save(user);
            logger.info("Complete!");
            return true;
        } catch (Exception e){
            logger.info("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(Integer id){
        logger.info("Removing video");
        try{
            VideoDTO videoDTO = repository.findById(id);
            repository.delete(videoDTO);
            logger.info("Complete!");
            return true;
        } catch (Exception e){
            logger.info("Error: " + e.getMessage());
            return false;
        }
    }

    public List<Video> get(){
        logger.info("Getting videos");
        try {
            List<VideoDTO> videoDTOS = repository.findAll();
            logger.info("Complete!");
            return converter.convertUsers(videoDTOS);
        }catch (Exception e) {
            logger.info("Error: " + e.getMessage());
            return null;
        }
    }

    public Video findById(Integer id){
        return new Video(repository.findById(id));
    }

}
