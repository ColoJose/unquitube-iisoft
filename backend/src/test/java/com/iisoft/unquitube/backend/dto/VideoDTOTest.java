package com.iisoft.unquitube.backend.dto;

import static org.junit.Assert.*;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class VideoDTOTest {

	@Test
	public void validateEqualsAndHashcodeMethodFunctioning() {
		EqualsVerifier.forClass(VideoDTO.class).verify();;
	}

}
