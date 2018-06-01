package io.github.nobe0716.iwsu.entity;

import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;

@RestResource(path = "/")
@Entity
public class ShortenUrl {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OrderColumn(nullable = false)
	private String shorten;

	@OrderColumn(nullable = false)
	private String original;
}
