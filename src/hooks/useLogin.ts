import { useRecoilState } from 'recoil';
import { loginPageState } from '../states/LoginPageState';
import { login } from '../services/AuthenticationService';

export default function useLogin() {
  const [loginPage, setLoginPage] = useRecoilState(loginPageState);

  return () => {
    new Promise((resolve) =>
      login(loginPage.email, loginPage.password)
        .then(() => {
          setLoginPage((prev) => ({
            ...prev,
            error: null,
          }));
          resolve(null);
        })
        .catch((error) => {
          setLoginPage((prev) => ({
            email: '',
            password: '',
            error: 'Invalid email or password',
          }));
          resolve(null);
        }),
    ).then(() => {});
  };
}
