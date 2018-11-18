package com.iisoft.unquitube.backend.controller;

import org.junit.Before;

//import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.Matchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iisoft.unquitube.backend.dto.ChannelDTO;
import com.iisoft.unquitube.backend.dto.VideoDTO;
import com.iisoft.unquitube.backend.service.ChannelService;

import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ChannelController.class)
public class ChannelControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ChannelService service;

	private ObjectMapper mapper;

	@Before
	public void setUp() {
		this.mapper = new ObjectMapper();
	}

	// *******************
	// GET ALL CHANNELS
	// *******************

	@Test
	public void GIVEN_a_request_to_get_all_channels_WHEN_requesting_THEN_response_with_all_channels() throws Exception {
		List<ChannelDTO> channels = Arrays.asList(new ChannelDTO(), new ChannelDTO(), new ChannelDTO(),
				new ChannelDTO());
		when(this.service.getAllChannels()).thenReturn(channels);

		MvcResult response = this.mockMvc.perform(get("/channel/")).andDo(print()).andExpect(status().isOk())
				.andReturn();

		ChannelDTO[] recoveredChannels = this.mapper.readValue(response.getResponse().getContentAsString(),
				ChannelDTO[].class);

		for (ChannelDTO channelDTO : recoveredChannels)
			assertTrue(channels.contains(channelDTO));
	}

	@Test
	public void GIVEN_a_request_to_get_all_channels_WHEN_trying_to_resolve_request_unexpected_exeption_is_throw_THEN_response_with_bad_request()
			throws Exception {
		when(this.service.getAllChannels())
				.thenThrow(new RuntimeException("Test exception when trying to get all channels"));

		this.mockMvc.perform(get("/channel/")).andDo(print()).andExpect(status().isBadRequest()).andReturn();
	}

	// *******************
	// GET A CHANNEL
	// *******************

	@Test
	public void GIVEN_a_request_to_get_a_channel_WHEN_requesting_THEN_response_with_requested_channel()
			throws Exception {
		ChannelDTO requestedChannel = new ChannelDTO();
		when(this.service.getChannel(10)).thenReturn(requestedChannel);

		MvcResult response = this.mockMvc.perform(get("/channel/10")).andDo(print()).andExpect(status().isOk())
				.andReturn();

		ChannelDTO recoveredChannel = this.mapper.readValue(response.getResponse().getContentAsString(),
				ChannelDTO.class);

		assertEquals(requestedChannel, recoveredChannel);
	}

	@Test
	public void GIVEN_a_request_to_get_a_channel_WHEN_doesnt_exist_THEN_responde_not_found() throws Exception {
		when(this.service.getChannel(10)).thenReturn(null);

		this.mockMvc.perform(get("/channel/10")).andDo(print()).andExpect(status().isNotFound()).andReturn();

	}

	@Test
	public void GIVEN_a_request_to_get_a_channel_WHEN_trying_to_resolve_request_unexpected_exeption_is_throw_THEN_response_with_bad_request()
			throws Exception {
		when(this.service.getChannel(10))
				.thenThrow(new RuntimeException("Test exception when trying to get a channels"));

		this.mockMvc.perform(get("/channel/10")).andDo(print()).andExpect(status().isBadRequest()).andReturn();
	}

	// *********************
	// GET CHANNELS BY TAGS
	// *********************
	
	@Test
	public void GIVEN_a_tags_string_WHEN_requesting_channels_with_given_tags_THEN_response_with_a_list_of_channels() throws Exception {
		Set<ChannelDTO> channels = new HashSet<>();  
		
		for (int i=0; i<3; i++) {
			ChannelDTO channelToSave = new ChannelDTO();
			channelToSave.setName("Test Channel Name " + i);
			channelToSave.getTags().add("accion");
			channelToSave.getTags().add("metal");
			channels.add(channelToSave);
		}

		Set<String> tagsToSend = new HashSet<>();
		tagsToSend.add("accion");
		tagsToSend.add("metal");
		when(this.service.getChannelsByTag(tagsToSend)).thenAnswer(new Answer<Set<ChannelDTO>>() {

			@Override
			public Set<ChannelDTO> answer(InvocationOnMock arg0) throws Throwable {
				@SuppressWarnings("unchecked")
				Set<String> tags = (Set<String>)arg0.getArgument(0);
				assertTrue(tags.contains("accion"));
				assertTrue(tags.contains("metal"));
				return channels;
			}
			
		});

		MvcResult response = this.mockMvc
				.perform(get("/channel/search/accion!metal"))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		ChannelDTO[] channelsArray = this.mapper.readValue(response.getResponse().getContentAsString(), ChannelDTO[].class);
		Set<ChannelDTO> retrievedChannels = new HashSet<>(); 

		for (ChannelDTO channelDTO : channelsArray)
			retrievedChannels.add(channelDTO);

		assertEquals(channels, retrievedChannels);
	}

	@Test
	public void GIVEN_a_invalid_request_WHEN_finding_channels_by_tags_THEN_responde_with_bad_request() throws Exception {

		when(this.service.getChannelsByTag(any()))
				.thenThrow(new RuntimeException("Test Exception when finding channels by tags"));

		this.mockMvc
				.perform(get("/channel/search/accion!aventura"))
				.andDo(print()).andExpect(status().isBadRequest()).andReturn();
	}
	
	// *******************
	// SAVE A NEW CHANNEL
	// *******************

	@Test
	public void GIVEN_a_request_to_save_a_new_channel_WHEN_saving_it_THEN_response_with_saved_channel()
			throws Exception {
		ChannelDTO channelToSave = new ChannelDTO();
		channelToSave.setName("Test Channel Name");
		channelToSave.getTags().add("tag");

		when(this.service.saveChannel(channelToSave)).thenReturn(channelToSave);

		MvcResult response = this.mockMvc
				.perform(post("/channel/").content(this.objectToJson(channelToSave))
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print()).andExpect(status().isCreated()).andReturn();

		ChannelDTO savedChannel = this.mapper.readValue(response.getResponse().getContentAsString(), ChannelDTO.class);

		assertEquals(channelToSave, savedChannel);
	}

	@Test
	public void GIVEN_a_invalid_request_to_save_a_new_channel_WHEN_trying_to_save_it_THEN_responde_with_bad_request()
			throws Exception {
		ChannelDTO channelToSave = new ChannelDTO();

		when(this.service.saveChannel(channelToSave))
				.thenThrow(new RuntimeException("Test Exception when saving invalid channel"));

		this.mockMvc
				.perform(post("/channel/").content(this.objectToJson(channelToSave))
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print()).andExpect(status().isBadRequest()).andReturn();
	}

	// *******************
	// SAVE A NEW CHANNEL
	// *******************

	@Test
	public void GIVEN_a_request_to_save_a_new_video_on_a_channel_WHEN_saving_it_THEN_response_with_saved_video()
			throws Exception {
		ChannelDTO aChannel = new ChannelDTO();
		aChannel.setId(10);
		aChannel.setName("Test Channel Name");
		aChannel.getTags().add("tag");

		VideoDTO videoToSave = new VideoDTO();
		videoToSave.setTitle("Test Video Title");
		videoToSave.setUrl("http://sarasa.com/video?value=test");

		aChannel.getPlaylist().add(videoToSave);

		when(this.service.addVideoToChannel(videoToSave, 10)).thenReturn(aChannel);

		MvcResult response = this.mockMvc
				.perform(post("/channel/10/video").content(this.objectToJson(videoToSave))
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print()).andExpect(status().isCreated()).andReturn();

		ChannelDTO updatedChannel = this.mapper.readValue(response.getResponse().getContentAsString(),
				ChannelDTO.class);

		assertThat(updatedChannel.getPlaylist(), contains(videoToSave));
	}

	@Test
	public void GIVEN_a_invalid_request_to_save_a_new_video_in_a_channel_WHEN_trying_to_save_it_THEN_responde_with_bad_request()
			throws Exception {
		VideoDTO videoToSave = new VideoDTO();
		when(this.service.addVideoToChannel(videoToSave, 10))
				.thenThrow(new RuntimeException("Test Exception when saving invalid channel"));

		this.mockMvc
				.perform(post("/channel/10/video").content(this.objectToJson(videoToSave))
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print()).andExpect(status().isBadRequest()).andReturn();
	}

	// *******************
	// DELETE A CHANNEL
	// *******************

	@Test
	public void GIVEN_a_request_to_delete_a_channel_WHEN_deleting_it_THEN_response_with_ok() throws Exception {

		when(this.service.deleteChannel(10)).thenReturn(true);

		this.mockMvc.perform(delete("/channel/10")).andDo(print()).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void GIVEN_a_request_to_delete_a_channel_WHEN_channel_doesnt_exist_THEN_responde_with_not_found()
			throws Exception {
		when(this.service.deleteChannel(10)).thenReturn(false);

		this.mockMvc.perform(delete("/channel/10")).andDo(print()).andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void GIVEN_a_request_to_delete_a_channel_WHEN_deleting_it_THEN_exception_is_thrown_and_respondes_with_bad_request()
			throws Exception {
		when(this.service.deleteChannel(10))
				.thenThrow(new RuntimeException("Test Exception when saving invalid channel"));

		this.mockMvc.perform(delete("/channel/10")).andDo(print()).andExpect(status().isBadRequest()).andReturn();
	}

	// *******************
	// *** AUX_METHODS ***
	// *******************

	private String objectToJson(Object o) {
		try {
			return this.mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}