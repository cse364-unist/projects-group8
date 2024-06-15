import React, { Suspense } from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';
import HeaderLayout from './pages/HeaderLayout';
import LoginPage from './pages/login/page';
import RegisterPage from './pages/register/page';
import MainPage from './pages/main/page';
import MyPage from './pages/my/page';
import VotePage from './pages/vote/page';
import MoviePage from './pages/movie/page';

const router = createBrowserRouter([
  {
    path: '/',
    element: <HeaderLayout />,
    children: [
      {
        path: '/',
        element: <MainPage />,
      },
      {
        path: '/login',
        element: <LoginPage />,
      },
      {
        path: '/register',
        element: <RegisterPage />,
      },
      {
        path: '/my',
        element: <MyPage />,
      },
      {
        path: '/vote',
        element: <VotePage />,
      },
      {
        path: '/movie/:movieId',
        element: <MoviePage />,
      },
    ],
  },
]);

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement,
);
root.render(
  <React.StrictMode>
    <RecoilRoot>
      <Suspense>
        <RouterProvider router={router} />
      </Suspense>
    </RecoilRoot>
  </React.StrictMode>,
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
