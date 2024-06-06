import React from 'react';
import './JoinedDebateRoomItem.css';

interface JoinedDebateRoomItemProps {
  room: {
    title: string;
    date: string;
  };
}

const JoinedDebateRoomItem: React.FC<JoinedDebateRoomItemProps> = ({ room }) => {
  return (
    <div className="debate-room-item">
      <img src="./movinImg.png" alt="Room Poster" />
      <h3>{room.title}</h3>
      <p>Date: {room.date}</p>
    </div>
  );
}

export default JoinedDebateRoomItem;