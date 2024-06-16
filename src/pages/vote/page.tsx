import { useEffect, useRef } from 'react';
import {
  useSetVotePageDebateRoomId,
  votePageDebateRoomSelector,
} from '../../states/VotePageState';

import { useParams } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import styled from 'styled-components';
import VoteActions from './components/VoteActions';
import ChatHistory from './components/ChatHistory';
import EndButton from './components/EndButton';

const StyledVotePage = styled.div`
  display: flex;
  height: 100vh;

  .section-1 {
    flex: 1;
    display: flex;
    flex-direction: column;
    border-right: 1px solid #d9d9d9;
    box-shadow: 4px 0 8px 0 rgba(0, 0, 0, 0.04);
    z-index: 1;
    overflow-y: auto;
    min-width: 300px;
  }

  .section-1 .section-1-1 {
    display: flex;
    flex-direction: column;
    border-bottom: 1px solid #d9d9d9;
    padding: 28px 36px;

    h2 {
      margin: 0;
      font-size: 22px;
      font-weight: bold;
      margin-bottom: 24px;
    }
  }

  .section-1 .section-1-2 {
    display: flex;
    flex-direction: column;
    border-bottom: 1px solid #d9d9d9;
    padding: 28px 36px;
    gap: 24px;
  }

  .section-1 .section-1-3 {
    display: flex;
    flex-direction: column;
    padding: 28px 36px;
    border-bottom: 1px solid #d9d9d9;
  }

  .section-1 .section-1-4 {
    display: flex;
    flex-direction: column;
    padding: 28px 36px;
    border-bottom: 1px solid #d9d9d9;
  }

  .section-2 {
    flex: 3;
    background-color: #f3f3f3;
    text-align: center;
    min-width: 0;
  }

  .vote-button {
    padding: 10px 20px;
    margin: 5px;
    font-size: 16px;
    cursor: pointer;
  }

  .agree-button {
    background-color: #4caf50;
    color: white;
    border: none;
  }

  .disagree-button {
    background-color: #f44336;
    color: white;
    border: none;
  }

  .vote-counts {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }

  .agree-count,
  .disagree-count {
    font-weight: bold;
    padding: 12px;
  }

  .chat-list {
    width: 100%;
    max-width: 600px;
  }

  .chat-list ul {
    list-style-type: none;
    padding: 0;
  }

  .chat-list li {
    padding: 10px;
    border-bottom: 1px solid #ccc;
  }

  .chat-list strong {
    font-weight: bold;
  }

  .chat-list em {
    font-size: 0.9em;
    color: #666;
  }
`;

const StyledKeyAndValue = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;

  & .key {
    font-size: 16px;
    color: #6e6e6e;
  }

  & .value {
    font-size: 16px;
    font-weight: bold;
    color: black;
  }
`;

export default function VotePage() {
  const { debateRoomId } = useParams<{ debateRoomId: string }>();

  const LastDebateRoomIdRef = useRef<number | null>(null);

  const setDebateRoomId = useSetVotePageDebateRoomId();

  const debateRoom = useRecoilValue(votePageDebateRoomSelector);

  useEffect(() => {
    const id = Number(debateRoomId);
    if (LastDebateRoomIdRef.current !== id) {
      LastDebateRoomIdRef.current = id;
      setDebateRoomId(id);
    }
  }, [debateRoomId, setDebateRoomId, debateRoom.id]);

  const dueDate = new Date(
    debateRoom.startTime.getTime() + new Date(0, 0, 2).getTime(),
  );

  return (
    <StyledVotePage>
      <div className="section section-1">
        <div className="section-1-1">
          <h2>Vote</h2>
          <div>Vote due date: {dueDate.toLocaleString()}</div>
        </div>
        <div className="section-1-2">
          <StyledKeyAndValue>
            <div className="key">Title</div>
            <div className="value">{debateRoom.title}</div>
          </StyledKeyAndValue>
          <StyledKeyAndValue>
            <div className="key">Discussion Proposition</div>
            <div className="value">{debateRoom.topic}</div>
          </StyledKeyAndValue>
        </div>
        <div className="section-1-3">
          <p>
            Please read the debate record on the right carefully and click the
            'agree' or 'disagree' button.
          </p>
          <VoteActions />
        </div>
        <div className="section-1-4">
          <p>
            This is the button for demo. If the user clicks the button, the
            debate room vote will be closed, and the game money will be
            distributed to the users.
          </p>
          <EndButton />
        </div>
      </div>
      <div className="section section-2">
        <ChatHistory debateRoom={debateRoom} />
      </div>
    </StyledVotePage>
  );
}
