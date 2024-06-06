import React, { useState } from 'react';
import './style.css';

interface JoinDebateRoomPopupProps {
  onClose: () => void;
  onJoin: (position: 'AGREEMENT' | 'OPPOSITION') => void;
}

const JoinDebateRoomPopup: React.FC<JoinDebateRoomPopupProps> = ({ onClose, onJoin }) => {
  const [position, setPosition] = useState<'AGREEMENT' | 'OPPOSITION'>('AGREEMENT');

  const handleJoin = () => {
    onJoin(position);
    onClose();
  };

  return (
    <div className="popup">
      <h2>Join the Debating</h2>
      <div>
        <label>
          <input
            type="radio"
            value="AGREEMENT"
            checked={position === 'AGREEMENT'}
            onChange={() => setPosition('AGREEMENT')}
          />
          Agreement
        </label>
        <label>
          <input
            type="radio"
            value="OPPOSITION"
            checked={position === 'OPPOSITION'}
            onChange={() => setPosition('OPPOSITION')}
          />
          Opposition
        </label>
      </div>
      <button onClick={handleJoin}>Join</button>
      <button onClick={onClose}>Cancel</button>
    </div>
  );
};

export default JoinDebateRoomPopup;