package com.iisoft.unquitube.backend.service;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import com.iisoft.unquitube.backend.dto.ChannelDTO;
import com.iisoft.unquitube.backend.dto.VideoDTO;
import com.iisoft.unquitube.backend.repository.ChannelRepository;

public class ChannelServiceTest {
	
	@Mock
	private ChannelRepository channelRepository;
	private ChannelService channelService;
	
	@Before
	public void setUp() {
		this.channelRepository = mock(ChannelRepository.class);
		this.channelService = new ChannelService(this.channelRepository);
	}

	@Test
	public void GIVEN_a_request_to_get_all_channels_WHEN_searching_then_THEN_should_return_all_channels() {
		List<ChannelDTO> channels = Arrays.asList(new ChannelDTO(), new ChannelDTO());
		
		when(this.channelRepository.findAll()).thenReturn(channels);
		
		List<ChannelDTO> recoveredChannels = this.channelService.getAllChannels();
		
		for (ChannelDTO channelDTO : recoveredChannels)
			assertTrue(channels.contains(channelDTO));
	}
	
	@Test
	public void GIVEN_a_request_to_get_a_channel_WHEN_searching_it_THEN_should_return_requested_channel() {
		ChannelDTO requestedChannel = new ChannelDTO();
		
		when(this.channelRepository.findById(10)).thenReturn(Optional.of(requestedChannel));
		
		ChannelDTO recoveredChannel = this.channelService.getChannel(10);
		
		assertEquals(requestedChannel, recoveredChannel);
	}
	
	@Test
	public void GIVEN_a_channel_to_save_WHEN_saving_it_THEN_should_return_saved_channel() {
		ChannelDTO newChannel = new ChannelDTO();
		
		when(this.channelRepository.save(newChannel)).thenReturn(newChannel);
		
		ChannelDTO savedChannel = this.channelService.saveChannel(newChannel);
		
		assertEquals(newChannel, savedChannel);
	}
	
	@Test
	public void GIVEN_a_video_to_save_WHEN_saving_it_in_his_corresponding_channel_THEN_should_return_saved_channel_with_new_video() {
		ChannelDTO aChannel = new ChannelDTO();
		aChannel.setId(10);
		VideoDTO newVideo = new VideoDTO();
		newVideo.setTitle("a title");
		newVideo.setUrl("an url");
		
		aChannel.getPlaylist().add(newVideo);
		when(this.channelRepository.findById(10)).thenReturn(Optional.of(aChannel));
		
		ChannelDTO savedChannel = this.channelService.addVideoToChannel(newVideo, 10);
		
		assertEquals(savedChannel.getPlaylist().iterator().next(), newVideo);
	}
	
	@Test(expected=RuntimeException.class)
	public void GIVEN_a_video_to_save_WHEN_trying_to_save_it_to_an_unexisting_channel_THEN_should_throw_exception() {
		VideoDTO newVideo = new VideoDTO();
		newVideo.setTitle("a title");
		newVideo.setUrl("an url");
		
		when(this.channelRepository.findById(10)).thenReturn(Optional.ofNullable(null));
		
		this.channelService.addVideoToChannel(newVideo, 10);
	}
	
	@Test
	public void GIVEN_an_id_of_channel_WHEN_this_exist_and_is_deleted_THEN_should_return_true() {
		when(this.channelRepository.existsById(10)).thenReturn(true);
		
		boolean wasDeleted = this.channelService.deleteChannel(10);
		
		assertTrue(wasDeleted);
	}
	
	@Test
	public void GIVEN_an_id_of_channel_WHEN_this_doesnt_exist_tryes_to_deleted_THEN_should_return_false() {
		when(this.channelRepository.existsById(10)).thenReturn(false);
		
		boolean wasDeleted = this.channelService.deleteChannel(10);
		
		assertFalse(wasDeleted);
	}

	@Test
	public void GIVEN_a_request_to_get_channels_by_tags_WHEN_searching_it_THEN_should_return_requested_channels() {
		Set<String> serchedTags = new HashSet<String>();
		serchedTags.add("jazz");
		Set<ChannelDTO> requestedChannels = new HashSet<ChannelDTO>();
		requestedChannels.add(new ChannelDTO());

		when(this.channelRepository.findByTagsIn(serchedTags)).thenReturn(requestedChannels);

		Set<ChannelDTO> recoveredChannel = this.channelService.getChannelsByTag(serchedTags);

		assertEquals(requestedChannels, recoveredChannel);
	}

}
