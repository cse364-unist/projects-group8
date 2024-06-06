import React, { useState } from 'react';
import './SearchBox.css';

interface SearchBoxProps {
  onSearch: (keyword: string) => void;
}

const SearchBox: React.FC<SearchBoxProps> = ({ onSearch }) => {
  const [keyword, setKeyword] = useState('');

  const handleSearch = () => {
    onSearch(keyword);
  };

  return (
    <div className="search-box">
      <input
        type="text"
        placeholder="Search..."
        value={keyword}
        onChange={(e) => setKeyword(e.target.value)}
      />
      <button onClick={handleSearch}>Search</button>
    </div>
  );
};

export default SearchBox;