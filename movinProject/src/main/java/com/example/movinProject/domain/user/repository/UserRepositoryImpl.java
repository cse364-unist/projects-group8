package com.example.movinProject.domain.user.repository;

import com.example.movinProject.domain.user.domain.QUser;
import com.example.movinProject.domain.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom
{


    private final JPAQueryFactory queryFactory;
    @Override
    public Optional<User> findByUserName(String userName) {
        QUser qUser = QUser.user;
        User user = queryFactory.selectFrom(qUser)
                .where(qUser.userName.eq(userName))
                .fetchOne();
        return Optional.ofNullable(user);
    }
}
