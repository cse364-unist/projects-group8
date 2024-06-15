import { observer } from 'mobx-react-lite';
import styled from 'styled-components';

import GrayPerson from './GrayPerson.svg';
import { DebateRoom } from '../../../../models/DebateRoom';

const StyledChatHistory = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  padding: 36px 40px;

  overflow-y: auto;
  gap: 22px;

  min-height: 0;

  & .left {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
  }

  & .right {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
  }

  & .center {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: center;
  }
`;

const StyledMessage = styled.div`
  width: 606px;
  max-width: 100%;

  border-radius: 24px;

  background-color: #ffffff;

  display: flex;
  flex-direction: column;
  align-items: flex-start;

  box-sizing: border-box;

  padding: 10px 20px;

  & > .user-name {
    font-size: 12px;
    color: #494949;
    margin-bottom: 14px;
  }

  & > .message {
    font-size: 16px;
    color: #494949;
  }
`;

const StyledNotify = styled.div`
  width: 100%;
  text-align: center;

  font-size: 16px;
  color: black;
`;

const StyledHeader = styled.div`
  display: flex;
  justify-content: space-between;
  flex-direction: row;
  align-items: center;

  & > .member-count {
    color: #7b7b7b;
    font-size: 14px;
    font-weight: medium;
    display: flex;
    align-items: center;
    gap: 10px;

    & > .count {
      display: flex;
      flex-direction: row;
      gap: 2px;
      align-items: center;
    }
  }
`;

function ChatHistory({ debateRoom }: { debateRoom: DebateRoom }) {
  const { chats, agreeJoinedUserNumber, disagreeJoinedUserNumber } = debateRoom;

  return (
    <StyledChatHistory>
      <StyledHeader>
        <div className="member-count">
          <div>AGREEMENT</div>
          <div className="count">
            <img src={GrayPerson} alt="person" />
            <div>{agreeJoinedUserNumber}</div>
          </div>
        </div>
        <div className="member-count">
          <div>OPPOSITION</div>
          <div className="count">
            <img src={GrayPerson} alt="person" />
            <div>{disagreeJoinedUserNumber}</div>
          </div>
        </div>
      </StyledHeader>

      {chats.map((chat, index) => {
        return (
          <div
            key={index}
            className={
              chat.chatType === 'MODERATE'
                ? 'center'
                : chat.chatType === 'AGREE'
                  ? 'left'
                  : 'right'
            }
          >
            {chat.chatType === 'MODERATE' ? (
              <StyledNotify>{chat.message}</StyledNotify>
            ) : (
              <StyledMessage key={index}>
                <div className="user-name">{chat.userName}</div>
                <div className="message">{chat.message}</div>
              </StyledMessage>
            )}
          </div>
        );
      })}
    </StyledChatHistory>
  );
}

export default observer(ChatHistory);
