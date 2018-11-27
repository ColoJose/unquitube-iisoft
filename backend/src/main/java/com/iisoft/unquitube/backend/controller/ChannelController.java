package com.iisoft.unquitube.backend.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iisoft.unquitube.backend.dto.ChannelDTO;
import com.iisoft.unquitube.backend.dto.VideoDTO;
import com.iisoft.unquitube.backend.service.ChannelService;

import javax.xml.ws.Response;

@RestController
@RequestMapping("channel/")
@CrossOrigin(allowCredentials = "false")
public class ChannelController {
	
	private ChannelService channelService;
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	public ChannelController(ChannelService channelService) {
		this.channelService = channelService;
	}
	
	
	@RequestMapping(path="", method=RequestMethod.GET)
	public ResponseEntity<?> getAllChannels(){
		try {
			logger.info("getAllChannels - request to retrieve all channels");

			List<ChannelDTO> channels = this.channelService.getAllChannels();
			
			logger.info("getAllChannels - channel succesfully retrieved");
			return ResponseEntity.ok().body(channels);
		}
		catch(Exception e) {
			logger.error("getAllChannels - error while trying to get all channels", e);
			return ResponseEntity.badRequest().build();
		}
		
	}
	
	@RequestMapping(path="{channelId}", method=RequestMethod.GET)
	public ResponseEntity<?> getChannel(@PathVariable Integer channelId) {
		try {
			logger.info("getChannel - request channel with id=" + channelId + ".");

			ChannelDTO updatedChannel = this.channelService.getChannel(channelId);
			
			HttpStatus status = null;
			if (updatedChannel == null)
				status = HttpStatus.NOT_FOUND;
			else
				status = HttpStatus.OK;
			
			logger.info("getChannel - channel succesfully retrieved");
			return ResponseEntity.status(status).body(updatedChannel);
		}
		catch(Exception e) {
			logger.error("getChannel - error while trying to get a channel", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@RequestMapping(path="{channelId}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteChannel(@PathVariable Integer channelId) {
		try {
			logger.info("deleteChannel - request to delete the channel with id=" + channelId + ".");

			boolean wasDeleted = this.channelService.deleteChannel(channelId);
			
			logger.info("deleteChannel - channel succesfully deleted");
			
			HttpStatus status = wasDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND;
			
			return ResponseEntity.status(status).build();
		}
		catch(Exception e) {
			logger.error("deleteChannel - error while trying to get a channel", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@RequestMapping(path="", method=RequestMethod.POST)
	public ResponseEntity<?> saveChannel(@RequestBody ChannelDTO newChannel) {
		try {
			logger.info("saveChannel - request to save new channel. Received channel=" + newChannel);
			ChannelDTO savedChannel = this.channelService.saveChannel(newChannel);
			logger.info("saveChannel - channel saved");
			return ResponseEntity.status(HttpStatus.CREATED).body(savedChannel);
		}
		catch(Exception e) {
			logger.error("saveChannel - error while trying to save a new channel", e);
			return ResponseEntity.badRequest().build();
		}
	}

	@RequestMapping(path="update", method=RequestMethod.PUT)
	public ResponseEntity<?> updateChannel(@RequestBody ChannelDTO newChannel){
		try{
			logger.info("updateChannel - request to update channel. Received channel= " + newChannel);
			ChannelDTO updateChannel = this.channelService.updateChannel(newChannel);
			logger.info("updateChannel - channel updated");
			return ResponseEntity.status(HttpStatus.OK).body(updateChannel);
		} catch (Exception e) {
			logger.error("updateChannel - error while trying to update channel");
			return ResponseEntity.badRequest().build();
		}
	}

	@RequestMapping(path = "{channelId}/add", method = RequestMethod.PUT)
	public ResponseEntity<?> addViewer(@PathVariable Integer channelId){
		try{
			logger.info("addViewer - request to add viewer to the channel. Received channelId = " + channelId);
			ChannelDTO channelDTO = this.channelService.getChannel(channelId);
			channelDTO.addViewer();
			this.channelService.updateChannel(channelDTO);
			logger.info("addViewer - channel viewer added");
			return ResponseEntity.status(HttpStatus.OK).body(channelDTO);
		} catch (Exception e){
			logger.error("addViewer - error while trying to add viewer to the channel. Channel id = " + channelId,e);
			return ResponseEntity.badRequest().build();
		}
	}

	@RequestMapping(path = "{channelId}/del", method = RequestMethod.PUT)
	public ResponseEntity<?> delViewer(@PathVariable Integer channelId){
		try{
			logger.info("delViewer - request to delete viewer to the channel. Received channelId = " + channelId);
			ChannelDTO channelDTO = this.channelService.getChannel(channelId);
			channelDTO.delViewer();
			this.channelService.updateChannel(channelDTO);
			logger.info("delViewer - channel viewer deleted");
			return ResponseEntity.status(HttpStatus.OK).body(channelDTO);
		} catch (Exception e){
			logger.error("delViewer - error while trying to delete viewer to the channel. Channel id = " + channelId, e);
			return ResponseEntity.badRequest().build();
		}
	}

	@RequestMapping(path="{channelId}/video", method=RequestMethod.POST)
	public ResponseEntity<?> saveVideo(@RequestBody VideoDTO newVideo, @PathVariable Integer channelId) {
		try {
			logger.info("saveVideo - request to save new video for channel with id=" + channelId + ". Received video=" + newVideo);

			ChannelDTO updatedChannel = this.channelService.addVideoToChannel(newVideo, channelId);
			
			logger.info("saveVideo - channel updated");
			return ResponseEntity.status(HttpStatus.CREATED).body(updatedChannel);
		}
		catch(Exception e) {
			logger.error("saveVideo - error while trying to save a new video", e);
			return ResponseEntity.badRequest().build();
		}
	}

	@RequestMapping(path = "search/{tags}", method = RequestMethod.GET)
	public ResponseEntity<?> getChannelsByTag(@PathVariable String tags){
		try {
			logger.info("getChannelsByTag - request to retrieve channels by tags. tags = " + tags);
			String[] tagsList = tags.split("!");
			Set<String> tagsSet = new HashSet<>(Arrays.asList(tagsList));
			Set<ChannelDTO> filteredChannels = this.channelService.getChannelsByTag(tagsSet);

			Set<ChannelDTO> result = filteredChannels.stream()
														.filter(c -> c.getTags().containsAll(tagsSet))
														.collect(Collectors.toSet());

			logger.info("getChannelsByTag - channels retrieved. Founded " + result.size() + " channels");
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}catch (Exception e){
			logger.error("getChannelsByTag - error while trying to search a video", e);
			return ResponseEntity.badRequest().build();
		}
	}

}
