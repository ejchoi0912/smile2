package com.snuh.smile.repository;

import com.snuh.smile.domain.Sleeps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SleepLevelsMapRepository extends JpaRepository<Sleeps.SleepLevelsMap, Integer> {
}
