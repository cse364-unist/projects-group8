import React, { Suspense, useEffect } from 'react';
import { useRecoilState, useRecoilValueLoadable } from 'recoil';
import { useParams } from 'react-router-dom';
import MovieInformation from './components/MovieInformation';
import DebateRoomItem from './components/DebateRoomItem';
import CreateNewDiscussionPopup from './popup/CreateNewDiscussionPopup';
import JoinDebateRoomPopup from './popup/JoinDebateRoomPopup';
import {
  moviePageMovieIdSelector,
  moviePageMovieSelector,
  moviePageWaitingForVoteDebateRoomSelector,
  moviePageDebateOpenedDebateRoomSelector,
} from '../../states/MoviePageState';
import { createDebateRoom } from '../../services/DebateRoomService';
import './style.css';
import Title from '../../components/Title';
import ContentArea from '../../components/ContentArea';
import CreateButton from './components/CreateButton';

const MoviePageContent: React.FC = () => {
  const movie = useRecoilValueLoadable(moviePageMovieSelector);
  const waitingForVoteRooms = useRecoilValueLoadable(
    moviePageWaitingForVoteDebateRoomSelector,
  );
  const debateOpenedRooms = useRecoilValueLoadable(
    moviePageDebateOpenedDebateRoomSelector,
  );

  const [isJoinPopupOpen, setJoinPopupOpen] = React.useState(false);

  if (movie.state === 'loading') return <div>Loading...</div>;
  if (movie.state === 'hasError') return <div>Error loading movie data</div>;

  const handleJoinDebateRoom = (position: string) => {
    console.log('Joined debate room with position:', position);
  };

  return (
    <div className="movie-detailed-page">
      <MovieInformation />
      <div style={{ height: 80 }} />
      <ContentArea>
        <div className="debate-rooms">
          <Title text="Waiting for vote" />
          {waitingForVoteRooms.state === 'loading' && <div>Loading...</div>}
          {waitingForVoteRooms.state === 'hasError' && (
            <div>Error loading debate rooms</div>
          )}
          {waitingForVoteRooms.state === 'hasValue' &&
            waitingForVoteRooms.contents.map((room) => (
              <DebateRoomItem key={room.id} debateRoom={room} />
            ))}

          <Title text="You Can Join" />
          {debateOpenedRooms.state === 'loading' && <div>Loading...</div>}
          {debateOpenedRooms.state === 'hasError' && (
            <div>Error loading debate rooms</div>
          )}
          {debateOpenedRooms.state === 'hasValue' &&
            debateOpenedRooms.contents.map((room) => (
              <DebateRoomItem key={room.id} debateRoom={room} />
            ))}
        </div>
        <CreateButton />

        {isJoinPopupOpen && (
          <JoinDebateRoomPopup
            onClose={() => setJoinPopupOpen(false)}
            onJoin={handleJoinDebateRoom}
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
