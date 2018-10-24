package com.iisoft.unquitube.backend.repository;


import com.iisoft.unquitube.backend.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<UserDTO, Serializable>{
    public abstract UserDTO findById(Integer id);
    public abstract List<UserDTO> findByName(String name);
    public abstract List<UserDTO> findByAge(Integer age);
    public abstract List<UserDTO> findByNameAndAge(String name, Integer age);
}
