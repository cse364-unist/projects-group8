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

export const mainPageDebatingMoviesSelector = selector<SimpleMovie[]>({
  key: 'mainPageDebatingMovies',
  get: async ({ get }) => get(mainPageDisplayMoviesSelector).debatingMovies,
});

export const mainPagePopularMoviesSelector = selector<SimpleMovie[]>({
  key: 'mainPagePopularMovies',
  get: async ({ get }) => get(mainPageDisplayMoviesSelector).popularMovies,
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

const mainPageState = atom<IMainPageState>({
  key: 'mainPageState',
  default: initialState,
});

export const mainPageSearchKeywordSelector = selector<string>({
  key: 'mainPageSearchKeyword',
  get: ({ get }) => get(mainPageState).searchKeyword,
  set: async ({ set, get }, newKeyword) => {
    const { searchKeyword } = get(mainPageState);

    if (searchKeyword === newKeyword) {
      return;
    }

    if (typeof newKeyword !== 'string') {
      throw new Error('Invalid search keyword');
    }

    const firstResult = await searchMovies(searchKeyword, 1);

    set(mainPageState, {
      searchKeyword: newKeyword,
      searchPage: 1,
      searchReachedEnd: firstResult.length === 0,
      searchResult: firstResult,
    });
  },
});

export const mainPageSearchTriggerSelector = selector<undefined>({
  key: 'mainPageSearchTrigger',
  get: () => undefined,
  set: async ({ set, get }) => {
    const { searchKeyword, searchPage, searchReachedEnd, searchResult } =
      get(mainPageState);

    if (searchReachedEnd) {
      return;
    }

    const newResult = await searchMovies(searchKeyword, searchPage + 1);

    set(mainPageState, {
      searchKeyword: searchKeyword,
      searchPage: searchPage + 1,
      searchReachedEnd: newResult.length === 0,
      searchResult: [...searchResult, ...newResult],
    });
  },
});

export const movieSearchResultSelector = selector<SimpleMovie[]>({
  key: 'movieSearchResult',
  get: ({ get }) => get(mainPageState).searchResult,
});
