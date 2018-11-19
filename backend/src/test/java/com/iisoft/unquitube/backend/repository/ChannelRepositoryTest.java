package com.iisoft.unquitube.backend.repository;

import static org.junit.Assert.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.iisoft.unquitube.backend.dto.ChannelDTO;
import com.iisoft.unquitube.backend.dto.VideoDTO;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations="classpath:com/iisoft/unquitube/backend/repository/repository-test.properties")
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(true)
public class ChannelRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private Environment environment;

	private static Logger logger = LogManager.getLogger();
	
	@Before
	public void initializeDatabase() {
		String dbName = this.environment.getRequiredProperty("spring.datasource.url");
		// por las dudas...
		if (!dbName.contains("unquitube_TEST"))
			throw new RuntimeException("La base de datos configurada no es la de test. Se encontro: " + dbName);
		
		// Limpio todas las tablas antes de correr los test (Se supone que Spring por defecto hace rollback despues de correr cada 
		// test. Por algún motivo el rollback no hace efecto. Por eso ejecuto 
		// los TRUNCATE)
		for (String tableName : Arrays.asList("channel","channel_playlist","channeldto_tags","video")) {
			String truncateQuery = "TRUNCATE TABLE `" + tableName + "`;";
			Query query = this.entityManager.getEntityManager().createNativeQuery(truncateQuery);
			query.executeUpdate();
		}
	}
	
	@Autowired
	private ChannelRepository channelRepository;

	@Ignore
	@Test
	public void GIVEN_a_new_channel_WHEN_persisting_it_should_be_saved_with_an_id_THEN_recovered_channel_is_equals_to_original() {

		ChannelDTO channel = createChannel("Mi canal de video para TEST", 2);
		
		ChannelDTO savedChannel = channelRepository.save(channel);
		
		logger.info("savedChannel = " + savedChannel);
		assertNotNull(savedChannel.getId());
		savedChannel.setId(null);
		assertEquals(channel, savedChannel);
	}
	
	private ChannelDTO createChannel(String channelName, Integer numberOfVideos) {
		ChannelDTO channel = new ChannelDTO();
		channel.setName(channelName);

		channel.setPlaylist(new HashSet<>());
		VideoDTO video = null;
		
		for (int i=1; i<=numberOfVideos; i++) {
			video = new VideoDTO();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
			video.setTitle("Mi video "+i+"_"+formatter.format(Instant.now()));
			video.setUrl("https://www.youtube.com/embed/sarasa_loquevenga_"+i);
			video.setThumbnailUrl("https://www.youtube.com/embed/sarasa_loquevenga_"+i+".jpg");
			channel.getPlaylist().add(video);

			channel.setTags(new HashSet<>());
			channel.getTags().add("jazz");
			channel.getTags().add("acción");
			channel.getTags().add("anime");
		}
		
		return channel;
	}

}
