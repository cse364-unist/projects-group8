export const baseApiUrl =
  process.env.NODE_ENV === 'development'
    ? 'http://localhost:8080'
    : 'http://localhost:8080/api';

export const baseWsUrl =
  process.env.NODE_ENV === 'development'
    ? 'ws://localhost:8080'
    : 'ws://localhost:8080/api';

export const desktopContentAreaWidth = 1200;
export const desktopContentAreaPadding = 20;
