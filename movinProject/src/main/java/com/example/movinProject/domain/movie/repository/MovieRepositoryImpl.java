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

    private final DebateRoomRepository debateRoomRepository;

    @Override
    public List<Movie> findMoviesByOpenDebateRooms() {
        List<Long> openDebateRoomMovieIds = debateRoomRepository.findMovieIdsByOpenState();
        return queryFactory
                .selectFrom(QMovie.movie)
                .where(QMovie.movie.id.in(openDebateRoomMovieIds))
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
    public List<Movie> searchMoviesByKeywordwithSize(String keyword, int size) {
        QMovie movie = QMovie.movie;
        return queryFactory
                .selectFrom(movie)
                .where(movie.title.containsIgnoreCase(keyword)
                        .or(movie.description.containsIgnoreCase(keyword)))
                .orderBy(movie.title.asc())
                .limit(size)
                .fetch();
    }
}