package com.snuh.smile.repository;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.Epochs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpochRepository extends JpaRepository<Epochs.Epoch, Integer> {
    @Nullable
    List<Epochs.Epoch> findByUserAccessToken(String token);

    @Nullable
    Page<Epochs.Epoch> findByUserAccessTokenContaining(String token, Pageable pageable);
}
