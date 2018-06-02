package io.github.nobe0716.iwsu.controller;

import io.github.nobe0716.iwsu.entity.ShorteningMappingEntity;
import io.github.nobe0716.iwsu.service.ShorteningMappingService;
import io.github.nobe0716.iwsu.type.IwsuConstant;
import io.github.nobe0716.iwsu.util.DummyParamGenerator;
import io.github.nobe0716.iwsu.util.UrlComposer;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(NavigationController.class)
public class NavigationControllerTest {
	@Autowired
	private MockMvc mvc;
	@MockBean
	private ShorteningMappingService shorteningMappingService;
	@MockBean
	private UrlComposer urlComposer;

	private String SHORTEN_HASH = RandomStringUtils.randomAlphanumeric(IwsuConstant.SHORTEN_URL_LEN);
	private String SHORTEN_URL = "https://localhost:8080/" + SHORTEN_HASH;
	private String ORIGINAL_URL = "https://google.com";

	@Test
	public void index() {
	}

	@Test
	public void navigate() throws Exception {
		ShorteningMappingEntity entity = DummyParamGenerator.getShorteningMappingEntity(ORIGINAL_URL, SHORTEN_URL);
		given(shorteningMappingService.findByShorten(SHORTEN_HASH))
			.willReturn(Optional.of(entity));

		mvc.perform(get("/{shortenHash}", SHORTEN_HASH))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.header().stringValues("location", ORIGINAL_URL));
	}

	@Test
	public void notExistValidHash() throws Exception {
		given(shorteningMappingService.findByShorten(any()))
			.willReturn(Optional.empty());

		mvc.perform(get("/{shortenHash}", SHORTEN_HASH))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("mappingNotFound"));
	}

	@Test
	public void malFormedHash() throws Exception {
		mvc.perform(get("/{shortenHash}", RandomStringUtils.random(10)))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("mappingNotFound"));
	}
}