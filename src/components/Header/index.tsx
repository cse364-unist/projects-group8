import styled from 'styled-components';
import ContentArea from '../ContentArea';
import { useRecoilValue } from 'recoil';
import { isAuthenticatedState } from '../../states/AuthState';
import { Link } from 'react-router-dom';

const StyledHeader = styled.header`
  width: 100vw;
  position: relative;

  height: 52px;

  .inner {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
  }

  & img.logo {
    height: 18px;
  }
  & .action {
    font-size: 15px;
    color: black;
    font-weight: medium;
  }
`;

export default function Header() {
  const isAuthenticated = useRecoilValue(isAuthenticatedState);

  return (
    <StyledHeader>
      <ContentArea fitHeight>
        <div className="inner">
          <Link to="/">
            <img className="logo" src="/logo.png" alt="logo" />
          </Link>
          {isAuthenticated ? (
            <div className="action">Logout</div>
          ) : (
            <div className="action">Login</div>
          )}
        </div>
      </ContentArea>
    </StyledHeader>
  );
}
