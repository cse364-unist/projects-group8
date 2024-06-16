import { observer } from 'mobx-react-lite';
import ContentArea from '../../../../components/ContentArea';
import styled from 'styled-components';
import SendIcon from './SendIcon.svg';
import { useClient } from '../../provider/ClientProvider';

const StyledInputBox = styled.div<{
  disabled: boolean;
}>`
  width: 100%;
  height: 136px;
  background-color: #ffffff;
  border: 1px solid #cccccc;
  box-shadow: 0px 4px 14px rgba(0, 0, 0, 0.08);
  border-radius: 24px;
  box-sizing: border-box;
  padding: 24px;

  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 14px;

  pointer-events: ${(props) => (props.disabled ? 'none' : 'auto')};
  opacity: ${(props) => (props.disabled ? 0.5 : 1)};

  & > div {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 8px;

    & > div {
      font-size: 12px;
      color: #494949;
    }

    & > textarea {
      width: 100%;
      flex: 1;
      border: none;
      border-radius: 14px;
      padding: 16px;
      font-size: 16px;
      resize: none;
      box-sizing: border-box;
    }
  }

  & > button {
    padding: 18px 20px;
    background-color: #313131;
    color: #ffffff;
    display: flex;
    border-radius: 14px;
    align-items: center;
    gap: 8px;
    font-size: 16px;
    font-weight: bold;
  }

  & > button:hover {
    background-color: #4a4a4a;
  }

  & > button:active {
    background-color: #1f1f1f;
  }
`;

function ChatInputBox() {
  const client = useClient();

  return (
    <ContentArea>
      <StyledInputBox disabled={!client.getIsChatAvailable()}>
        <div>
          <div>
            You are: {client.debateRoom.agree ? 'Agreement' : 'Disagreement'} |{' '}
            {client.user.name}
          </div>
          <textarea
            placeholder="Type your message here"
            value={client.message}
            onChange={(e) => {
              client.setMessage(e.target.value);
            }}
          />
        </div>
        <button
          onClick={() => {
            client.sendMessage();
          }}
        >
          <img src={SendIcon} alt="Send" />
          Send
        </button>
      </StyledInputBox>
    </ContentArea>
  );
}

export default observer(ChatInputBox);
