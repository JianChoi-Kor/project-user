package com.project.user.repository.querydsl;

import static com.project.user.entity.QUser.user;

import com.project.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<User> findByUsername(String username) {
        User targetUser = queryFactory.select(user)
                .from(user)
                .where(user.username.eq(username))
                .fetchFirst();

        return Optional.ofNullable(targetUser);
    }

    public boolean existsByUsername(String username) {
        Integer fetchOne = queryFactory.selectOne()
            .from(user)
            .where(user.username.eq(username))
            .fetchFirst();

        return fetchOne != null;
    }
}