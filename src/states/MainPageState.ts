import { atom, selector } from 'recoil';
import { Movie, SimpleMovie } from '../models/Movie';
import { getMoviesForMainPage, searchMovies } from '../services/MovieService';

const mainPageDisplayMoviesSelector = selector<{
  debatingMovies: Movie[];
  popularMovies: Movie[];
}>({
  key: 'mainPageDisplayMovie',
  get: async () => {
    const result = await getMoviesForMainPage();
    return {
      debatingMovies: result.debatingMovies,
      popularMovies: result.popularMovies,
    };
  },
});

export const mainPageDebatingMoviesSelector = selector<Movie[]>({
  key: 'mainPageDebatingMovies',
  get: ({ get }) => get(mainPageDisplayMoviesSelector).debatingMovies,
});

export const mainPagePopularMoviesSelector = selector<Movie[]>({
  key: 'mainPagePopularMovies',
  get: ({ get }) => get(mainPageDisplayMoviesSelector).popularMovies,
});

interface IMainPageState {
  searchKeyword: string;
  searchPage: number;
  searchReachedEnd: boolean;
  searchResult: SimpleMovie[];
}

const initialState: IMainPageState = {
  searchKeyword: '',
  searchPage: 1,
  searchReachedEnd: false,
  searchResult: [],
};

export const mainPageState = atom<IMainPageState>({
  key: 'mainPageState',
  default: initialState,
});

export const mainPageSearchKeywordSelector = selector<string>({
  key: 'mainPageSearchKeyword',
  get: ({ get }) => get(mainPageState).searchKeyword,
  set: ({ set, get }, newKeyword) => {
    const state = get(mainPageState);
    if (typeof newKeyword === 'string') {
      set(mainPageState, {
        ...state,
        searchKeyword: newKeyword,
        searchPage: 1,
        searchReachedEnd: false,
        searchResult: [],
      });
    }
  },
});

export const mainPageSearchTriggerSelector = selector<undefined>({
  key: 'mainPageSearchTrigger',
  get: () => undefined,
  set: ({ set, get }) => {
    const state = get(mainPageState);
    set(mainPageState, {
      ...state,
      searchPage: state.searchPage + 1,
    });
  },
});

export const movieSearchResultSelector = selector<SimpleMovie[]>({
  key: 'movieSearchResult',
  get: ({ get }) => get(mainPageState).searchResult,
});