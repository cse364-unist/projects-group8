import { useNavigate } from 'react-router-dom';
import useLogout from './hooks/useLogout';

export default function Error() {
  const logout = useLogout();
  const navigate = useNavigate();

  const handleOnClick = () => {
    logout();
    navigate('/');
  };
  return (
    <div>
      <h1>Error</h1>
      <p>There was an error</p>
      <button onClick={() => handleOnClick}>Try Logout</button>
    </div>
  );
}
