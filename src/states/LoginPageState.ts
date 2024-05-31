import { atom } from 'recoil';

export interface ILoginPageState {
  email: string;
  password: string;
  error: string | null;
}

const initialState: ILoginPageState = {
  email: '',
  password: '',
  error: null,
};

export const loginPageState = atom<ILoginPageState>({
  key: 'loginPageState',
  default: initialState,
});
