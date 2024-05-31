import Chat from './Chat';
import { SimpleMovie } from './Movie';

export interface SimpleDebateRoom {
  id: number;
  title: string;
  topic: string;
  state: 'OPEN' | 'DISCUSS' | 'VOTE' | 'CLOSE';
  movie: SimpleMovie;
  startTime: Date;
  duration: number;
  maxUserNumber: number;
  agreeJoinedUserNumber: number;
  disagreeJoinedUserNumber: number;
}

export interface DebateRoomWithUserInformation extends SimpleDebateRoom {
  voted: boolean;
  voteAgree: boolean;
  joined: boolean;
  agree: boolean;
}

export interface DebateRoom extends DebateRoomWithUserInformation {
  summarize: string | null;
  chats: Chat[];
}

export const emptyDebateRoom: DebateRoom = {
  id: 0,
  title: '',
  topic: '',
  state: 'OPEN',
  movie: {
    id: 0,
    title: '',
    thumbnailUrl: '',
  },
  startTime: new Date(),
  duration: 0,
  maxUserNumber: 0,
  agreeJoinedUserNumber: 0,
  disagreeJoinedUserNumber: 0,
  voted: false,
  voteAgree: false,
  joined: false,
  agree: false,
  summarize: null,
  chats: [],
};
