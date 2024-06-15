import React from 'react';
import styled from 'styled-components';

import { useRecoilValue } from 'recoil';
import {
  mainPageSearchKeywordSelector,
  useSetKeyword,
} from '../../../../states/MainPageState';

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

const SearchBox: React.FC<SearchBoxProps> = () => {
  const keyword = useRecoilValue(mainPageSearchKeywordSelector);
  const setKeyword = useSetKeyword();

  return (
    <StyledSearchBox>
      <input
        type="text"
        placeholder="Search..."
        value={keyword}
        onChange={(e) => setKeyword(e.target.value)}
      />
    </StyledSearchBox>
  );
};

export default SearchBox;
