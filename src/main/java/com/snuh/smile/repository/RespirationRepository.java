package com.snuh.smile.repository;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.Respirations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespirationRepository extends JpaRepository<Respirations.Respiration, Integer> {
    @Nullable
    List<Respirations.Respiration> findByUserAccessToken(String token);

    @Nullable
    Page<Respirations.Respiration> findByUserAccessTokenContaining(String token, Pageable pageable);
}
