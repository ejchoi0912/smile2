package com.snuh.smile.repository;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.StressDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StressDetailRepository extends JpaRepository<StressDetails.StressDetail, Integer> {
    @Nullable
    List<StressDetails.StressDetail> findByUserAccessToken(String token);

    @Nullable
    Page<StressDetails.StressDetail> findByUserAccessTokenContaining(String token, Pageable pageable);
}
