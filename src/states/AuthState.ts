import { atom, selector } from 'recoil';
import User, { emptyUser } from '../models/User';
import { DebateRoomWithUserInformation } from '../models/DebateRoom';

interface IAuthState {
  isAuthenticated: boolean;
  user: User;
}

const initialState: IAuthState = {
  isAuthenticated: false,
  user: emptyUser,
};

export const authState = atom<IAuthState>({
  key: 'authState',
  default: initialState,
});

export const isAuthenticatedState = selector<boolean>({
  key: 'isAuthenticatedState',
  get: ({ get }) => get(authState).isAuthenticated,
});

export const userState = selector<User>({
  key: 'userState',
  get: ({ get }) => get(authState).user,
});

export const userJoinedDebateRoomsSelector = selector<
  DebateRoomWithUserInformation[]
>({
  key: 'joinedDebateRooms',
  get: ({ get }) => get(authState).user.joinedDebateRooms,
  set: ({ set, get }, newDebateRooms) => {
    if (!Array.isArray(newDebateRooms)) {
      throw new Error('Invalid debate rooms');
    }

    const user = get(authState).user;

    set(authState, {
      isAuthenticated: true,
      user: {
        ...user,
        joinedDebateRooms: newDebateRooms,
      },
    });
  },
});
