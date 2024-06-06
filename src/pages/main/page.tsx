import React from 'react';
import Hero from './components/Hero';
import BigMovieItems from './components/BigMovieItems';
import JoinedDebateRoomItem from './components/JoinedDebateRoomItem';
import SearchBox from './components/SearchBox';
import './style.css';

const MainPage: React.FC = () => {
  return (
    <div className="main-page">
      {/* Hero Section */}
      <Hero />

      {/* Discussion Rooms Section */}
      <section className="discussion-rooms">
        <h2>Discussion Rooms</h2>
        <div className="room-list">
          <JoinedDebateRoomItem />
          <JoinedDebateRoomItem />
          <JoinedDebateRoomItem />
        </div>
      </section>

      {/* Trending Active Rooms Section */}
      <section className="trending-rooms">
        <h2>Trending Active Rooms</h2>
        <div className="room-list">
          <BigMovieItems />
          <BigMovieItems />
          <BigMovieItems />
        </div>
      </section>

      {/* Explore Section */}
      <section className="explore">
        <h2>Explore</h2>
        <SearchBox />
        <div className="explore-list">
          <BigMovieItems />
          <BigMovieItems />
          <BigMovieItems />
        </div>
      </section>
    </div>
  );
}

export default MainPage;