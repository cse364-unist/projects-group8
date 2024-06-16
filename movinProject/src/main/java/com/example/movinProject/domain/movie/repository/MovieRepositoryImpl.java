package com.example.movinProject.domain.movie.repository;

import com.example.movinProject.domain.debateRoom.repository.DebateRoomRepository;
import com.example.movinProject.domain.movie.domain.Movie;
import com.example.movinProject.domain.movie.domain.QMovie;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class MovieRepositoryImpl implements MovieRepositoryCustom {

    private final JPAQueryFactory queryFactory;



    @Override
    public List<Movie> findMoviesByOpenDebateRooms(List<Long> openDebateRoomIds) {
        return queryFactory
                .selectFrom(QMovie.movie)
                .where(QMovie.movie.id.in(openDebateRoomIds))
                .fetch();
    }

    @Override
    public List<Movie> findMoviesWithAverageRatingAbove(Double rating) {
        QMovie movie = QMovie.movie;
        return queryFactory
                .selectFrom(movie)
                .where(movie.avgRating.goe(rating))
                .orderBy(movie.avgRating.desc())
                .fetch();
    }

    @Override
    public List<Movie> searchMoviesByKeywordwithSize(String keyword, int pageNum, int limit) {
        QMovie movie = QMovie.movie;
        return queryFactory
                .selectFrom(movie)
                .where(movie.title.containsIgnoreCase(keyword)
                        .or(movie.description.containsIgnoreCase(keyword)))
                .orderBy(movie.title.asc())
                .offset((long) (pageNum - 1) * limit)
                .limit(limit)
                .fetch();
    }
}