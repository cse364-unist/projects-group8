import { SimpleMovie } from '../../../../models/Movie';

export interface BigMovieItemsProps {
  movie: SimpleMovie;
}

export default function BigMovieItems({ movie }: BigMovieItemsProps) {
  return (
    <div>
      <h2>{movie.title}</h2>
      <img src={movie.thumbnailUrl} alt={movie.title} />
    </div>
  );
}
