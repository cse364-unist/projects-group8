import api from '../config/api';

export async function logout() {
  localStorage.removeItem('token');
}

export async function login(userName: string, password: string) {
  const result = (
    await api.post(
      '/auth/v1/login',
      {},
      {
        params: {
          userName: userName,
          password,
        },
      },
    )
  ).data;

  localStorage.setItem('token', result.token);
}
