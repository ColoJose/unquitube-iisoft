package com.iisoft.unquitube.backend.converter;

import com.iisoft.unquitube.backend.dto.UserDTO;
import com.iisoft.unquitube.backend.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("UserConverter")
public class UserConverter {
    public List<User> convertUsers(List<UserDTO> userDTOS){
        List<User> users = new ArrayList<>();

        userDTOS.forEach(u -> users.add(new User(u)));

        return users;
    }
}
