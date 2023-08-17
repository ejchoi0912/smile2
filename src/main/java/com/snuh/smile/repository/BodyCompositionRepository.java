package com.snuh.smile.repository;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.BodyCompositions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BodyCompositionRepository extends JpaRepository<BodyCompositions.BodyComposition, Integer> {
    @Nullable
    List<BodyCompositions.BodyComposition> findByUserAccessToken(String token);

    @Nullable
    Page<BodyCompositions.BodyComposition> findByUserAccessTokenContaining(String token, Pageable pageable);
}
