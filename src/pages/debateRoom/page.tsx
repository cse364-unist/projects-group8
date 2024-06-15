import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import { ClientProvider } from './provider/ClientProvider';
import { useRecoilValue } from 'recoil';
import {
  authState,
  userJoinedDebateRoomsSelector,
} from '../../states/AuthState';

import Header from './components/Header';
import ChatInputBox from './components/ChatInputBox';
import ChatHistory from './components/ChatHistory';
import ContentArea from '../../components/ContentArea';

const StyledDebateRoom = styled.div`
  width: 100vw;
  height: 100vh;
  box-sizing: border-box;
  padding: 20px;

  background: linear-gradient(90deg, #f6f6f6 0%, #e9e9e9 100%);
  display: flex;
  flex-direction: column;

  & > .body {
    flex: 1;
    width: 100%;

    display: flex;
    flex-direction: column;

    & > .history-part {
      flex: 1;
    }
  }
`;

export function DebateRoom() {
  const { debateRoomId: debateRoomIdStr } = useParams<{
    debateRoomId: string;
  }>();

  const auth = useRecoilValue(authState);
  const userJoinedDebateRooms = useRecoilValue(userJoinedDebateRoomsSelector);

  const debateRoomId = parseInt(debateRoomIdStr ?? '-1', 10);

  const thisDebateRoom =
    userJoinedDebateRooms.find((room) => room.id === debateRoomId) ?? null;

  return (
    <ClientProvider
      debateRoomId={debateRoomId}
      user={auth.isAuthenticated ? auth.user : null}
      debateRoom={thisDebateRoom}
    >
      <StyledDebateRoom>
        <Header />
        <div className="body">
          <div className="history-part">
            <ContentArea fitHeight>
              <ChatHistory />
            </ContentArea>
          </div>
          <ChatInputBox />
        </div>
      </StyledDebateRoom>
    </ClientProvider>
  );
}
