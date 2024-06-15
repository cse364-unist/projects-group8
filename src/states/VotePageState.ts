import { atom, selector, useSetRecoilState } from 'recoil';
import { DebateRoom, emptyDebateRoom } from '../models/DebateRoom';
import { getDebateRoomById } from '../services/DebateRoomService';

interface IVotePageState {
  debateRoomId: number;
  debateRoom: DebateRoom;
}

const initialState: IVotePageState = {
  debateRoomId: 0,
  debateRoom: emptyDebateRoom,
};

const votePageState = atom<IVotePageState>({
  key: 'votePageState',
  default: initialState,
});

export const votePageDebateRoomIdSelector = selector<number>({
  key: 'votePageDebateRoomIdSelector',
  get: ({ get }) => get(votePageState).debateRoomId,
});

export const useSetVotePageDebateRoomId = () => {
  const setVotePageState = useSetRecoilState(votePageState);

  return async (newDebateRoomId: number) => {
    const debateRoom = await getDebateRoomById(newDebateRoomId);

    setVotePageState((prev) => ({
      ...prev,
      debateRoomId: newDebateRoomId,
      debateRoom,
    }));
  };
};

export const votePageDebateRoomSelector = selector<DebateRoom>({
  key: 'votePageDebateRoomSelector',
  get: ({ get }) => get(votePageState).debateRoom,
});

export const votePageUserVotedSelector = selector<{
  voted: boolean;
  voteAgree: boolean;
}>({
  key: 'votePageUserVotedSelector',
  get: ({ get }) => {
    const debateRoom = get(votePageDebateRoomSelector);

    return {
      voted: debateRoom.voted,
      voteAgree: debateRoom.voteAgree,
    };
  },

  set: ({ set }, newData) => {
    if (
      typeof newData !== 'object' ||
      newData === null ||
      typeof (newData as any).voted !== 'boolean' ||
      typeof (newData as any).voteAgree !== 'boolean'
    ) {
      throw new Error('Invalid data');
    }

    set(votePageState, (prev) => ({
      ...prev,
      debateRoom: {
        ...prev.debateRoom,
        voted: (newData as any).voted as boolean,
        voteAgree: (newData as any).voteAgree as boolean,
      },
    }));
  },
});
