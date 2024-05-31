import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
});

// Add a request interceptor
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    // Do something with request error
    return Promise.reject(error);
  },
);

api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response && error.response.status === 403) {
      const token = localStorage.getItem('token');
      if (token) {
        const currentUrl = window.location.href;
        const newUrl = currentUrl.includes('?')
          ? `${currentUrl}&login=true`
          : `${currentUrl}?login=true`;
        window.location.replace(newUrl);
      }
    }
    return Promise.reject(error);
  },
);

export default api;
