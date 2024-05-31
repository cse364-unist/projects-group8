import { DebateRoomVoteDto } from './DebateRoomVoteDto';

export default interface UserDto {
  id: number;
  name: string;
  money: number;
  lastAttendance: string | null;
  joinedDebateRooms: DebateRoomVoteDto[];
}
