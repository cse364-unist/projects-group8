import React, { useState, useEffect } from 'react';
import './VotePopup.css';
import {voteToDebateRoom} from '../../../../services/DebateRoomService';
import {DebateRoomWithUserInformation} from '../../../../models/DebateRoom';

interface VotePopupProps {
  message: string;
  onClose: () => void;
}

const VotePopup: React.FC<VotePopupProps> = ({ message, onClose}) => {
  const [vote, setVote] = useState<DebateRoomWithUserInformation | null>(null);
  const [debateRoomId, setDebateRoomId] = useState<number>(0);
  const [agree, setAgree] = useState<boolean>(false);

  const handleVote = async () => {
    if (message === 'Agree') {
      setAgree(true);
    }
    else {
      setAgree(false);
    }
    try {
      const result = await voteToDebateRoom(debateRoomId, agree);
      setVote(result);
      // // Replace this URL with your actual API endpoint
      // const response = await fetch('https://your-api-endpoint.com/vote', {
      //   method: 'POST',
      //   headers: {
      //     'Content-Type': 'application/json',
      //   },
      //   body: JSON.stringify({ message }),
      // });

      // if (!response.ok) {
      //   throw new Error('Failed to vote');
      // }

      // // Handle successful vote (e.g., show a success message)
      // onClose();
    } catch (error) {
      console.error('Error voting:', error);
      // Handle error (e.g., show an error message)
    }
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal" onClick={(e) => e.stopPropagation()}>
        <h2>Vote "{message}"</h2>
        <p>When you join a discussion room, you bet 100 points.</p>
        <div className="box">100$</div>
        <p>At the end of the voting period, all bet amounts will be distributed to the winning participants and those who voted for the winning team.</p>
        <button onClick={onClose} className="close-button">Cancle</button>
        <button onClick={handleVote} className="close-button">VOTE</button>
      </div>
    </div>
  );
}

export default VotePopup;