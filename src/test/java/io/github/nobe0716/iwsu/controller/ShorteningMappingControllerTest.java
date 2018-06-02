package io.github.nobe0716.iwsu.controller;

import io.github.nobe0716.iwsu.entity.ShorteningMappingEntity;
import io.github.nobe0716.iwsu.model.GenerationResult;
import io.github.nobe0716.iwsu.model.req.GenerateShortenReq;
import io.github.nobe0716.iwsu.model.res.ShorteningMappingRto;
import io.github.nobe0716.iwsu.service.ShorteningMappingService;
import io.github.nobe0716.iwsu.util.DummyParamGenerator;
import io.github.nobe0716.iwsu.util.JacksonUtils;
import io.github.nobe0716.iwsu.util.UrlComposer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(SimpleShorteningController.class)
public class ShorteningMappingControllerTest {
	@Autowired
	private MockMvc mvc;
	@MockBean
	private ShorteningMappingService shorteningMappingService;
	@MockBean
	private UrlComposer urlComposer;
	private String NORMAL_URL = "https://github.com/";

	@Before
	public void before() {
		given(urlComposer.getBaseUrl())
			.willReturn("http://localhost:8080/");
	}

	@Test
	public void generateShorten() throws Exception {
		ShorteningMappingEntity entity = DummyParamGenerator.getShorteningMappingEntity(NORMAL_URL);
		GenerationResult generationResult = GenerationResult.of(true, entity);
		given(shorteningMappingService.findOrSaveMapping(NORMAL_URL))
			.willReturn(generationResult);

		GenerateShortenReq req = GenerateShortenReq.of(NORMAL_URL);
		Optional<String> reqAsOptional = JacksonUtils.toJson(req);
		assert reqAsOptional.isPresent();

		ShorteningMappingRto rto = ShorteningMappingRto.of(entity, urlComposer.getBaseUrl());
		Optional<String> resAsOptional = JacksonUtils.toJson(rto);
		assert resAsOptional.isPresent();

		mvc.perform(post("/v1/mappings")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(reqAsOptional.get()))
			.andExpect(MockMvcResultMatchers.content().json(resAsOptional.get()))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@Test
	public void generateDuplicate() throws Exception {
		ShorteningMappingEntity entity = DummyParamGenerator.getShorteningMappingEntity(NORMAL_URL);
		GenerationResult generationResult = GenerationResult.of(false, entity);
		given(shorteningMappingService.findOrSaveMapping(NORMAL_URL))
			.willReturn(generationResult);

		GenerateShortenReq req = GenerateShortenReq.of(NORMAL_URL);
		Optional<String> reqAsOptional = JacksonUtils.toJson(req);
		assert reqAsOptional.isPresent();

		ShorteningMappingRto rto = ShorteningMappingRto.of(entity, urlComposer.getBaseUrl());
		Optional<String> resAsOptional = JacksonUtils.toJson(rto);
		assert resAsOptional.isPresent();

		mvc.perform(post("/v1/mappings")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(reqAsOptional.get()))
			.andExpect(MockMvcResultMatchers.content().json(resAsOptional.get()))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@Test
	public void generateShortenFromMalFormed() throws Exception {
		String MALFORMED_URL = "mal_formed_url";
		GenerateShortenReq req = GenerateShortenReq.of(MALFORMED_URL);
		Optional<String> reqAsOptional = JacksonUtils.toJson(req);
		assert reqAsOptional.isPresent();

		mvc.perform(post("/v1/mappings")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(reqAsOptional.get()))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	public void getBy() throws Exception {
		ShorteningMappingEntity entity = DummyParamGenerator.getShorteningMappingEntity(NORMAL_URL);
		long id = entity.getId();
		given(shorteningMappingService.findOne(id))
			.willReturn(Optional.of(entity));

		ShorteningMappingRto rto = ShorteningMappingRto.of(entity, urlComposer.getBaseUrl());
		Optional<String> resAsOptional = JacksonUtils.toJson(rto);
		assert resAsOptional.isPresent();

		mvc.perform(get("/v1/mappings/{id}", id))
			.andExpect(MockMvcResultMatchers.content().json(resAsOptional.get()))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
}