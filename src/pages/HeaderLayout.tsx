import { Outlet } from 'react-router-dom';
import Header from '../components/Header';
import useAutoLoginOnStart from '../hooks/useAutoLoginOnStart';

export default function HeaderLayout() {
  useAutoLoginOnStart();

  return (
    <main>
      <Header />
      <Outlet />
    </main>
  );
}
