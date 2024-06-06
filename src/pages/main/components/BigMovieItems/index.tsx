import React from 'react';
import { Link } from 'react-router-dom';
import './BigMovieItems.css';

interface BigMovieItemsProps {
  movie: {
    id: number;
    title: string;
    thumbnailUrl: string;
  };
}

const BigMovieItems: React.FC<BigMovieItemsProps> = ({ movie }) => {
  return (
    <Link to={`/movie/${movie.id}`} className="big-movie-item">
      <img src={movie.thumbnailUrl} alt={`${movie.title} Poster`} />
      <h3>{movie.title}</h3>
    </Link>
  );
}

export default BigMovieItems;