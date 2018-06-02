package io.github.nobe0716.iwsu.controller;

import io.github.nobe0716.iwsu.entity.ShorteningMappingEntity;
import io.github.nobe0716.iwsu.service.ShorteningMappingService;
import io.github.nobe0716.iwsu.type.ShorteningMappingNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NavigationController {
	private final ShorteningMappingService shorteningMappingService;
	private static final Pattern pattern = Pattern.compile("[\\w\\d]{8}");

	/**
	 * TODO: 추후 보안 용 혼잡 제어시 모델에 데이터를 내려줘야할 수 있음
	 *
	 * @return ModelAndView of index.html
	 */
	@GetMapping
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	@ResponseBody
	@RequestMapping("/{shortenHash}")
	public ResponseEntity<Void> navigate(@PathVariable String shortenHash) throws URISyntaxException {
		if (!pattern.matcher(shortenHash).matches()) {
			throw new ShorteningMappingNotFoundException(shortenHash);
		}
		Optional<ShorteningMappingEntity> optional = shorteningMappingService.findByShorten(shortenHash);
		ShorteningMappingEntity entity = optional.orElseThrow(()
			-> new ShorteningMappingNotFoundException(shortenHash));
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(new URI(entity.getOriginalUrl()));
		return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
			.headers(httpHeaders)
			.build();
	}
}
