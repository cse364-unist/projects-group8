import api from '../config/api';
import Chat from '../models/Chat';
import {
  DebateRoom,
  DebateRoomWithUserInformation,
  SimpleDebateRoom,
} from '../models/DebateRoom';
import ChatDto from '../models/dto/ChatDto';
import {
  DebateRoomChatDto,
  DebateRoomDto,
  DebateRoomVoteDto,
} from '../models/dto/DebateRoomDto';
import { simpleMovieDtoToSimpleMovie } from './MovieService';

export async function getDebateRoomsByMovieId(movieId: number): Promise<{
  waitingForVote: SimpleDebateRoom[];
  debateOpened: SimpleDebateRoom[];
}> {
  const result = (
    await api.get(`debateRooms`, {
      params: {
        movieId,
      },
    })
  ).data;

  return {
    waitingForVote: result.voteDebateRooms
      .map(debateRoomDtoToSimpleDebateRoom)
      .reverse(),
    debateOpened: result.openDebateRooms
      .map(debateRoomDtoToSimpleDebateRoom)
      .reverse(),
  };
}

export async function getDebateRoomById(id: number): Promise<DebateRoom> {
  const result = (
    await api.get(`/debateRooms/${id}`, {
      withCredentials: true,
    })
  ).data;

  return debateRoomChatDtoToDebateRoom(result);
}

export async function createDebateRoom(
  title: string,
  topic: string,
  startTime: Date,
  movieId: number,
): Promise<number> {
  const id = (
    await api.post(
      '/debateRooms/create',
      {
        title,
        topic,
        startTime: startTime.toISOString(),
        movieId,
      },
      {
        withCredentials: true,
      },
    )
  ).data;

  return id;
}

export async function voteToDebateRoom(
  debateRoomId: number,
  vote: boolean,
): Promise<DebateRoomWithUserInformation> {
  const result = (
    await api.post(
      `/debateRooms/${debateRoomId}/vote`,
      {
        vote,
      },
      {
        withCredentials: true,
      },
    )
  ).data;

  return debateRoomVoteDtoToDebateRoomWithUserInformation(result);
}

export async function joinDebateRoom(
  debateRoomId: number,
  agree: boolean,
): Promise<DebateRoomWithUserInformation> {
  const result = (
    await api.post(
      `/debateRooms/${debateRoomId}/join`,
      {
        agree,
      },
      {
        withCredentials: true,
      },
    )
  ).data;

  return debateRoomVoteDtoToDebateRoomWithUserInformation(result);
}

// Converts

export function chatDtoToChat(dto: ChatDto): Chat {
  return {
    id: dto.id,
    debateRoomId: dto.debateRoomId,
    userId: dto.userId,
    userName: dto.userName,
    message: dto.message,
    chatType: dto.chatType,
    date: new Date(dto.date),
  };
}

export function debateRoomDtoToSimpleDebateRoom(
  dto: DebateRoomDto,
): SimpleDebateRoom {
  return {
    id: dto.id,
    title: dto.title,
    topic: dto.topic,
    state: dto.state,
    movie: simpleMovieDtoToSimpleMovie(dto.movie),
    startTime: new Date(dto.startTime),
    duration: dto.duration,
    maxUserNumber: dto.maxUserNumber,
    agreeJoinedUserNumber: dto.agreeJoinedUserNumber,
    disagreeJoinedUserNumber: dto.disagreeJoinedUserNumber,
  };
}

export function debateRoomVoteDtoToDebateRoomWithUserInformation(
  dto: DebateRoomVoteDto,
): DebateRoomWithUserInformation {
  return {
    id: dto.id,
    title: dto.title,
    topic: dto.topic,
    state: dto.stateType,
    movie: simpleMovieDtoToSimpleMovie(dto.movie),
    startTime: new Date(dto.startTime),
    duration: dto.duration,
    maxUserNumber: dto.maxUserNumber,
    agreeJoinedUserNumber: dto.agreeJoinedUserNumber,
    disagreeJoinedUserNumber: dto.disagreeJoinedUserNumber,

    voted: dto.voted,
    voteAgree: dto.voteAgree,
    joined: dto.joined,
    agree: dto.agree,
  };
}

export function debateRoomChatDtoToDebateRoom(
  dto: DebateRoomChatDto,
): DebateRoom {
  return {
    id: dto.id,
    title: dto.title,
    topic: dto.topic,
    state: dto.stateType,
    movie: simpleMovieDtoToSimpleMovie(dto.movie),
    startTime: new Date(dto.startTime),
    duration: dto.duration,
    maxUserNumber: dto.maxUserNumber,
    agreeJoinedUserNumber: dto.agreeJoinedUserNumber,
    disagreeJoinedUserNumber: dto.disagreeJoinedUserNumber,
    voted: dto.voted,
    voteAgree: dto.voteAgree,
    joined: dto.joined,
    agree: dto.agree,
    chats: dto.chats.map(chatDtoToChat),
  };
}
