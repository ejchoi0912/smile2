package com.snuh.smile.repository;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.Auth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity.Activities, Integer> {

    @Nullable
    List<Activity.Activities> findByUserAccessToken(String token);

    @Nullable
    Page<Activity.Activities> findByUserAccessTokenContaining(String token, Pageable pageable);
}
