import { observer } from 'mobx-react-lite';
import styled from 'styled-components';

import GrayPerson from './GrayPerson.svg';
import { useClient } from '../../provider/ClientProvider';
import ContentArea from '../../../../components/ContentArea';
import { stepName } from '../../store/Client';
import { useEffect, useState } from 'react';

const StyledHeader = styled.div`
  width: 100%;
  height: 76px;
  background-color: #313131;
  border-radius: 24px;
  flex-shrink: 0;

  & .inner {
    height: 100%;
    display: flex;
    justify-content: space-between;
    flex-direction: row;
    align-items: center;

    & > .member-count {
      color: #ffffff7f;
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

    & > .main {
      display: flex;
      flex-direction: row;
      align-items: center;
      gap: 36px;

      & > .divider {
        width: 1px;
        height: 16px;
        background-color: #ffffff;
      }

      & > .key-and-value {
        display: flex;
        flex-direction: row;
        align-items: center;
        gap: 14px;

        & > div:first-child {
          color: #a8a8a8;
          font-size: 18px;
          font-weight: medium;
        }

        & > div:last-child {
          color: #ffffff;
          font-size: 24px;
          font-weight: medium;
        }
      }
    }
  }
`;

const Timer = observer(() => {
  const { stepEndTime } = useClient();

  const [now, setNow] = useState(new Date());

  useEffect(() => {
    const interval = setInterval(() => {
      setNow(new Date());
    }, 1000);

    return () => {
      clearInterval(interval);
    };
  }, []);

  const diff = now.getTime() - stepEndTime.getTime();

  let minutes = Math.floor(diff / 60000);
  let seconds = Math.floor((diff % 60000) / 1000);

  if (minutes < 0) {
    minutes = 0;
  }

  if (seconds < 0) {
    seconds = 0;
  }

  const minutesStr = minutes.toString().padStart(2, '0');
  const secondsStr = seconds.toString().padStart(2, '0');

  return (
    <>
      {minutesStr}:{secondsStr}
    </>
  );
});

function Header() {
  const client = useClient();

  return (
    <StyledHeader>
      <ContentArea fitHeight>
        <div className="inner">
          <div className="member-count">
            <div>AGREEMENT</div>
            <div className="count">
              <img src={GrayPerson} alt="Gray Person" />
              <div>{client?.debateRoom.agreeJoinedUserNumber}</div>
            </div>
          </div>
          <div className="main">
            <div className="key-and-value">
              <div>NOW:</div>
              <div>{stepName[client.curStep]}</div>
            </div>
            <div className="divider" />
            <div className="key-and-value">
              <div>TIMELEFT:</div>
              <div>{<Timer />}</div>
            </div>
          </div>
          <div className="member-count">
            <div>OPPOSITION</div>
            <div className="count">
              <img src={GrayPerson} alt="Gray Person" />
              <div>{client?.debateRoom.disagreeJoinedUserNumber}</div>
            </div>
          </div>
        </div>
      </ContentArea>
    </StyledHeader>
  );
}

export default observer(Header);
