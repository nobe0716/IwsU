package io.github.nobe0716.iwsu.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
public class ShorteningMappingEntity {
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
