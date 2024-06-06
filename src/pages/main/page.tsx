import React, { useEffect, useState } from 'react';
import { useRecoilValue, useRecoilState, useSetRecoilState } from 'recoil';
import Hero from './components/Hero';
import BigMovieItems from './components/BigMovieItems';
import JoinedDebateRoomItem from './components/JoinedDebateRoomItem';
import SearchBox from './components/SearchBox';
import {
  mainPageDebatingMoviesSelector,
  mainPagePopularMoviesSelector,
  movieSearchResultSelector,
  mainPageSearchKeywordSelector,
  mainPageSearchTriggerSelector,
  mainPageState,
} from '../../states/MainPageState';
import { getMoviesForMainPage, searchMovies } from '../../services/MovieService';
import './style.css';

const MainPage: React.FC = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false); // 로그인 상태 시뮬레이션
  const debateMovies = useRecoilValue(mainPageDebatingMoviesSelector);
  const popularMovies = useRecoilValue(mainPagePopularMoviesSelector);
  const searchResult = useRecoilValue(movieSearchResultSelector);
  const [mainPage, setMainPageState] = useRecoilState(mainPageState);

  const setSearchKeyword = useSetRecoilState(mainPageSearchKeywordSelector);
  const triggerSearch = useSetRecoilState(mainPageSearchTriggerSelector);

  useEffect(() => {
    const fetchData = async () => {
      const result = await getMoviesForMainPage();
      setMainPageState((prevState) => ({
        ...prevState,
        debatingMovies: result.debatingMovies,
        popularMovies: result.popularMovies,
      }));
    };

    fetchData();
  }, [setMainPageState]);

  useEffect(() => {
    const fetchSearchResults = async () => {
      if (mainPage.searchKeyword) {
        const result = await searchMovies(mainPage.searchKeyword, mainPage.searchPage);
        setMainPageState((prevState) => ({
          ...prevState,
          searchResult: [...prevState.searchResult, ...result],
          searchReachedEnd: result.length === 0,
        }));
      }
    };

    fetchSearchResults();
  }, [mainPage.searchPage, mainPage.searchKeyword, setMainPageState]);

  const handleSearch = (keyword: string) => {
    setSearchKeyword(keyword);
  };

  const loadMoreSearchResults = () => {
    triggerSearch(undefined);
  };

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