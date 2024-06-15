import { useSetRecoilState } from 'recoil';
import { authState } from '../states/AuthState';
import { getUser } from '../services/UserService';
import { emptyUser } from '../models/User';
import { useEffect, useRef } from 'react';

export default function useAutoLoginOnStart() {
  const IsFirstRef = useRef(true);
  const setAuth = useSetRecoilState(authState);

  useEffect(() => {
    if (!IsFirstRef.current) {
      return;
    }
    IsFirstRef.current = false;
    getUser()
      .then((user) => {
        setAuth((prev) => ({
          ...prev,
          isAuthenticated: true,
          user,
        }));
      })
      .catch((error) => {
        setAuth((prev) => ({
          ...prev,
          isAuthenticated: false,
          user: emptyUser,
        }));
      });
  }, [setAuth]);
}
