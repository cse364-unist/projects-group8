import React, { useState } from 'react';
import styled from 'styled-components';
import Modal from '../../../../components/Modal';

import CoinItem from './CoinItem.svg';
import { createDebateRoom } from '../../../../services/DebateRoomService';
import { useRecoilValue } from 'recoil';
import { moviePageMovieIdSelector } from '../../../../states/MoviePageState';

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

interface CreateNewDiscussionPopupProps {
  visible: boolean;
  onClose: () => void;
  onCreate: (title: string, points: string, startTime: Date) => void;
}

const CreateNewDiscussionPopup: React.FC<CreateNewDiscussionPopupProps> = ({
  visible,
  onClose,
}) => {
  const movieId = useRecoilValue(moviePageMovieIdSelector);
  const [title, setTitle] = useState('');
  const [points, setPoints] = useState('');
  const [startTime, setStartTime] = useState('');

  const handleSubmit = () => {
    const date = new Date(startTime);
    createDebateRoom(title, points, date, movieId)
      .then((id) => {
        onClose();
      })
      .catch((e) => {
        alert('Failed to create a discussion room');
      });
  };

  return (
    <Modal visible={visible} onClose={onClose}>
      <StyledPopup>
        <h2>Create New Discussion</h2>
        <StyledInputItem>
          <div>Title</div>
          <input
            type="text"
            placeholder="Title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </StyledInputItem>
        <StyledInputItem>
          <div>Disccussion points</div>
          <input
            type="text"
            placeholder="Discussion points"
            value={points}
            onChange={(e) => setPoints(e.target.value)}
          />
        </StyledInputItem>
        <StyledInputItem>
          <div>Start Time</div>
          <input
            type="datetime-local"
            value={startTime}
            onChange={(e) => setStartTime(e.target.value)}
          />
        </StyledInputItem>
        <StyledInputItem>
          <div>when you create a discussion room, you consume 100 points.</div>
          <StyledMoneyIndicator>
            <div>-</div>
            <img src={CoinItem} alt="Coin" />
            <div>100</div>
          </StyledMoneyIndicator>
        </StyledInputItem>
        <div className="actions">
          <button onClick={onClose}>Cancel</button>
          <button
            disabled={title === '' || points === '' || startTime === ''}
            onClick={handleSubmit}
          >
            Create
          </button>
        </div>
      </StyledPopup>
    </Modal>
  );
};

export default CreateNewDiscussionPopup;
