import React from 'react';
import { voteToDebateRoom } from '../../../../services/DebateRoomService';

import { useSetRecoilState } from 'recoil';
import { votePageUserVotedSelector } from '../../../../states/VotePageState';
import Modal from '../../../../components/Modal';
import styled from 'styled-components';

import CoinIcon from './CoinItem.svg';

const StyledPopup = styled.div`
  display: flex;
  flex-direction: column;

  gap: 28px;

  padding: 28px;

  & h2 {
    margin: 0;
    font-size: 24px;
    font-weight: bold;
  }

  & button {
    padding: 14px 20px;
    border-radius: 14px;
    border: 1px solid #d9d9d9;
    color: black;
    font-size: 16px;
    font-weight: medium;
    background-color: white;
  }

  & button:hover {
    background-color: #f4f4f4;
  }

  & button:active {
    background-color: #e8e8e8;
  }

  & button:disabled {
    opacity: 0.5;
  }

  & .actions {
    display: flex;
    flex-direction: row;
    justify-content: end;
    gap: 8px;
  }
`;

const StyledMoneyIndicator = styled.div`
  padding: 14px 10px;
  width: 240px;
  background-color: #f4f4f4;
  border-radius: 14px;
  border: 1px solid #d9d9d9;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #db4f4f;
  font-weight: medium;
  gap: 4px;
  box-shadow: inset 2px 2px 2px 0px rgba(0, 0, 0, 0.08);
  box-sizing: border-box;
`;

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
      <StyledPopup>
        <h2>Vote "{message}"</h2>
        <p>When you join a discussion room, you bet 100 points.</p>
        <StyledMoneyIndicator>
          <div>-</div>
          <img src={CoinIcon} alt="Coin" />
          <div>100</div>
        </StyledMoneyIndicator>
        <p>
          At the end of the voting period, all bet amounts will be distributed
          to the winning participants and those who voted for the winning team.
        </p>
        <div className="actions">
          <button onClick={onClose} className="close-button">
            Cancle
          </button>
          <button onClick={handleVote} className="close-button">
            VOTE
          </button>
        </div>
      </StyledPopup>
    </Modal>
  );
};

export default VotePopup;
