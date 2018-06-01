package io.github.nobe0716.iwsu.controller;

import io.github.nobe0716.iwsu.entity.ShorteningMappingEntity;
import io.github.nobe0716.iwsu.service.ShorteningMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NavigationController {
	private final ShorteningMappingService shorteningMappingService;

	@RequestMapping("/{shortenHash:[\\w\\d]+}")
	public ResponseEntity<Void> navigate(@PathVariable String shortenHash) throws URISyntaxException {
		Optional<ShorteningMappingEntity> optional = shorteningMappingService.findByShorten(shortenHash);
		if (!optional.isPresent()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "The page /" + shortenHash + " does not exist");
		}
		ShorteningMappingEntity entity = optional.get();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(new URI(entity.getOriginal()));
		return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
			.headers(httpHeaders)
			.build();
	}
}
