import { useLocation, useNavigate } from 'react-router-dom';
import useLogout from './useLogout';
import { useEffect } from 'react';

export default function useLogoutWithUrl() {
  const logout = useLogout();
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    if (searchParams.get('logout') === 'true') {
      logout();

      const newPathWithoutLogout =
        location.pathname + location.search.replace(/(\?|&)logout=true/, '');

      navigate(newPathWithoutLogout, {
        replace: true,
      });
    }
  }, [location, logout, navigate]);
}
