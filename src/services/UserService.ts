import api from '../config/api';
import User from '../models/User';
import UserDto from '../models/dto/UserDto';
import { debateRoomVoteDtoToDebateRoomWithUserInformation } from './DebateRoomService';

export async function getUser(): Promise<User> {
  const result = (await api.get('/users/my')).data;

  return userDtoToUser(result);
}

export async function registerUser(
  name: string,
  email: string,
  password: string,
): Promise<User> {
  const result = (
    await api.post(
      '/users/register',
      {},
      {
        params: {
          userName: name,
          email,
          password,
        },
      },
    )
  ).data;

  return userDtoToUser(result);
}

// Converts

export function userDtoToUser(dto: UserDto): User {
  return {
    id: dto.id,
    name: dto.name,
    money: dto.money,
    lastAttendance: dto.lastAttendance ? new Date(dto.lastAttendance) : null,
    joinedDebateRooms: dto.joinedDebateRooms
      .map(
        (debateRoomVoteDto) =>
          debateRoomVoteDtoToDebateRoomWithUserInformation(debateRoomVoteDto) ??
          [],
      )
      .reverse(),
  };
}
