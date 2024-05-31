import queryString from 'query-string';
import { useLocation, useNavigate } from 'react-router-dom';

export default function useLogout() {
  const navigate = useNavigate();
  const location = useLocation();

  return () => {
    const query = queryString.parse(location.search);

    const newQuery = { ...query };
    newQuery.logout = 'true';
    const newSearch = queryString.stringify(newQuery);
    navigate(
      {
        pathname: location.pathname,
        search: newSearch,
      },
      { replace: true },
    );
  };
}
