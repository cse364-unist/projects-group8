import { atom, selector, useRecoilState } from 'recoil';
import { Movie, SimpleMovie } from '../models/Movie';
import { getMoviesForMainPage, searchMovies } from '../services/MovieService';
import { useCallback } from 'react';

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
});

export function useSetKeyword() {
  const [mainPage, setMainPageState] = useRecoilState(mainPageState);

  return useCallback(
    async (keyword: string) => {
      const { searchKeyword } = mainPage;

      if (searchKeyword === keyword) {
        return;
      }

      const firstResult = await searchMovies(searchKeyword, 1);

      setMainPageState({
        searchKeyword: keyword,
        searchPage: 1,
        searchReachedEnd: firstResult.length === 0,
        searchResult: firstResult,
      });
    },
    [mainPage, setMainPageState],
  );
}

export function useSearchTrigger() {
  const [mainPage, setMainPageState] = useRecoilState(mainPageState);

  return useCallback(async () => {
    const { searchKeyword, searchPage, searchReachedEnd, searchResult } =
      mainPage;

    if (searchReachedEnd) {
      return;
    }

    const newResult = await searchMovies(searchKeyword, searchPage + 1);

    setMainPageState({
      searchKeyword: searchKeyword,
      searchPage: searchPage + 1,
      searchReachedEnd: newResult.length === 0,
      searchResult: [...searchResult, ...newResult],
    });
  }, [mainPage, setMainPageState]);
}

export const movieSearchResultSelector = selector<SimpleMovie[]>({
  key: 'movieSearchResult',
  get: ({ get }) => get(mainPageState).searchResult,
});
