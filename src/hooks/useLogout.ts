import { useSetRecoilState } from 'recoil';
import { authState } from '../states/AuthState';
import { emptyUser } from '../models/User';

export default function useLogout() {
  const setAuth = useSetRecoilState(authState);

  return () => {
    localStorage.removeItem('token');
    setAuth((prev) => ({ ...prev, isAuthenticated: false, user: emptyUser }));
  };
}
