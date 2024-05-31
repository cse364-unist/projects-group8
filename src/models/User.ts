import { DebateRoomWithUserInformation } from './DebateRoom';

export default interface User {
  id: number;
  name: string;
  money: number;
  lastAttendance: Date | null;
  joinedDebateRooms: DebateRoomWithUserInformation[];
}

export const emptyUser: User = {
  id: 0,
  name: '',
  money: 0,
  lastAttendance: null,
  joinedDebateRooms: [],
};
