package io.github.nobe0716.iwsu.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlComposer {
	//	@LocalServerPort // mock test를 위해선 기본값이 필요.
	@Value("${local.server.port:8080}")
	private int localServerPort;

	public String getBaseUrl() {
		return "http://localhost:" + localServerPort + "/";
	}
}
