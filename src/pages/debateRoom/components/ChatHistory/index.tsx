import { observer } from 'mobx-react-lite';
import styled from 'styled-components';
import { useClient } from '../../provider/ClientProvider';

const StyledChatHistory = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;

  overflow-y: auto;

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

function ChatHistory() {
  const client = useClient();

  const { messages } = client;

  return (
    <StyledChatHistory>
      {messages.map((message, index) => {
        return (
          <div
            key={index}
            className={
              message.senderUserId === -1
                ? 'center'
                : message.senderAgree
                  ? 'left'
                  : 'right'
            }
          >
            {message.senderUserId === -1 ? (
              <StyledNotify>{message.message}</StyledNotify>
            ) : (
              <StyledMessage key={index}>
                <div className="user-name">{message.senderUserName}</div>
                <div className="message">{message.message}</div>
              </StyledMessage>
            )}
          </div>
        );
      })}
    </StyledChatHistory>
  );
}

export default observer(ChatHistory);
