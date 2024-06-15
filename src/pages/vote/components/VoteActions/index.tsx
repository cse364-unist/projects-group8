import styled from 'styled-components';
import {
  votePageDebateRoomIdSelector,
  votePageDebateRoomSelector,
  votePageUserVotedSelector,
} from '../../../../states/VotePageState';
import { useRecoilValue } from 'recoil';
import CheckIcon from './CheckIcon.svg';
import { useState } from 'react';
import VotePopup from '../../popup/VotePopup';

const StyledVoteActions = styled.div`
  display: flex;
  flex-direction: column;
  gap: 11px;
`;

const StyledVoteButton = styled.button`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 18px 32px;

  background-color: #f4f4f4;
  border: 1px solid #d9d9d9;
  border-radius: 24px;
  font-size: 16px;
  font-weight: bold;

  &:hover {
    background-color: #f3f3f3;
  }

  &:active {
    background-color: #e9e9e9;
  }

  &:disabled {
    opacity: 0.5;
  }
`;

function VoteButton({
  agree,
  disabled,
  highlighted,
  onClick,
}: {
  agree: boolean;
  disabled: boolean;
  highlighted: boolean;
  onClick: () => void;
}) {
  return (
    <StyledVoteButton
      style={{
        backgroundColor: highlighted ? '#ff4d4f' : '#f3f3f3',
        color: highlighted ? 'white' : 'black',
      }}
      disabled={disabled}
      onClick={onClick}
      type="button"
    >
      {agree ? 'Agree' : 'Disagree'}
      <img src={CheckIcon} alt="check" />
    </StyledVoteButton>
  );
}

export default function VoteActions() {
  const id = useRecoilValue(votePageDebateRoomIdSelector);
  const { voted, voteAgree } = useRecoilValue(votePageUserVotedSelector);

  const [votePopup, setVotePopup] = useState({
    isOpen: false,
    roomId: 0,
    agree: false,
  });

  return (
    <StyledVoteActions>
      <VoteButton
        agree
        disabled={voted}
        highlighted={voted && voteAgree}
        onClick={() => {
          setVotePopup({ isOpen: true, roomId: id, agree: true });
        }}
      />
      <VoteButton
        agree={false}
        disabled={voted}
        highlighted={voted && !voteAgree}
        onClick={() => {
          console.log('clicked');
          setVotePopup({ isOpen: true, roomId: id, agree: false });
        }}
      />
      <VotePopup
        visible={votePopup.isOpen}
        debateRoomId={votePopup.roomId}
        agree={votePopup.agree}
        onClose={() => setVotePopup({ isOpen: false, roomId: 0, agree: false })}
      />
    </StyledVoteActions>
  );
}
