import React, { useEffect } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';
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
import './style.css';

export default function MoviePage() {
  const { movieId } = useParams();
  const [, setMovieId] = useRecoilState(moviePageMovieIdSelector);
  const movie = useRecoilValue(moviePageMovieSelector);
  const waitingForVoteRooms = useRecoilValue(
    moviePageWaitingForVoteDebateRoomSelector
  );
  const debateOpenedRooms = useRecoilValue(
    moviePageDebateOpenedDebateRoomSelector
  );

  const [isCreatePopupOpen, setCreatePopupOpen] = React.useState(false);
  const [isJoinPopupOpen, setJoinPopupOpen] = React.useState(false);

  useEffect(() => {
    if (movieId) {
      setMovieId(Number(movieId));
    }
  }, [movieId, setMovieId]);

  const handleCreateNewDiscussion = (title: string, points: string, startTime: string) => {
    console.log('New discussion created:', { title, points, startTime });
  };

  const handleJoinDebateRoom = (position: string) => {
    console.log('Joined debate room with position:', position);
  };

  return (
    <div className="movie-detailed-page">
      <MovieInformation />
      <div className="debate-rooms">
        <h2>Waiting for vote</h2>
        {waitingForVoteRooms.map(room => (
          <DebateRoomItem key={room.id} debateRoom={room} />
        ))}

        <h2>You can join</h2>
        {debateOpenedRooms.map(room => (
          <DebateRoomItem key={room.id} debateRoom={room} />
        ))}
      </div>
      <button onClick={() => setCreatePopupOpen(true)}>Create new discussion!</button>
      {isCreatePopupOpen && <CreateNewDiscussionPopup onClose={() => setCreatePopupOpen(false)} onCreate={handleCreateNewDiscussion} />}
      {isJoinPopupOpen && <JoinDebateRoomPopup onClose={() => setJoinPopupOpen(false)} onJoin={handleJoinDebateRoom} />}
    </div>
  );
}