import React, { useEffect, useRef } from 'react';
import { useRecoilValue } from 'recoil';
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

import { isAuthenticatedState } from '../../states/AuthState';
import ContentArea from '../../components/ContentArea';
import Title from './components/Title';
import styled from 'styled-components';

const StyledSection = styled.section`
  margin-bottom: 86px;

  &.last {
    margin-bottom: 0;
  }
`;

const StyledExploreList = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: center;

  gap: 36px 25px;

  & > div {
    flex: 1 1 30%;
    margin: 10px;
  }
`;

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
      <div
        style={{
          height: 86,
        }}
      />

      {isLoggedIn && (
        <StyledSection className="discussion-rooms">
          <h2>Your Discussion Rooms</h2>
          <div className="room-list">
            {/* 여기에 유저가 참가한 토론 방 정보를 넣어야 합니다. */}
          </div>
        </StyledSection>
      )}

      {/* Discussing Active Movies Section */}

      <ContentArea>
        <StyledSection className="trending-rooms">
          <Title text="Discussing Active Movies" />
          <div className="room-list">
            {debateMovies.map((movie) => (
              <BigMovieItems key={movie.id} movie={movie} />
            ))}
          </div>
        </StyledSection>
      </ContentArea>

      {/* Hot Movies Section */}
      <ContentArea>
        <StyledSection className="hot-movies">
          <Title text="Hot Movies" />
          <div className="room-list">
            {popularMovies.map((movie) => (
              <BigMovieItems key={movie.id} movie={movie} />
            ))}
          </div>
        </StyledSection>
      </ContentArea>

      {/* Explore Section */}
      <ContentArea>
        <StyledSection className="explore last">
          <Title text="Explore" />
          <SearchBox onSearch={handleSearch} />
          <StyledExploreList className="explore-list">
            {searchResult.length > 0
              ? searchResult.map((movie) => (
                  <BigMovieItems key={movie.id} movie={movie} />
                ))
              : popularMovies.map((movie) => (
                  <BigMovieItems key={movie.id} movie={movie} />
                ))}
          </StyledExploreList>
          {searchResult.length > 0 && (
            <button onClick={loadMoreSearchResults}>Load More</button>
          )}
        </StyledSection>
      </ContentArea>
    </div>
  );
};

export default MainPage;
