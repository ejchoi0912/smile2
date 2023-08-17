package com.snuh.smile.repository;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.ActivityDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityDetailRepository extends JpaRepository<ActivityDetails.ActivityDetail, Integer> {
    @Nullable
    List<ActivityDetails.ActivityDetail> findByUserAccessToken(String token);

    @Nullable
    Page<ActivityDetails.ActivityDetail> findByUserAccessTokenContaining(String token, Pageable pageable);
}
