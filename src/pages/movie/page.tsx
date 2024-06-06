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

const MoviePageContent: React.FC = () => {
  const movie = useRecoilValueLoadable(moviePageMovieSelector);
  const waitingForVoteRooms = useRecoilValueLoadable(moviePageWaitingForVoteDebateRoomSelector);
  const debateOpenedRooms = useRecoilValueLoadable(moviePageDebateOpenedDebateRoomSelector);
  const [movieId] = useRecoilState(moviePageMovieIdSelector);

  const [isCreatePopupOpen, setCreatePopupOpen] = React.useState(false);
  const [isJoinPopupOpen, setJoinPopupOpen] = React.useState(false);

  if (movie.state === 'loading') return <div>Loading...</div>;
  if (movie.state === 'hasError') return <div>Error loading movie data</div>;

  const handleCreateNewDiscussion = async (title: string, points: string, startTime: Date) => {
    console.log('handleCreateNewDiscussion:', title, points, startTime); // 디버깅을 위해 로그 추가
    try {
      await createDebateRoom(title, points, startTime, movieId);
      console.log('New discussion created:', { title, points, startTime });
      // 성공 후 로직 추가 (예: 상태 업데이트, 메시지 표시 등)
    } catch (error) {
      console.error('Error creating new discussion:', error);
    }
  };

  const handleJoinDebateRoom = (position: string) => {
    console.log('Joined debate room with position:', position);
  };

  return (
    <div className="movie-detailed-page">
      <MovieInformation />
      <div className="debate-rooms">
        <h2>Waiting for vote</h2>
        {waitingForVoteRooms.state === 'loading' && <div>Loading...</div>}
        {waitingForVoteRooms.state === 'hasError' && <div>Error loading debate rooms</div>}
        {waitingForVoteRooms.state === 'hasValue' &&
          waitingForVoteRooms.contents.map(room => (
            <DebateRoomItem key={room.id} debateRoom={room} />
          ))}

        <h2>You can join</h2>
        {debateOpenedRooms.state === 'loading' && <div>Loading...</div>}
        {debateOpenedRooms.state === 'hasError' && <div>Error loading debate rooms</div>}
        {debateOpenedRooms.state === 'hasValue' &&
          debateOpenedRooms.contents.map(room => (
            <DebateRoomItem key={room.id} debateRoom={room} />
          ))}
      </div>
      <button onClick={() => setCreatePopupOpen(true)}>Create new discussion!</button>
      {isCreatePopupOpen && (
        <CreateNewDiscussionPopup
          onClose={() => setCreatePopupOpen(false)}
          onCreate={handleCreateNewDiscussion}
        />
      )}
      {isJoinPopupOpen && <JoinDebateRoomPopup onClose={() => setJoinPopupOpen(false)} onJoin={handleJoinDebateRoom} />}
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
