package io.github.nobe0716.iwsu.controller;

import io.github.nobe0716.iwsu.entity.ShorteningMappingEntity;
import io.github.nobe0716.iwsu.model.GenerationResult;
import io.github.nobe0716.iwsu.model.req.GenerateShortenReq;
import io.github.nobe0716.iwsu.model.res.ShorteningMappingRto;
import io.github.nobe0716.iwsu.service.ShorteningMappingService;
import io.github.nobe0716.iwsu.util.UrlComposer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/v1/mappings")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SimpleShorteningController {
	private final ShorteningMappingService shorteningMappingService;
	private final UrlComposer urlComposer;

	@PostMapping("")
	public ResponseEntity<ShorteningMappingRto> generateShorten(@RequestBody @Valid GenerateShortenReq req,
																HttpServletRequest httpServletRequest) {
		GenerationResult result = shorteningMappingService.findOrSaveMapping(req.getUrl());
		ShorteningMappingEntity entity = result.getEntity();
		StringBuffer sb = httpServletRequest.getRequestURL();
		sb.append("/").append(entity.getId());
		return ResponseEntity.created(URI.create(sb.toString()))
			.body(ShorteningMappingRto.of(entity, urlComposer.getBaseUrl()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ShorteningMappingRto> getBy(@PathVariable long id) {
		Optional<ShorteningMappingEntity> optional = shorteningMappingService.findOne(id);
		return optional
			.map(entity ->
				ResponseEntity.ok(ShorteningMappingRto.of(entity, urlComposer.getBaseUrl())))
			.orElse(ResponseEntity.notFound().build());
	}
}
