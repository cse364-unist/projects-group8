import styled from 'styled-components';
import ContentArea from '../../components/ContentArea';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import { useNavigate } from 'react-router-dom';
import { registerPageState } from '../../states/RegisterPageState';
import { registerUser } from '../../services/UserService';

const StyledRegisterPage = styled.div`
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

export default function RegisterPage() {
  const registerPage = useRecoilValue(registerPageState);
  const setRegisterPage = useSetRecoilState(registerPageState);
  const navigate = useNavigate();

  const handleRegister = () => {
    registerUser(registerPage.name, registerPage.email, registerPage.password)
      .then(() => {
        navigate('/login');
      })
      .catch((error) => {
        setRegisterPage({ ...registerPage, error: error.message });
      });
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
        <StyledRegisterPage>
          <h1>Register</h1>
          <form>
            <input
              type="text"
              placeholder="Name"
              value={registerPage.name}
              onChange={(e) => {
                setRegisterPage({ ...registerPage, name: e.target.value });
              }}
            />
            <input
              type="email"
              placeholder="Email"
              value={registerPage.email}
              onChange={(e) => {
                setRegisterPage({ ...registerPage, email: e.target.value });
              }}
            />
            <input
              type="password"
              placeholder="Password"
              value={registerPage.password}
              onChange={(e) => {
                setRegisterPage({ ...registerPage, password: e.target.value });
              }}
            />
            <input
              type="password"
              placeholder="Confirm Password"
              value={registerPage.confirmPassword}
              onChange={(e) => {
                setRegisterPage({
                  ...registerPage,
                  confirmPassword: e.target.value,
                });
              }}
            />
            <button
              type="submit"
              disabled={
                registerPage.confirmPassword !== registerPage.password ||
                registerPage.password === ''
              }
              onClick={(e) => {
                e.preventDefault();
                handleRegister();
              }}
            >
              Register
            </button>
            {registerPage.error && (
              <div className="error">{registerPage.error}</div>
            )}
          </form>
        </StyledRegisterPage>
      </div>
    </ContentArea>
  );
}
