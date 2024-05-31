import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import queryString from 'query-string';

const useLogoutHandler = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const handleOnLogout = () => {
    localStorage.removeItem('token');
  };

  useEffect(() => {
    const query = queryString.parse(location.search);
    if (query.logout === 'true') {
      handleOnLogout();

      const newQuery = { ...query };
      delete newQuery.logout;
      const newSearch = queryString.stringify(newQuery);
      navigate(
        {
          pathname: location.pathname,
          search: newSearch,
        },
        { replace: true },
      );
    }
  }, [location, navigate]);
};

export default useLogoutHandler;
