import styled from 'styled-components';

const StyledTitle = styled.div`
  display: flex;
  flex-direction: column;
  align-items: start;

  & > .indicator {
    height: 8px;
    width: 46px;
    border-radius: 4px;
    background-color: black;
  }

  & h1 {
    font-size: 22px;
    font-weight: bold;
    margin-top: 14px;
    margin-bottom: 36px;
  }
`;

export default function Title({ text }: { text: string }) {
  return (
    <StyledTitle>
      <div className="indicator" />
      <h1>{text}</h1>
    </StyledTitle>
  );
}
