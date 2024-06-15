import React, { Suspense, useEffect, useState } from 'react';
import { useRecoilState, useRecoilValueLoadable } from 'recoil';
import { useParams } from 'react-router-dom';
import MovieInformation from './components/MovieInformation';
import DebateRoomItem from './components/DebateRoomItem';
import JoinDebateRoomPopup from './popup/JoinDebateRoomPopup';
import {
  moviePageMovieIdSelector,
  moviePageMovieSelector,
  moviePageWaitingForVoteDebateRoomSelector,
  moviePageDebateOpenedDebateRoomSelector,
} from '../../states/MoviePageState';
import './style.css';
import Title from '../../components/Title';
import ContentArea from '../../components/ContentArea';
import CreateButton from './components/CreateButton';
import styled from 'styled-components';

const StyledWrapContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  gap: 36px 14px;
`;

const StyledSection = styled.section`
  width: 100%;
  margin-bottom: 86px;

  &.last {
    margin-bottom: 0;
  }
`;

const MoviePageContent: React.FC = () => {
  const movie = useRecoilValueLoadable(moviePageMovieSelector);
  const waitingForVoteRooms = useRecoilValueLoadable(
    moviePageWaitingForVoteDebateRoomSelector,
  );
  const debateOpenedRooms = useRecoilValueLoadable(
    moviePageDebateOpenedDebateRoomSelector,
  );

  const [isJoinPopupOpen, setJoinPopupOpen] = useState({
    isOpen: false,
    roomId: 0,
  });

  const handleOnJoinItemClick = (roomId: number) => {
    setJoinPopupOpen({ isOpen: true, roomId });
  };

  if (movie.state === 'loading') return <div>Loading...</div>;
  if (movie.state === 'hasError') return <div>Error loading movie data</div>;

  return (
    <div className="movie-detailed-page">
      <MovieInformation />
      <div style={{ height: 80 }} />
      <ContentArea>
        <div
          style={{
            width: '100%',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <StyledSection>
            <Title text="Waiting for vote" />
            {waitingForVoteRooms.state === 'loading' && <div>Loading...</div>}
            {waitingForVoteRooms.state === 'hasError' && (
              <div>Error loading debate rooms</div>
            )}
            {waitingForVoteRooms.state === 'hasValue' && (
              <StyledWrapContainer>
                {waitingForVoteRooms.contents.map((room) => (
                  <DebateRoomItem
                    key={room.id}
                    debateRoom={room}
                    onClick={() => {}}
                  />
                ))}
              </StyledWrapContainer>
            )}
          </StyledSection>
          <StyledSection>
            <Title text="You Can Join" />
            {debateOpenedRooms.state === 'loading' && <div>Loading...</div>}
            {debateOpenedRooms.state === 'hasError' && (
              <div>Error loading debate rooms</div>
            )}
            {debateOpenedRooms.state === 'hasValue' && (
              <StyledWrapContainer>
                {debateOpenedRooms.contents.map((room) => (
                  <DebateRoomItem
                    key={room.id}
                    debateRoom={room}
                    onClick={() => handleOnJoinItemClick(room.id)}
                  />
                ))}
              </StyledWrapContainer>
            )}
          </StyledSection>
          <CreateButton />
        </div>
        <div style={{ height: 80 }} />

        {isJoinPopupOpen && (
          <JoinDebateRoomPopup
            onClose={() => setJoinPopupOpen({ isOpen: false, roomId: 0 })}
            id={isJoinPopupOpen.roomId}
            visible={isJoinPopupOpen.isOpen}
          />
        )}
      </ContentArea>
    </div>
  );
};

const MoviePage: React.FC = () => {
  const { movieId } = useParams<{ movieId: string }>();
  const [, setMovieId] = useRecoilState(moviePageMovieIdSelector);

  useEffect(() => {
    if (movieId) {
      setMovieId(Number(movieId));
    }
  }, [movieId, setMovieId]);

  return (
    <Suspense fallback={<div>Loading...</div>}>
      <MoviePageContent />
    </Suspense>
  );
};

export default MoviePage;
