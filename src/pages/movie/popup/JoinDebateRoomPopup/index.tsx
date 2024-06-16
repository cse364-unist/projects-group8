import React, { useState } from 'react';
import styled from 'styled-components';
import Modal from '../../../../components/Modal';

import CoinItem from './CoinItem.svg';
import { joinDebateRoom } from '../../../../services/DebateRoomService';
import { useSetRecoilState } from 'recoil';
import { userJoinedDebateRoomsSelector } from '../../../../states/AuthState';
import { useNavigate } from 'react-router-dom';

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

const StyledInputItem = styled.div`
  display: flex;
  flex-direction: column;
  gap: 14px;
  color: black;

  & input {
    display: block;
    border-radius: 14px;
    width: 100%;
    padding: 14px;
    box-sizing: border-box;
    height: 52px;
    background-color: #f4f4f4;
    border: none;
    font-size: 16px;
  }

  & input::placeholder {
    color: #989898;
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

const StyledToggleLayout = styled.div`
  display: flex;
  flex-direction: row;
  gap: 14px;

  & button {
    padding: 14px 20px;
    font-size: 16px;
    background-color: transparent;
    text-font: 400;
    color: #494949;
    border-radius: 14px;
    border: 1px solid transparent;
    box-sizing: border-box;
  }

  & button:hover {
    background-color: #f4f4f4;
  }

  & button:active {
    background-color: #e8e8e8;
  }

  & button.selected {
    background-color: #f4f4f4;
    border: 1px solid #d9d9d9;
    font-weight: bold;
    color: black;
  }

  & button.selected:hover {
    background-color: #f4f4f4;
  }

  & button.selected:active {
    background-color: #e8e8e8;
  }
`;

interface JoinDebateRoomPopupProps {
  visible: boolean;
  id: number;
  onClose: () => void;
}

function PositionToggle({
  position,
  onChange,
}: {
  position: 'AGREEMENT' | 'OPPOSITION';
  onChange: (position: 'AGREEMENT' | 'OPPOSITION') => void;
}) {
  return (
    <StyledToggleLayout>
      <button
        className={position === 'AGREEMENT' ? 'selected' : ''}
        onClick={() => onChange('AGREEMENT')}
      >
        Agreement
      </button>
      <button
        className={position === 'OPPOSITION' ? 'selected' : ''}
        onClick={() => onChange('OPPOSITION')}
      >
        Opposition
      </button>
    </StyledToggleLayout>
  );
}

const JoinDebateRoomPopup: React.FC<JoinDebateRoomPopupProps> = ({
  visible,
  id,
  onClose,
}) => {
  const [position, setPosition] = useState<'AGREEMENT' | 'OPPOSITION'>(
    'AGREEMENT',
  );
  const navigate = useNavigate();
  const setUserJoinedDebateRoomsSelector = useSetRecoilState(
    userJoinedDebateRoomsSelector,
  );

  const handleJoin = () => {
    joinDebateRoom(id, position === 'AGREEMENT' ? true : false)
      .then((room) => {
        setUserJoinedDebateRoomsSelector((prev) => [...prev, room]);
        onClose();
        navigate(0);
      })
      .catch(() => {
        alert('Failed to join the debate room');
      });
  };

  return (
    <Modal visible={visible} onClose={onClose}>
      <StyledPopup>
        <h2>Join the Debating</h2>
        <StyledInputItem>
          <div>Your Position</div>
          <PositionToggle position={position} onChange={setPosition} />
        </StyledInputItem>
        <StyledInputItem>
          <div>When you join a discussion room, you bet 100 points.</div>
          <StyledMoneyIndicator>
            <div>-</div>
            <img src={CoinItem} alt="Coin" />
            <div>100</div>
          </StyledMoneyIndicator>
        </StyledInputItem>
        <div className="actions">
          <button onClick={onClose}>Cancel</button>
          <button onClick={handleJoin}>Join</button>
        </div>
      </StyledPopup>
    </Modal>
  );
};

export default JoinDebateRoomPopup;
