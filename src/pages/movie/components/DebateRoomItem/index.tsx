import styled from 'styled-components';
import { SimpleDebateRoom } from '../../../../models/DebateRoom';

import NextIcon from './NextIcon.svg';
import PersonIcon from './PersonIcon.svg';

const StyledRoomItem = styled.button`
  box-sizing: border-box;
  width: 588px;
  height: 160px;
  padding: 28px 24px;
  border: 1px solid #d9d9d9;
  border-radius: 14px;
  cursor: pointer;
  display: flex;
  flex-direction: row;
  align-items: center;
  background-color: #fbfbfb;

  &:hover {
    background-color: #f4f4f4;
  }

  &:active {
    background-color: #e9e9e9;
  }

  & .inner {
    height: 100%;
    flex: 1;
    color: black;
    font-size: 14px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: start;

    & h3 {
      font-size: 16px;
      font-weight: bold;
      margin: 0;
    }
  }

  & .next {
    width: 56px;
    height: 56px;
    border-radius: 50%;
    background-color: #f4f4f4;
    display: flex;
    justify-content: center;
    align-items: center;
    line-height: 1;

    & img {
      width: 24px;
      height: 24px;
    }
  }

  & .divider {
    height: 8px;
    width: 1px;
    background-color: #d9d9d9;
  }
`;

export default function DebateRoomItem({
  debateRoom,
  onClick,
}: {
  debateRoom: SimpleDebateRoom;
  onClick: () => void;
}) {
  return (
    <StyledRoomItem type="button" onClick={onClick}>
      <div className="inner">
        <div
          style={{
            display: 'flex',
            flexDirection: 'column',
            gap: 14,
            alignItems: 'start',
          }}
        >
          <h3>{debateRoom.title}</h3>
          <div>Proposition: {debateRoom.topic}</div>
        </div>
        <div style={{ display: 'flex', alignItems: 'center', gap: 4 }}>
          <div>Agreement</div>
          <div style={{ display: 'flex', alignItems: 'center', gap: 2 }}>
            <img src={PersonIcon} alt="person" />
            {debateRoom.agreeJoinedUserNumber}
          </div>
          <div className="divider" />
          <div>Opposition</div>
          <div style={{ display: 'flex', alignItems: 'center', gap: 2 }}>
            <img src={PersonIcon} alt="person" />
            {debateRoom.disagreeJoinedUserNumber}
          </div>
        </div>
      </div>
      <div className="next">
        <img src={NextIcon} alt="next" />
      </div>
    </StyledRoomItem>
  );
}
