import React, { useState } from 'react';
import './SearchBox.css';
import styled from 'styled-components';

import SearchIcon from './SearchIcon.svg';

const StyledSearchBox = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  height: 58px;
  gap: 8px;
  margin-bottom: 36px;

  & input {
    flex: 1;
    height: 100%;
    padding: 10px;
    font-size: 1em;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
    border-radius: 50px;
    padding-left: 20px;
  }

  & button {
    width: 58px;
    height: 58px;
    border-radius: 50%;
    background-color: #fbfbfb;
    border: 1px solid #d9d9d9;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }
`;

interface SearchBoxProps {
  onSearch: (keyword: string) => void;
}

const SearchBox: React.FC<SearchBoxProps> = ({ onSearch }) => {
  const [keyword, setKeyword] = useState('');

  const handleSearch = () => {
    onSearch(keyword);
  };

  return (
    <StyledSearchBox>
      <input
        type="text"
        placeholder="Search..."
        value={keyword}
        onChange={(e) => setKeyword(e.target.value)}
      />
      <button onClick={handleSearch} type="button">
        <img src={SearchIcon} alt="Search" />
      </button>
    </StyledSearchBox>
  );
};

export default SearchBox;
