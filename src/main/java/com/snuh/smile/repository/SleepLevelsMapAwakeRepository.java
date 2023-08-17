package com.snuh.smile.repository;

import com.snuh.smile.domain.Sleeps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SleepLevelsMapAwakeRepository extends JpaRepository<Sleeps.Awake, Integer> {
}
