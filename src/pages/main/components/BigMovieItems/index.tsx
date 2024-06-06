import React from 'react';
import './BigMovieItems.css';

const BigMovieItems: React.FC = () => {
  return (
    <div className="big-movie-item">
      <img src="./movinImg.png" alt="Movie Poster" />
      <h3>Movie Title</h3>
    </div>
  );
}

export default BigMovieItems;