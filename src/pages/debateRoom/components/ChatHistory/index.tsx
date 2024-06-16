import { observer } from 'mobx-react-lite';
import styled from 'styled-components';
import { useClient } from '../../provider/ClientProvider';
import LeaveButton from '../LeaveButton';
import { useEffect } from 'react';

const StyledChatHistory = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  padding: 20px;

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

  &.error {
    color: red;
  }

  &.notify {
    color: blue;
  }
`;

function ChatHistory() {
  const client = useClient();

  const { messages } = client;

  // scroll to bottom if it is near the bottom
  useEffect(() => {
    const chatHistory = document.getElementById('chat-history');
    if (
      chatHistory &&
      chatHistory.scrollHeight -
        chatHistory.scrollTop -
        chatHistory.clientHeight <
        100
    ) {
      chatHistory.scrollTop = chatHistory.scrollHeight;

      if (client.curStep === 7) {
        chatHistory.scrollTop = chatHistory.scrollHeight + 100;
      }
    }
  }, [messages.length, client.curStep]);

  return (
    <StyledChatHistory id="chat-history">
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
      {client.curStep === 7 && (
        <>
          <div className="center">
            <StyledNotify className="notify">
              Debate is over. Vote page is opened.
            </StyledNotify>
          </div>
          <div className="center">
            <LeaveButton />
          </div>
        </>
      )}
      {client.errorMessage && (
        <div className="center">
          <StyledNotify className="error">{client.errorMessage}</StyledNotify>
        </div>
      )}
    </StyledChatHistory>
  );
}

export default observer(ChatHistory);
