package com.iisoft.unquitube.backend.service;

import com.iisoft.unquitube.backend.converter.UserConverter;
import com.iisoft.unquitube.backend.dto.UserDTO;
import com.iisoft.unquitube.backend.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iisoft.unquitube.backend.repository.UserRepository;

import java.util.List;

@Service("UserService")
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserConverter converter;

    public static final Log logger = LogFactory.getLog(UserService.class);

    public boolean create(UserDTO user){
        try{
            repository.save(user);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean update(UserDTO user){
        try{
            repository.save(user);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean delete(Integer id){
        try{
            UserDTO userDTO = repository.findById(id);
            repository.delete(userDTO);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public List<User> get(){
        logger.info("Getting users");
        try {
            List<UserDTO> userDTOS = repository.findAll();
            logger.info("Complete!");
            return converter.convertUsers(userDTOS);
        }catch (Exception e) {
            logger.info("Error getting users: " + e.getMessage());
            return null;
        }
    }

    public User findById(Integer id){
        return new User(repository.findById(id));
    }


}
