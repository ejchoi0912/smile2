package com.snuh.smile.repository;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.HealthSnapshots;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthSnapshotRepository extends JpaRepository<HealthSnapshots.HealthSnapshot, Integer> {
    @Nullable
    List<HealthSnapshots.HealthSnapshot> findByUserAccessToken(String token);

    @Nullable
    Page<HealthSnapshots.HealthSnapshot> findByUserAccessTokenContaining(String token, Pageable pageable);
}
