package com.project.user.repository.querydsl;

import com.project.user.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
