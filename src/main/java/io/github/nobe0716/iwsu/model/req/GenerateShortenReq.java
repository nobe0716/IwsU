package io.github.nobe0716.iwsu.model.req;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data(staticConstructor = "of")
public class GenerateShortenReq {
	@URL
	private final String url;
}
