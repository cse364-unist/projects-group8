export interface SimpleMovieDto {
  id: number;
  thumbnailUrl: string;
  name: string;
  year: number;
}

export interface MovieDto {
  id: number;
  title: string;
  genre: string;
  avgRating: number;
  thumbnailUrl: string;
  description: string;
  year: number;
}
