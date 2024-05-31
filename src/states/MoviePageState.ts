import { atom, selector } from 'recoil';
import { Movie, emptyMovie } from '../models/Movie';
import { getMovieById } from '../services/MovieService';
import { getDebateRoomsByMovieId } from '../services/DebateRoomService';
import { SimpleDebateRoom } from '../models/DebateRoom';

interface IMoviePageState {
  movieId: number;
}

const initialState: IMoviePageState = {
  movieId: 0,
};

const moviePageState = atom<IMoviePageState>({
  key: 'moviePageAtom',
  default: initialState,
});

export const moviePageMovieIdSelector = selector<number>({
  key: 'moviePageMovieIdSelector',
  get: ({ get }) => get(moviePageState).movieId,
  set: ({ set }, newMovieId) => {
    if (typeof newMovieId !== 'number' || newMovieId <= 0) {
      throw new Error('Invalid movie ID');
    }

    set(moviePageState, { movieId: newMovieId });
  },
});

export const moviePageMovieSelector = selector<Movie>({
  key: 'moviePageMovieSelector',
  get: async ({ get }) => {
    const movieId = get(moviePageMovieIdSelector);

    if (movieId <= 0) {
      return emptyMovie;
    }

    const result = await getMovieById(movieId);

    return result;
  },
});

const moviePageDebateRoomIdSelector = selector<{
  waitingForVote: SimpleDebateRoom[];
  debateOpened: SimpleDebateRoom[];
}>({
  key: 'moviePageDebateRoomIdSelector',
  get: async ({ get }) => {
    const movieId = get(moviePageMovieIdSelector);

    if (movieId <= 0) {
      return {
        waitingForVote: [],
        debateOpened: [],
      };
    }

    const result = await getDebateRoomsByMovieId(movieId);

    return result;
  },
});

export const moviePageWaitingForVoteDebateRoomSelector = selector<
  SimpleDebateRoom[]
>({
  key: 'moviePageWaitingForVoteDebateRoomSelector',
  get: ({ get }) => get(moviePageDebateRoomIdSelector).waitingForVote,
});

export const moviePageDebateOpenedDebateRoomSelector = selector<
  SimpleDebateRoom[]
>({
  key: 'moviePageDebateOpenedDebateRoomSelector',
  get: ({ get }) => get(moviePageDebateRoomIdSelector).debateOpened,
});
