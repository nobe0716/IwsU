package io.github.nobe0716.iwsu.entity;

import javax.persistence.*;

@Entity
public class ShorteningMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OrderColumn(nullable = false)
	@Column(unique = true)
	private String shorten;

	@OrderColumn(nullable = false)
	@Column(unique = true)
	private String original;
}
