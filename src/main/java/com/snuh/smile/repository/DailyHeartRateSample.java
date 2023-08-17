package com.snuh.smile.repository;

import com.snuh.smile.domain.Dailies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyHeartRateSample extends JpaRepository<Dailies.HeartRateSample, Integer> {
}
