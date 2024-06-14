import React, { useState, useEffect } from 'react';
import './votePage.css';
import VotePopup from './popup/VotePopup/index';
import {
  votePageDebateRoomIdSelector,
  votePageDebateRoomSelector,
  votePageUserVotedSelector,

} from '../../states/VotePageState';

import {
  DebateRoom,
  DebateRoomWithUserInformation,
  SimpleDebateRoom,
} from '../../models/DebateRoom';

import {
  getDebateRoomsByMovieId,
  voteToDebateRoom,
} from '../../services/DebateRoomService';

export default function VotePage() {
  const [dueDate, setDueDate] = useState<string | null>(null);
  const [discussionTitle, setDiscussionTitle] = useState<string | null>(null);
  const [discussionPoint, setDiscussionPoint] = useState<string | null>(null);
  const [chat, setChat] = useState<string[]>([]);
  const [agreeCount, setAgreeCount] = useState<number>(0);
  const [disagreeCount, setDisagreeCount] = useState<number>(0);

  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [modalMessage, setModalMessage] = useState<string>('');
  const [movieId, setMovieId] = useState<number>(0);
  const [debateRooms, setDebateRooms] = useState<{
    waitingForVote: SimpleDebateRoom[];
    debateOpened: SimpleDebateRoom[];
  }>({
    waitingForVote: [],
    debateOpened: [],
  });

  useEffect(() => {
    // 여기서 백엔드 API를 호출하여 데이터를 가져올 수 있습니다.
    // 예를 들어, fetch() 함수나 axios 라이브러리를 사용할 수 있습니다.
    fetchDebateRoomData();
    fetchDueDate();
    fetchDiscussionTitle();
    fetchDiscussionPoint();
    
  }, []);

  async function fetchDebateRoomData() {
    try{
      const result  = await getDebateRoomsByMovieId(movieId);
      setDebateRooms(result);
    } catch (error) {
      console.error('Error fetching getDebateRoomsByMovieId:', error);
    }
  }

  const fetchDueDate = async () => {
    try {
      const response = await fetch('/api/due-date'); // 백엔드 API 엔드포인트
      if (!response.ok) {
        throw new Error('Failed to fetch due date');
      }
      const data = await response.json();
      setDiscussionTitle(data.dueDate); // 백엔드에서 가져온 데이터 설정
    } catch (error) {
      console.error('Error fetching due date:', error);
    }
  };

  const fetchDiscussionTitle = async () => {
    try {
      const response = await fetch('/api/due-date'); // 백엔드 API 엔드포인트
      if (!response.ok) {
        throw new Error('Failed to fetch discussion title');
      }
      const disTitle = await response.json();
      setDiscussionPoint(disTitle.title); // 백엔드에서 가져온 데이터 설정
    } catch (error) {
      console.error('Error fetching discution title:', error);
    }
  };

  const fetchDiscussionPoint = async () => {
    try {
      const response = await fetch('/api/due-date'); // 백엔드 API 엔드포인트
      if (!response.ok) {
        throw new Error('Failed to fetch discussion point');
      }
      const point = await response.json();
      setDueDate(point.topic); // 백엔드에서 가져온 데이터 설정
    } catch (error) {
      console.error('Error fetching discussion point:', error);
    }
  };

  const fetchChat = async () => {
    try {
      const response = await fetch('/api/chat'); // 백엔드 API 엔드포인트
      if (!response.ok) {
        throw new Error('Failed to fetch chat');
      }
      const chat = await response.json();
      setChat(chat); // 백엔드에서 가져온 데이터 설정
    } catch (error) {
      console.error('Error fetching chat:', error);
    }
  };

  const fetchAgreeCount = async () => {
    try {
      const response = await fetch('/api/agree-count'); // 백엔드 API 엔드포인트
      if (!response.ok) {
        throw new Error('Failed to fetch agree count');
      }
      const count = await response.json();
      setAgreeCount(count); // 백엔드에서 가져온 데이터 설정
      setDisagreeCount(count); // 백엔드에서 가져온 데이터 설정
    } catch (error) {
      console.error('Error fetching agree count:', error);
    }
  };

  const handleAgree = () => {
    setModalMessage('AGREE');
    setIsModalOpen(true);
  };

  const handleDisagree = () => {
    setModalMessage('DISAGREE');
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className="container">
      <div className="section section-1">
        <div className="section-1-1">
          <h2>Vote</h2>
          <p>Vote due date: {dueDate}</p>
        </div>
        <div className="section-1-2">
          <h2>Title</h2>
          <p>{discussionTitle}</p>
          <h2>Discussion Point</h2>
          <p>{discussionPoint}</p>
        </div>
        <div className="section-1-3">
          <p>Please read the debate record on the right carefully and click the 'agree' or 'disagree' button.</p>
          <button onClick={handleAgree} className="vote-button agree-button">
            AGREE
          </button>
          <button onClick={handleDisagree} className="vote-button disagree-button">
            DISAGREE
          </button>
        </div>
      </div>
      <div className="section section-2">
        <div className="vote-counts">
          <div className="agree-count">AGREEMENT: {agreeCount}</div>
          <div className="disagree-count">DISAGREEMET: {disagreeCount}</div>
        </div>
        <p>Start the DISCCUSSION!</p>
        <p>The opening statements from the affirmative side will now begin. Affirmative participants, please proceed with your individual opening statements.</p>
        <p>{chat}</p>
      </div>

      {isModalOpen && (
        <VotePopup message={modalMessage} onClose={closeModal}/>
      )}
    </div>
  );
}
