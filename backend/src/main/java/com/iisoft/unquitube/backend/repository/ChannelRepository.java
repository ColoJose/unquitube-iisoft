package com.iisoft.unquitube.backend.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iisoft.unquitube.backend.dto.ChannelDTO;


@Repository("ChannelRepository")
public interface ChannelRepository extends JpaRepository<ChannelDTO, Integer> {
	public abstract List<ChannelDTO> findAll();

	Set<ChannelDTO> findByTagsIn(Set<String> SearchTags);
}
