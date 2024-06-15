import { useRecoilState, useSetRecoilState } from 'recoil';
import { loginPageState } from '../states/LoginPageState';
import { login } from '../services/AuthenticationService';
import { authState } from '../states/AuthState';
import { getUser } from '../services/UserService';
import { emptyUser } from '../models/User';

export default function useLogin() {
  const [loginPage, setLoginPage] = useRecoilState(loginPageState);
  const setAuth = useSetRecoilState(authState);

  return () => {
    return new Promise((resolve) =>
      login(loginPage.email, loginPage.password)
        .then(async () => {
          const user = await getUser();

          return user;
        })
        .then((user) => {
          setLoginPage((prev) => ({
            ...prev,
            error: null,
          }));
          setAuth((prev) => ({
            ...prev,
            isAuthenticated: true,
            user,
          }));
          resolve(true);
        })
        .catch((error) => {
          setLoginPage((prev) => ({
            email: '',
            password: '',
            error: 'Invalid email or password',
          }));
          setAuth((prev) => ({
            ...prev,
            isAuthenticated: false,
            user: emptyUser,
          }));
          resolve(false);
        }),
    );
  };
}
