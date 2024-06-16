import styled from 'styled-components';
import { useClient } from '../../provider/ClientProvider';
import { useNavigate } from 'react-router-dom';

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

export default function LeaveButton() {
  const client = useClient();
  const navigate = useNavigate();

  const goToVotePage = () => {
    client.exit();
    navigate(`/vote/${client.debateRoomId}`, {
      replace: true,
    });
  };

  return (
    <StyledButton type="button" onClick={goToVotePage}>
      Go To Vote Page
    </StyledButton>
  );
}
