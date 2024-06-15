import React, { Suspense, lazy, useEffect, useRef } from 'react';
import { useRecoilValue } from 'recoil';
import {
  mainPageDebatingMoviesSelector,
  mainPagePopularMoviesSelector,
  movieSearchResultSelector,
  useSearchTrigger,
  useSetKeyword,
} from '../../states/MainPageState';

import {
  isAuthenticatedState,
  userJoinedDebateRoomsSelector,
} from '../../states/AuthState';
import ContentArea from '../../components/ContentArea';
import Title from '../../components/Title';
import styled from 'styled-components';
import JoinedDebateRoomItem from './components/JoinedDebateRoomItem';

// Lazy load components
const Hero = lazy(() => import('./components/Hero'));
const BigMovieItems = lazy(() => import('./components/BigMovieItems'));
const SearchBox = lazy(() => import('./components/SearchBox'));

const StyledSection = styled.section`
  margin-bottom: 86px;

  &.last {
    margin-bottom: 0;
  }

  & .room-list {
    width: 100%;
    display: flex;
    flex-direction: row;
    overflow-x: auto;
    gap: 26px;

    & > * {
      flex-shrink: 0;
    }
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

function ScrollButtomDetector() {
  const triggerSearch = useSearchTrigger();

  useEffect(() => {
    const handleScroll = () => {
      if (
        window.innerHeight + document.documentElement.scrollTop >=
        document.documentElement.offsetHeight - 200
      ) {
        triggerSearch();
      }
    };

    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  });

  return null;
}

const MainPage: React.FC = () => {
  const isLoggedIn = useRecoilValue(isAuthenticatedState);
  const userJoinedDebateRooms = useRecoilValue(userJoinedDebateRoomsSelector);
  const debateMovies = useRecoilValue(mainPageDebatingMoviesSelector);
  const popularMovies = useRecoilValue(mainPagePopularMoviesSelector);
  const searchResult = useRecoilValue(movieSearchResultSelector);
  const IsFirstRef = useRef<boolean>(true);

  const setSearchKeyword = useSetKeyword();
  const triggerSearch = useSearchTrigger();

  const handleSearch = (keyword: string) => {
    setSearchKeyword(keyword);
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
      <Suspense>
        <Hero />
      </Suspense>
      <div
        style={{
          height: 86,
        }}
      />

      {isLoggedIn && (
        <StyledSection className="discussion-rooms">
          <Title text="Your Discussion Rooms" />
          <div className="room-list">
            <Suspense fallback={<div>Loading...</div>}>
              {userJoinedDebateRooms.map((debateRoom) => (
                <JoinedDebateRoomItem key={debateRoom.id} room={debateRoom} />
              ))}
            </Suspense>
          </div>
        </StyledSection>
      )}

      {/* Discussing Active Movies Section */}
      <ContentArea>
        <StyledSection className="trending-rooms">
          <Title text="Discussing Active Movies" />
          <div className="room-list">
            <Suspense fallback={<div>Loading...</div>}>
              {debateMovies.map((movie) => (
                <BigMovieItems key={movie.id} movie={movie} />
              ))}
            </Suspense>
          </div>
        </StyledSection>
      </ContentArea>

      {/* Hot Movies Section */}
      <ContentArea>
        <StyledSection className="hot-movies">
          <Title text="Hot Movies" />
          <div className="room-list">
            <Suspense fallback={<div>Loading...</div>}>
              {popularMovies.map((movie) => (
                <BigMovieItems key={movie.id} movie={movie} />
              ))}
            </Suspense>
          </div>
        </StyledSection>
      </ContentArea>

      {/* Explore Section */}
      <ContentArea>
        <StyledSection className="explore last">
          <Title text="Explore" />
          <Suspense fallback={<div>Loading...</div>}>
            <SearchBox onSearch={handleSearch} />
          </Suspense>
          <StyledExploreList className="explore-list">
            <Suspense fallback={<div>Loading...</div>}>
              {searchResult.map((movie) => (
                <BigMovieItems key={movie.id} movie={movie} />
              ))}
            </Suspense>
          </StyledExploreList>
          <ScrollButtomDetector />
        </StyledSection>
      </ContentArea>
    </div>
  );
};

export default MainPage;
