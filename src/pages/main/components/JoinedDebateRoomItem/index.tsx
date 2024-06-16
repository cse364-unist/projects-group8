import { DebateRoomWithUserInformation } from '../../../../models/DebateRoom';
import styled from 'styled-components';
import PersonIcon from './PersonIcon.svg';
import CalendarIcon from './CalendarIcon.svg';
import { useNavigate } from 'react-router-dom';

const StyledContainer = styled.div`
  width: 675px;
  height: 186px;

  display: flex;
  flex-direction: row;
  align-items: center;

  border-radius: 14px;

  padding: 20px;

  gap: 27px;

  box-sizing: border-box;

  background-color: #fbfbfb;
  border: 1px solid #d9d9d9;
  box-shadow: 0px 4px 14px rgba(0, 0, 0, 0.04);

  & > img {
    width: 108px;
    height: 145px;
    border-radius: 14px;
  }

  & > div {
    flex: 1;
    height: 100%;
    box-sizing: border-box;
    padding: 8px 0px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    font-size: 14px;
    font-weight: 400;

    & > div:first-child {
      display: flex;
      flex-direction: column;

      & > h3 {
        font-size: 16px;
        font-weight: bold;
        margin: 0px;
        margin-bottom: 8px;
      }

      & .time {
        display: flex;
        gap: 4px;
        align-items: center;

        margin-bottom: 18px;

        & > img {
          width: 16px;
          height: 16px;
        }
      }

      & p {
        margin: 0px;
        font-size: 14px;
        font-weight: 400;
      }
    }

    & > div:last-child {
      display: flex;
      flex-direction: row;
      align-items: center;
      gap: 4px;

      & .divider {
        height: 8px;
        width: 1px;
        background-color: #d9d9d9;
      }
    }
  }
`;

const StyledGoInButton = styled.button`
  width: 106px;
  height: 58px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 14px;

  color: black;
  border: 1px solid #d9d9d9;
  background-color: #ffffff;
  font-size: 14px;
  font-weight: bold;

  &:disabled {
    background-color: transparent;
    opacity: 0.5;
  }

  &:hover {
    background-color: #f5f5f5;
  }

  &:active {
    background-color: #e9e9e9;
  }
`;

function JoinedDebateRoomItem({
  debateRoom,
}: {
  debateRoom: DebateRoomWithUserInformation;
}) {
  // convert startTime to string  like 2024-04-02 18:02
  const startTime = new Date(debateRoom.startTime);
  const navigate = useNavigate();
  const startTimeStr = `${startTime.getFullYear()}-${
    startTime.getMonth() + 1
  }-${startTime.getDate()} ${startTime.getHours()}:${startTime.getMinutes()}`;

  const isStarted = new Date() > startTime;

  const handleOnClickGoIn = () => {
    navigate(`/debateRoom/${debateRoom.id}`);
  };

  return (
    <StyledContainer>
      <img
        src={debateRoom.movie.thumbnailUrl}
        alt={`${debateRoom.movie.title} Poster`}
      />
      <div>
        <div>
          <h3>{debateRoom.title}</h3>
          <div className="time">
            <img src={CalendarIcon} alt="calendar" />
            <p>{startTimeStr}</p>
          </div>
          <p>Proposition: {debateRoom.topic}</p>
        </div>
        <div>
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
      <StyledGoInButton disabled={!isStarted} onClick={handleOnClickGoIn}>
        {isStarted ? 'START!' : 'NOT YET'}
      </StyledGoInButton>
    </StyledContainer>
  );
}

export default JoinedDebateRoomItem;
