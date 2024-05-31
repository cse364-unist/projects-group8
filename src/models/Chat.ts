export default interface Chat {
  id: number;
  debateRoomId: number;
  userId: number;
  userName: string;
  message: string;
  chatType: 'AGREE' | 'DISAGREE' | 'MODERATE';
  date: Date;
}
