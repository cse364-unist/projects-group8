import { SimpleDebateRoom } from '../../../../models/DebateRoom';
import './style.css';

export default function DebateRoomItem({
  debateRoom,
}: {
  debateRoom: SimpleDebateRoom;
}) {
  return (
    <div className="debate-room-item">
      <h3>{debateRoom.title}</h3>
      <p>Proposition: {debateRoom.topic}</p>
      <p>Agreement: {debateRoom.agreeJoinedUserNumber} | Opposition: {debateRoom.disagreeJoinedUserNumber}</p>
    </div>
  );
}