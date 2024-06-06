import { useRecoilValue } from 'recoil';
import { moviePageMovieSelector } from '../../../../states/MoviePageState';
import './style.css';

export default function MovieInformation() {
  const movie = useRecoilValue(moviePageMovieSelector);
  return (
    <div className="movie-information">
      <img src={movie.thumbnailUrl} alt={`${movie.title} Thumbnail`} />
      <h1>{movie.title}</h1>
      <p>{movie.description}</p>
    </div>
  );
}