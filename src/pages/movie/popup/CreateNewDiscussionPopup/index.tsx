import React, { useState } from 'react';
import styled from 'styled-components';
import Modal from '../../../../components/Modal';

import { createDebateRoom } from '../../../../services/DebateRoomService';
import { useRecoilValue } from 'recoil';
import { moviePageMovieIdSelector } from '../../../../states/MoviePageState';
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
  const navigate = useNavigate();

  const handleSubmit = () => {
    const date = new Date(startTime);
    createDebateRoom(title, points, date, movieId)
      .then((id) => {
        onClose();
        navigate(0);
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
