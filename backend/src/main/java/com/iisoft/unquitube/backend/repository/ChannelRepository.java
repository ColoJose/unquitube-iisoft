package com.iisoft.unquitube.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iisoft.unquitube.backend.dto.ChannelDTO;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelDTO, Integer> {	
}
