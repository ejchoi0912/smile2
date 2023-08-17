package com.snuh.smile.repository;

import com.snuh.smile.domain.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Integer> {

    @Nullable
    Auth findByEmail(String email);
}
