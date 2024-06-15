import styled from 'styled-components';
import ContentArea from '../../components/ContentArea';
import useLogin from '../../hooks/useLogin';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import { loginPageState } from '../../states/LoginPageState';
import { useNavigate } from 'react-router-dom';

const StyledLoginPage = styled.div`
  width: 100%;
  max-width: 400px;
  display: flex;
  flex-direction: column;

  gap: 14px;

  & form {
    display: flex;
    flex-direction: column;
    gap: 14px;
  }

  & input {
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 14px;
  }

  & button {
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    background-color: #f0f0f0;
    border-radius: 14px;
    cursor: pointer;
  }

  & button:disabled {
    opacity: 0.5;
  }

  & button:hover {
    background-color: #e0e0e0;
  }

  & button:active {
    background-color: #d8d8d8;
  }

  & .error {
    color: red;
  }
`;

export default function LoginPage() {
  const login = useLogin();
  const loginPage = useRecoilValue(loginPageState);
  const setLoginPage = useSetRecoilState(loginPageState);
  const navigate = useNavigate();

  const handleLogin = () => {
    login();
  };

  return (
    <ContentArea>
      <div
        style={{
          width: '100%',
          display: 'flex',
          flexDirection: 'row',
          justifyContent: 'center',
        }}
      >
        <StyledLoginPage>
          <h1>Login</h1>
          <form>
            <input
              type="email"
              placeholder="Email"
              value={loginPage.email}
              onChange={(e) => {
                setLoginPage((prev) => ({
                  ...prev,
                  email: e.target.value,
                }));
              }}
            />
            <input
              type="password"
              placeholder="Password"
              value={loginPage.password}
              onChange={(e) => {
                setLoginPage((prev) => ({
                  ...prev,
                  password: e.target.value,
                }));
              }}
            />
            <button
              type="submit"
              onClick={(e) => {
                e.preventDefault();
                handleLogin();
              }}
            >
              Login
            </button>
          </form>

          <button
            className="register"
            onClick={() => {
              navigate('/register');
            }}
          >
            Register
          </button>
          {loginPage.error && <div className="error">{loginPage.error}</div>}
        </StyledLoginPage>
      </div>
    </ContentArea>
  );
}
