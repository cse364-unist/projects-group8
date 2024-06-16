import React from 'react';
import styled from 'styled-components';
import './Hero.css';
import BackgroundImg from './movinImg.png';
import ContentArea from '../../../../components/ContentArea';

import Hero1 from './hero1.png';
import Hero2 from './hero2.png';
import Hero3 from './hero3.png';
import JoinMessage from './join.png';
import RightIcon from './RightIcon.svg';

const StyledHero = styled.div`
  background: radial-gradient(
      circle,
      rgba(0, 0, 0, 0.7) 60%,
      rgba(0, 0, 0, 0.5) 100%
    ),
    url('${BackgroundImg}');
  background-size: cover;
  background-position: center;
  text-align: center;
  height: 685px;
  color: white;

  display: flex;
  justify-content: center;

  & h1 {
    font-size: 3em;
    margin-bottom: 20px;
  }

  & p {
    font-size: 1.5em;
    margin-bottom: 20px;
  }

  & button {
    font-size: 1.2em;
    padding: 10px 20px;
    background-color: #ff4500;
    border: none;
    color: white;
    cursor: pointer;
  }

  & .inner {
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: start;
  }

  & .inner > img:nth-child(1) {
    height: 18px;
    margin-bottom: 30px;
  }

  & .inner > img:nth-child(2) {
    height: 58px;
    margin-bottom: 68px;
  }

  & .inner > img:nth-child(3) {
    height: 74px;
    margin-bottom: 82px;
  }

  & button {
    drop-filter: blur(8px);
    --webkit-filter: blur(8px);

    background-color: #ffffff0a;
    border: 1px solid #ffffff14;
    border-radius: 45px;
    box-sizing: border-box;

    display: flex;
    flex-direction: row;
    align-items: center;

    padding: 28px 40px;

    &:hover {
      background-color: #ffffff1a;
    }

    &:active {
      background-color: #ffffff1f;
    }

    & img:nth-child(1) {
      height: 31px;
      object-fit: contain;
    }

    & img:nth-child(2) {
      height: 32px;
      object-fit: contain;
      margin-left: 14px;
    }
  }
`;

const Hero: React.FC = () => {
  const handleOnClick = () => {
    window.scrollTo({
      top: 800,
      behavior: 'smooth',
    });
  };

  return (
    <StyledHero>
      <ContentArea fitHeight>
        <div className="inner">
          <img src={Hero1} alt="hero1" />
          <img src={Hero2} alt="hero2" />
          <img src={Hero3} alt="hero3" />
          <button type="button" onClick={handleOnClick}>
            <img src={JoinMessage} alt="join" />
            <img src={RightIcon} alt="right" />
          </button>
        </div>
      </ContentArea>
    </StyledHero>
  );
};

export default Hero;
