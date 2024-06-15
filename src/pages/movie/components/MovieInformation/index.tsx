import { useRecoilValue } from 'recoil';
import { moviePageMovieSelector } from '../../../../states/MoviePageState';
import styled from 'styled-components';
import ContentArea from '../../../../components/ContentArea';

import StarIcon from './StarIcon.svg';

const StyledMovieInformation = styled.div`
  width: 100%;
  height: 558px;
  overflow: hidden;
  position: relative;


  .backblur {
    width: 100%;
    height: 100%;
    object-fit: cover;
    position: absolute;
    z-index: 0;
    top: 0;
    left: 0;
    filter: blur(200px);
    --webkit-filter: blur(200px);
    clip-path: polygon(0 0, 100% 0, 100% 100%, 0 100%);
  }

  .color-overlay {
    width: 100%;
    height: 100%;
    background: radial-gradient(
      circle,
      rgba(0, 0, 0, 0.8) 0%,
      rgba(0, 0, 0, 0.6) 100%
    );
    position: absolute;
    z-index: 1;
    top: 0;
    left: 0;
  }

  .main {
    position: relative;
    z-index: 2;
    width: 100%;
    height: 100%;
  }
}
`;

const StyledInner = styled.div`
  width: 100%;
  height: 100%;

  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 116px;

  & > img {
    width: 281px;
    height: 379px;
    object-fit: cover;
    border-radius: 14px;
    background-color: white;
    drop-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  }

  & > div {
    display: flex;
    flex-direction: column;
    gap: 47px;
  }

  & .title {
    display: flex;
    flex-direction: column;
    color: white;
    gap: 14px;

    & > h1 {
      font-size: 48px;
      font-weight: bold;
      margin: 0;
      padding: 0;
    }

    & > .details {
      display: flex;
      gap: 16px;
      flex-direction: row;
      align-items: center;
      gap: 8px;
    }

    & > .details > .rating {
      display: flex;
      gap: 4px;
      align-items: center;

      & > img {
        width: 16px;
        height: 16px;
      }
    }

    & > .details > .circle {
      width: 2px;
      height: 2px;
      border-radius: 50%;
      background-color: white;
    }
  }

  & .description {
    width: 100%;
    color: white;
    font-size: 18px;
    line-height: 24px;
  }
`;

export default function MovieInformation() {
  const movie = useRecoilValue(moviePageMovieSelector);
  return (
    <StyledMovieInformation>
      <img
        className="backblur"
        src={movie.thumbnailUrl}
        alt={`${movie.title} Thumbnail`}
      />
      <div className="color-overlay" />
      <div className="main">
        <ContentArea fitHeight>
          <StyledInner>
            <img src={movie.thumbnailUrl} alt={`${movie.title} Thumbnail`} />
            <div>
              <div className="title">
                <h1>{movie.title}</h1>
                <div className="details">
                  <div className="rating">
                    <img src={StarIcon} alt="Star Icon" />
                    <span>{movie.avgRating}</span>
                  </div>
                  <div className="circle" />
                  <span>{movie.genre}</span>
                </div>
              </div>
              <div className="description">{movie.description}</div>
            </div>
          </StyledInner>
        </ContentArea>
      </div>
    </StyledMovieInformation>
  );
}
