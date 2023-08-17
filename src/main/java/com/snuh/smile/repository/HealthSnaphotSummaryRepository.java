package com.snuh.smile.repository;

import com.snuh.smile.domain.HealthSnapshots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthSnaphotSummaryRepository extends JpaRepository<HealthSnapshots.SnapshotSummary, Integer> {
}
