import { Outlet } from 'react-router-dom';
import Header from '../components/Header';
import useAutoLoginOnStart from '../hooks/useAutoLoginOnStart';
import useLogoutWithUrl from '../hooks/useLogoutWithUrl';

export default function HeaderLayout() {
  useAutoLoginOnStart();
  useLogoutWithUrl();

  return (
    <main>
      <Header />
      <Outlet />
    </main>
  );
}
