package com.snuh.smile.repository;

import com.snuh.smile.domain.ActivityDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryRepository extends JpaRepository<ActivityDetails.Summarys, Integer> {



}
