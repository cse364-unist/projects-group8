import React from 'react';
import './JoinedDebateRoomItem.css';
import { DebateRoomWithUserInformation } from '../../../../models/DebateRoom';

function JoinedDebateRoomItem({
  room,
}: {
  room: DebateRoomWithUserInformation;
}) {
  return (
    <div className="debate-room-item">
      <img src="./movinImg.png" alt="Room Poster" />
      <h3>{room.title}</h3>
      <p>Date: {room.duration}</p>
    </div>
  );
}

export default JoinedDebateRoomItem;
