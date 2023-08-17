package com.snuh.smile.repository;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.PulseOxs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PulseOxRepository extends JpaRepository<PulseOxs.PulseOx, Integer> {
    @Nullable
    List<PulseOxs.PulseOx> findByUserAccessToken(String token);

    @Nullable
    Page<PulseOxs.PulseOx> findByUserAccessTokenContaining(String token, Pageable pageable);
}
