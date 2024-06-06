import React from 'react';
import './SearchBox.css';

const SearchBox: React.FC = () => {
  return (
    <div className="search-box">
      <input type="text" placeholder="Search..." />
      <button>Search</button>
    </div>
  );
}

export default SearchBox;