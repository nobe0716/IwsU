package io.github.nobe0716.iwsu.controller;

import io.github.nobe0716.iwsu.model.GenerationResult;
import io.github.nobe0716.iwsu.model.req.GenerateShortenReq;
import io.github.nobe0716.iwsu.model.res.GenerateShortenRto;
import io.github.nobe0716.iwsu.model.res.ShortenMappingRto;
import io.github.nobe0716.iwsu.service.ShorteningMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Validated
@RestController
@RequestMapping("v1/mappings")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShorteningMappingController {
	private final ShorteningMappingService shorteningMappingService;

	@PostMapping("")
	public ResponseEntity<GenerateShortenRto> generateShorten(@RequestBody GenerateShortenReq req,
															  HttpServletRequest httpServletRequest) {
		GenerationResult result = shorteningMappingService.findOrSaveMapping(req.getUrl());

		GenerateShortenRto rto = GenerateShortenRto.of(result);
		StringBuffer sb = httpServletRequest.getRequestURL();
		sb.append("/").append(result.getId());
		return ResponseEntity.created(URI.create(sb.toString())).body(rto);
	}

	@GetMapping("/{id}")
	@ResponseBody
	public ShortenMappingRto getBy(@PathVariable long id) {
		return ShortenMappingRto.of(shorteningMappingService.findOne(id));
	}
}
