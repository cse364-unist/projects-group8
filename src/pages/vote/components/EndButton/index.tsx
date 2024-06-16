import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { endDebateRoom } from '../../../../services/DebateRoomService';
import { useRecoilValue } from 'recoil';
import { votePageDebateRoomIdSelector } from '../../../../states/VotePageState';

const StyledButton = styled.button`
  border-rounded: 14px;

  background-color: #f4f4f4;
  border: 1px solid #d9d9d9;

  &:hover {
    background-color: #e9e9e9;
  }

  &:active {
    background-color: #d9d9d9;
  }

  padding: 10px 20px;
`;

export default function EndButton() {
  const debateRoomId = useRecoilValue(votePageDebateRoomIdSelector);
  const navigate = useNavigate();

  const handleOnClick = () => {
    endDebateRoom(debateRoomId)
      .then(() => {
        alert('Debate room ended');
        navigate(`/`, {
          replace: true,
        });
      })
      .catch(() => {
        alert('Failed to end the debate room');
      });
  };

  return (
    <StyledButton type="button" onClick={handleOnClick}>
      Force End Debate Room
    </StyledButton>
  );
}
