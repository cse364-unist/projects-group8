import styled from 'styled-components';
import React from 'react';
import { Link } from 'react-router-dom';

const StyledBigMovieItem = styled(Link)`
  text-align: center;
  width: 274px;
  position: relative;

  & img {
    width: 100%;
    height: 370px;
    object-fit: cover;
    border-radius: 6px;
    margin-bottom: 14px;
    background-color: #ffffff;
    box-shadow: 0px 4px 14px rgba(0, 0, 0, 0.04);
  }

  display: flex;
  flex-direction: column;
  align-items: start;

  & .title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 8px;
  }

  & .sub {
    font-size: 14px;
  }
`;
interface BigMovieItemsProps {
  movie: {
    id: number;
    title: string;
    thumbnailUrl: string;
  };
}

const BigMovieItems: React.FC<BigMovieItemsProps> = ({ movie }) => {
  return (
    <StyledBigMovieItem to={`/movie/${movie.id}`}>
      <img src={movie.thumbnailUrl} alt={`${movie.title} Poster`} />
      <div className="title">{movie.title}</div>
      <div className="sub">year</div>
    </StyledBigMovieItem>
  );
};

export default BigMovieItems;
