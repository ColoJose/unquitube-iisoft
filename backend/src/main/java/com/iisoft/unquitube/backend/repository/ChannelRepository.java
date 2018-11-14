package com.iisoft.unquitube.backend.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iisoft.unquitube.backend.dto.ChannelDTO;


@Repository("ChannelRepository")
public interface ChannelRepository extends JpaRepository<ChannelDTO, Integer> {
	public abstract List<ChannelDTO> findAll();

	/* --- FORMA NO VILLERA (No funciona, dice que no se manda un Set por parametro) ---

	@Query(value = "SELECT c FROM ChannelDTO c WHERE c.tags IN ?1")
	Set<ChannelDTO> findByTagsFiltered(Set<String> searchTags);
	*/


	Set<ChannelDTO> findByTagsIn(Set<String> SearchTags);

}
