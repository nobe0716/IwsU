package io.github.nobe0716.iwsu.controller;

import io.github.nobe0716.iwsu.type.ShorteningMappingNotFoundException;
import io.github.nobe0716.iwsu.util.UrlComposer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ErrorController {
	private final UrlComposer urlComposer;

	@ExceptionHandler(ShorteningMappingNotFoundException.class)
	public ModelAndView handle(ShorteningMappingNotFoundException e) {
		ModelAndView mav = new ModelAndView("mappingNotFound");
		mav.addObject("givenHashUrl", urlComposer.getHashUrl(e.getShortenHash()));
		return mav;
	}
}
