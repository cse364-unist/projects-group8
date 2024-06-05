import { useParams } from 'react-router-dom';

export default function MoviePage() {
  const movieId = useParams().movieId;
  console.log(movieId);

  return <div>Movie Page</div>;
}
