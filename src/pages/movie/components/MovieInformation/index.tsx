import { useRecoilValue } from 'recoil';
import { moviePageMovieSelector } from '../../../../states/MoviePageState';

export default function MovieInformation() {
  const movie = useRecoilValue(moviePageMovieSelector);
  return <div />;
}
