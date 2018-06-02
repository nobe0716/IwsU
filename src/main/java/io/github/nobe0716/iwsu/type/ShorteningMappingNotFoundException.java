package io.github.nobe0716.iwsu.type;

import lombok.Data;

@Data
public class ShorteningMappingNotFoundException extends RuntimeException {
	private String shortenHash;

	public ShorteningMappingNotFoundException(String shortenHash) {
		super();
		this.shortenHash = shortenHash;
	}
}
