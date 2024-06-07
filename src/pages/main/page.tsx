import React, { useEffect, useRef } from 'react';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import Hero from './components/Hero';
import BigMovieItems from './components/BigMovieItems';
import SearchBox from './components/SearchBox';
import {
  mainPageDebatingMoviesSelector,
  mainPagePopularMoviesSelector,
  movieSearchResultSelector,
  useSearchTrigger,
  useSetKeyword,
} from '../../states/MainPageState';

import './style.css';
import { isAuthenticatedState } from '../../states/AuthState';

const MainPage: React.FC = () => {
  const isLoggedIn = useRecoilValue(isAuthenticatedState);
  const debateMovies = useRecoilValue(mainPageDebatingMoviesSelector);
  const popularMovies = useRecoilValue(mainPagePopularMoviesSelector);
  const searchResult = useRecoilValue(movieSearchResultSelector);
  const IsFirstRef = useRef<boolean>(true);

  const setSearchKeyword = useSetKeyword();
  const triggerSearch = useSearchTrigger();

  const handleSearch = (keyword: string) => {
    setSearchKeyword(keyword);
  };

  const loadMoreSearchResults = () => {
    triggerSearch();
  };

  useEffect(() => {
    if (IsFirstRef.current) {
      triggerSearch();
    }
    IsFirstRef.current = false;
  }, [triggerSearch]);

  return (
    <div className="main-page">
      {/* Hero Section */}
      <Hero />

      {isLoggedIn && (
        <section className="discussion-rooms">
          <h2>Your Discussion Rooms</h2>
          <div className="room-list">
            {/* 여기에 유저가 참가한 토론 방 정보를 넣어야 합니다. */}
          </div>
        </section>
      )}

      {/* Discussing Active Movies Section */}
      <section className="trending-rooms">
        <h2>Discussing Active Movies</h2>
        <div className="room-list">
          {debateMovies.map((movie) => (
            <BigMovieItems key={movie.id} movie={movie} />
          ))}
        </div>
      </section>

      {/* Hot Movies Section */}
      <section className="hot-movies">
        <h2>Hot Movies</h2>
        <div className="room-list">
          {popularMovies.map((movie) => (
            <BigMovieItems key={movie.id} movie={movie} />
          ))}
        </div>
      </section>

      {/* Explore Section */}
      <section className="explore">
        <h2>Explore</h2>
        <SearchBox onSearch={handleSearch} />
        <div className="explore-list">
          {searchResult.length > 0
            ? searchResult.map((movie) => (
                <BigMovieItems key={movie.id} movie={movie} />
              ))
            : popularMovies.map((movie) => (
                <BigMovieItems key={movie.id} movie={movie} />
              ))}
        </div>
        {searchResult.length > 0 && (
          <button onClick={loadMoreSearchResults}>Load More</button>
        )}
      </section>
    </div>
  );
};

export default MainPage;
