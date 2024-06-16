import api from '../config/api';
import { Movie, SimpleMovie } from '../models/Movie';
import { MovieDto, SimpleMovieDto } from '../models/dto/MovieDto';

export async function getMoviesForMainPage(): Promise<{
  debatingMovies: Movie[];
  popularMovies: Movie[];
}> {
  const result = (await api.get('/movies/mainPage')).data;

  return {
    debatingMovies: result.debateMovies.map(movieDtoToMovie),
    popularMovies: result.popularMovies.map(movieDtoToMovie),
  };
}

export async function getMovieById(id: number): Promise<Movie> {
  const result = (await api.get(`/movies/${id}`)).data;

  return movieDtoToMovie(result);
}

export async function searchMovies(
  query: string,
  page: number, // Start from 1~
): Promise<SimpleMovie[]> {
  const result = (
    await api.post('/movies/search', {}, { params: { keyword: query, page } })
  ).data.movies;

  return result.map(simpleMovieDtoToSimpleMovie);
}

// Converts

export function simpleMovieDtoToSimpleMovie(dto: SimpleMovieDto): SimpleMovie {
  return {
    id: dto.id,
    thumbnailUrl: dto.thumbnailUrl,
    title: dto.name,
    year: dto.year,
  };
}

export function movieDtoToMovie(dto: MovieDto): Movie {
  return {
    id: dto.id,
    thumbnailUrl: dto.thumbnailUrl,
    title: dto.title,
    genre: dto.genre,
    avgRating: dto.avgRating,
    description: dto.description,
    year: dto.year,
  };
}
