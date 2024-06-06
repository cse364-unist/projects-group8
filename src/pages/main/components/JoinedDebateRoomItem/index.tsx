import React from 'react';
import './JoinedDebateRoomItem.css';

const JoinedDebateRoomItem: React.FC = () => {
  return (
    <div className="debate-room-item">
      <img src="./movinImg.png" alt="Room Poster" />
      <h3>Room Title</h3>
      <p>Date: 2023-06-06</p>
    </div>
  );
}

export default JoinedDebateRoomItem;