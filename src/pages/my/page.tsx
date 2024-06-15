import styled from 'styled-components';
import ContentArea from '../../components/ContentArea';
import { useRecoilValue } from 'recoil';
import { isAuthenticatedState, userState } from '../../states/AuthState';

import CoinIcon from './CoinIcon.svg';

const StyledMyPage = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;

  & > h1 {
    font-size: 24px;
    font-weight: bold;
    margin: 0;
  }

  & > .container {
    background-color: #f9f9f9;
    border-radius: 24px;
    display: flex;
    flex-direction: column;
    padding: 24px;
    box-sizing: border-box;
    gap: 28px;
  }
`;

const StyledNameAndValue = styled.div`
  display: flex;
  flex-direction: column;
  gap: 12px;

  & > div:first-child {
    font-size: 15px;
    color: #989898;
  }

  & > div:last-child {
    font-size: 18px;
    color: black;
  }
`;

const StyledMoneyIndicator = styled.div`
  padding: 14px 10px;
  width: 240px;
  background-color: #f4f4f4;
  border-radius: 14px;
  border: 1px solid #d9d9d9;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #4f65db;
  font-weight: medium;
  gap: 4px;
  box-shadow: inset 2px 2px 2px 0px rgba(0, 0, 0, 0.08);
  box-sizing: border-box;
  font-size: 16px;

  & div {
    font-size: 16px;
    font-weight: bold;
    color: #4f65db;
  }
`;

export default function MyPage() {
  const isAuthenticated = useRecoilValue(isAuthenticatedState);
  const user = useRecoilValue(userState);
  if (!isAuthenticated) {
    return (
      <ContentArea>
        <div style={{ height: 24 }}></div>로그인이 필요합니다.
      </ContentArea>
    );
  }

  return (
    <ContentArea>
      <StyledMyPage>
        <div style={{ height: 24 }}></div>
        <h1>My Page</h1>
        <div className="container">
          <StyledNameAndValue>
            <div>Name</div>
            <div>{user.name}</div>
          </StyledNameAndValue>
          <StyledNameAndValue>
            <div>Money</div>
            <StyledMoneyIndicator>
              <img src={CoinIcon} alt="coin" />
              <div>{user.money}</div>
            </StyledMoneyIndicator>
          </StyledNameAndValue>
        </div>
      </StyledMyPage>
    </ContentArea>
  );
}
