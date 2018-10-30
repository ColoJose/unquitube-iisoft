package com.iisoft.unquitube.backend.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iisoft.unquitube.backend.dto.ChannelDTO;
import com.iisoft.unquitube.backend.dto.VideoDTO;
import com.iisoft.unquitube.backend.repository.ChannelRepository;

@Service
public class ChannelService {
	
	private ChannelRepository channelRepository;
	
	@Autowired
	public ChannelService(ChannelRepository channelRepository) {
		this.channelRepository = channelRepository;
	}

	@Transactional 
	public ChannelDTO addVideoToChannel(VideoDTO newVideo, Integer channelId) {		
		/* NOTA DE HIBERNATE: Todo pasa dentro de una transacción. 
		 * En este método no hace falta guardar el video explicitamente. 
		 * Cuando este método retorna Hibernate cierra la transacción y se encarga solo 
		 * de guardar el Channel con su nuevo video.
		 * */
		ChannelDTO foundedChannel = this.getChannel(channelId);
		
		foundedChannel.getPlaylist().add(newVideo);
		return foundedChannel;
	}

	public ChannelDTO getChannel(Integer channelId) {
		Optional<ChannelDTO> optionalChannel = this.channelRepository.findById(channelId);
		
		if (!optionalChannel.isPresent())
			throw new RuntimeException("channel with id=" + channelId + " was not found");
		
		ChannelDTO foundedChannel = optionalChannel.get();
		foundedChannel.getPlaylist().size(); // hack para traer lista lazy
		return foundedChannel;
	}
	
}
