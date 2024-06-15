import React from 'react';
import './VotePopup.css';
import { voteToDebateRoom } from '../../../../services/DebateRoomService';

import { useSetRecoilState } from 'recoil';
import { votePageUserVotedSelector } from '../../../../states/VotePageState';
import Modal from '../../../../components/Modal';

interface VotePopupProps {
  onClose: () => void;
  debateRoomId: number;
  agree: boolean;
  visible: boolean;
}

const VotePopup: React.FC<VotePopupProps> = ({
  visible,
  debateRoomId,
  agree,
  onClose,
}) => {
  const setUserVoted = useSetRecoilState(votePageUserVotedSelector);
  const message = agree ? 'Agree' : 'Disagree';

  const handleVote = async () => {
    try {
      const result = await voteToDebateRoom(debateRoomId, agree);
      setUserVoted((prev) => ({
        ...prev,
        voted: result.voted,
        voteAgree: result.voteAgree,
      }));
      onClose();
    } catch (error) {
      alert('Error voting');
    }
  };

  return (
    <Modal visible={visible} onClose={onClose}>
      <div className="modal" onClick={(e) => e.stopPropagation()}>
        <h2>Vote "{message}"</h2>
        <p>When you join a discussion room, you bet 100 points.</p>
        <div className="box">100$</div>
        <p>
          At the end of the voting period, all bet amounts will be distributed
          to the winning participants and those who voted for the winning team.
        </p>
        <button onClick={onClose} className="close-button">
          Cancle
        </button>
        <button onClick={handleVote} className="close-button">
          VOTE
        </button>
      </div>
    </Modal>
  );
};

export default VotePopup;
