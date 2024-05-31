import { SimpleMovieDto } from './MovieDto';

export default interface DebateRoomDto {
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
