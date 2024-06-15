import { atom } from 'recoil';

export interface IRegisterPageState {
  name: string;
  email: string;
  password: string;
  confirmPassword: string;
  error: string | null;
}

const initialState: IRegisterPageState = {
  name: '',
  email: '',
  password: '',
  confirmPassword: '',
  error: null,
};

export const registerPageState = atom<IRegisterPageState>({
  key: 'registerPageState',
  default: initialState,
});
