export interface SimpleMovie {
  id: number;
  thumbnailUrl: string;
  title: string;
  year: number;
}

export interface Movie extends SimpleMovie {
  genre: string;
  avgRating: number;
  description: string;
}

export const emptySimpleMovie: SimpleMovie = {
  id: 0,
  thumbnailUrl: '',
  title: '',
  year: 0,
};

export const emptyMovie: Movie = {
  ...emptySimpleMovie,
  genre: '',
  avgRating: 0,
  description: '',
};
