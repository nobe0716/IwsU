package io.github.nobe0716.iwsu.repository;

import io.github.nobe0716.iwsu.entity.ShorteningMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShorteningMappingRepository extends JpaRepository<ShorteningMapping, Long> {

}
