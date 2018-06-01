package io.github.nobe0716.iwsu.controller;


import io.github.nobe0716.iwsu.model.req.GenerateShortenReq;
import io.github.nobe0716.iwsu.model.res.ShorteningMappingRto;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShorteningMappingControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;

	private String NORMAL_URL = "https://github.com/";
	private String MALFORMED_URL = "mal_formed_url";

	@Test
	public void generateShorten() {
		GenerateShortenReq req = GenerateShortenReq.of(NORMAL_URL);
		ResponseEntity<ShorteningMappingRto> responseEntity = restTemplate.postForEntity("/v1/mappings", req, ShorteningMappingRto.class);
		assertThat(responseEntity.getStatusCode(), Matchers.is(HttpStatus.CREATED));
	}

	@Test
	public void generateShortenTwice() {
		GenerateShortenReq req = GenerateShortenReq.of(NORMAL_URL);
		ResponseEntity<ShorteningMappingRto> responseEntity = restTemplate.postForEntity("/v1/mappings", req, ShorteningMappingRto.class);

		String firstShorten = responseEntity.getBody().getShorten();

		responseEntity = restTemplate.postForEntity("/v1/mappings", req, ShorteningMappingRto.class);
		String secondShorten = responseEntity.getBody().getShorten();

		assertThat(secondShorten, Matchers.is(firstShorten)); // same digest should be given
	}

	@Test
	public void generateShortenFromMalFormed() {
		GenerateShortenReq req = GenerateShortenReq.of(MALFORMED_URL);
		ResponseEntity<ShorteningMappingRto> entity = restTemplate.postForEntity("/v1/mappings", req, ShorteningMappingRto.class);
		assertThat(entity.getStatusCode(), Matchers.is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void getBy() {
		GenerateShortenReq req = GenerateShortenReq.of(NORMAL_URL);
		ResponseEntity<ShorteningMappingRto> responseEntity = restTemplate.postForEntity("/v1/mappings", req, ShorteningMappingRto.class);
		List<String> locationAsList = responseEntity.getHeaders().get("Location");
		assertThat(locationAsList, Matchers.hasSize(1));
		String location = locationAsList.get(0);

		ShorteningMappingRto rto = restTemplate.getForObject(location, ShorteningMappingRto.class);
		assertThat(rto, Matchers.is(responseEntity.getBody()));
	}

	@Test
	public void getByNotExist() {
		String url = "/v1/mapping/" + RandomStringUtils.randomNumeric(10);
		ResponseEntity<ShorteningMappingRto> entity = restTemplate.getForEntity(url, ShorteningMappingRto.class);
		assertThat(entity.getStatusCode(), Matchers.is(HttpStatus.NOT_FOUND));
	}
}