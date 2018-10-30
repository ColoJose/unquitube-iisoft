package com.iisoft.unquitube.backend.repository;

import com.iisoft.unquitube.backend.dto.VideoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("VideoRepository")
public interface VideoRepository extends JpaRepository<VideoDTO, Serializable> {
    public abstract VideoDTO findById(Integer id);
    public abstract List<VideoDTO> findByName(String name);
}
