import ChatDto from './ChatDto';
import { SimpleMovieDto } from './MovieDto';

export interface DebateRoomDto {
  id: number;
  title: string;
  topic: string;
  state: 'OPEN' | 'DISCUSS' | 'VOTE' | 'CLOSE';
  movie: SimpleMovieDto;
  startTime: string;
  duration: number;
  maxUserNumber: number;
  agreeJoinedUserNumber: number;
  disagreeJoinedUserNumber: number;
}

export interface DebateRoomVoteDto {
  id: number;
  voted: boolean;
  voteAgree: boolean;
  joined: boolean;
  agree: boolean;
  movie: SimpleMovieDto;
  title: string;
  topic: string;
  stateType: 'OPEN' | 'DISCUSS' | 'VOTE' | 'CLOSE';
  startTime: string;
  duration: number;
  maxUserNumber: number;
  agreeJoinedUserNumber: number;
  disagreeJoinedUserNumber: number;
}

export interface DebateRoomChatDto {
  id: number;
  voted: boolean;
  voteAgree: boolean;
  joined: boolean;
  agree: boolean;
  movie: SimpleMovieDto;
  title: string;
  topic: string;
  stateType: 'OPEN' | 'DISCUSS' | 'VOTE' | 'CLOSE';
  startTime: string;
  duration: number;
  maxUserNumber: number;
  agreeJoinedUserNumber: number;
  disagreeJoinedUserNumber: number;
  summarize: string | null;
  chats: ChatDto[];
}