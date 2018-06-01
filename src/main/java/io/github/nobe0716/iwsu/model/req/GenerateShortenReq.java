package io.github.nobe0716.iwsu.model.req;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class GenerateShortenReq {
	@URL
	private String url;
}
