import styled from 'styled-components';
import CreateNewDiscussionPopup from '../../popup/CreateNewDiscussionPopup';
import { createDebateRoom } from '../../../../services/DebateRoomService';
import { useState } from 'react';
import { useRecoilState } from 'recoil';
import { moviePageMovieIdSelector } from '../../../../states/MoviePageState';

import Background from './background.png';

const StyledButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px 20px;
  font-size: 16px;
  color: #000;
  background: url('${Background}') no-repeat center/cover; // 이미지 경로를 설정해주세요
  border: 1px solid #ccc;
  border-radius: 50px;
  cursor: pointer;
  outline: none;
  width: 760px;
  height: 100px;
  font-size: 22px;
  font-weight: bold;

  &:hover {
    filter: brightness(0.9);
  }

  &:active {
    filter: brightness(0.8);
  }
`;

export default function CreateButton() {
  const [isCreatePopupOpen, setCreatePopupOpen] = useState(false);
  const [movieId] = useRecoilState(moviePageMovieIdSelector);

  const handleCreateNewDiscussion = async (
    title: string,
    points: string,
    startTime: Date,
  ) => {
    console.log('handleCreateNewDiscussion:', title, points, startTime); // 디버깅을 위해 로그 추가
    try {
      await createDebateRoom(title, points, startTime, movieId);
      console.log('New discussion created:', { title, points, startTime });
      // 성공 후 로직 추가 (예: 상태 업데이트, 메시지 표시 등)
    } catch (error) {
      console.error('Error creating new discussion:', error);
    }
  };

  return (
    <>
      <StyledButton>+ Create New Discussion</StyledButton>
      {isCreatePopupOpen && (
        <CreateNewDiscussionPopup
          onClose={() => setCreatePopupOpen(false)}
          onCreate={handleCreateNewDiscussion}
        />
      )}
    </>
  );
}
