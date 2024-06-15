import styled from 'styled-components';
import ContentArea from '../ContentArea';
import { useRecoilValue } from 'recoil';
import { isAuthenticatedState } from '../../states/AuthState';
import { Link } from 'react-router-dom';
import useLogout from '../../hooks/useLogout';

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
    text-decoration: none;
  }

  & button.action {
    border: none;
    background-color: transparent;
    cursor: pointer;
  }
`;

export default function Header() {
  const isAuthenticated = useRecoilValue(isAuthenticatedState);
  const logout = useLogout();

  const handleLogout = () => {
    logout();
  };

  return (
    <StyledHeader>
      <ContentArea fitHeight>
        <div className="inner">
          <Link to="/">
            <img className="logo" src="/logo.png" alt="logo" />
          </Link>
          {isAuthenticated ? (
            <button className="action" type="button" onClick={handleLogout}>
              Logout
            </button>
          ) : (
            <Link to="/login" className="action">
              Login
            </Link>
          )}
        </div>
      </ContentArea>
    </StyledHeader>
  );
}
