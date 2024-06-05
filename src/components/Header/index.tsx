import styled from 'styled-components';
import ContentArea from '../ContentArea';
import { useRecoilValue } from 'recoil';
import { isAuthenticatedState } from '../../states/AuthState';

const StyledHeader = styled.header`
  width: 100%;

  height: 52px;

  .inner {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }
`;

export default function Header() {
  const isAuthenticated = useRecoilValue(isAuthenticatedState);

  return (
    <StyledHeader>
      <ContentArea fitHeight>
        <div className="inner">
          <div>Logo</div>
          {isAuthenticated ? <div>Logout</div> : <div>Login</div>}
        </div>
      </ContentArea>
    </StyledHeader>
  );
}
