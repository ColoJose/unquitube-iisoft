package com.iisoft.unquitube.backend.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iisoft.unquitube.backend.dto.ChannelDTO;
import com.iisoft.unquitube.backend.dto.VideoDTO;
import com.iisoft.unquitube.backend.service.ChannelService;

@RestController
@RequestMapping("channel/")
public class ChannelController {
	
	private ChannelService channelService;
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	public ChannelController(ChannelService channelService) {
		this.channelService = channelService;
	}

	@RequestMapping(path="{channelId}/video", method=RequestMethod.POST)
	public ResponseEntity<?> saveVideo(@RequestBody VideoDTO newVideo, @PathVariable Integer channelId) {
		try {
			logger.info("saveVideo - request to save new video for channel with id=" + channelId + ". Received video=" + newVideo);

			ChannelDTO updatedChannel = this.channelService.addVideoToChannel(newVideo, channelId);
			
			logger.info("saveVideo - channel updated");
			return ResponseEntity.ok().body(updatedChannel);
		}
		catch(Exception e) {
			logger.error("saveVideo - error while trying to save a new video", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@RequestMapping(path="{channelId}", method=RequestMethod.GET)
	public ResponseEntity<?> getChannelVideo(@PathVariable Integer channelId) {
		try {
			logger.info("getChannel - request channel with id=" + channelId + ".");

			ChannelDTO updatedChannel = this.channelService.getChannel(channelId);
			
			logger.info("getChannel - channel succesfully retrieved");
			return ResponseEntity.ok().body(updatedChannel);
		}
		catch(Exception e) {
			logger.error("getChannel - error while trying to get a channel", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
}
