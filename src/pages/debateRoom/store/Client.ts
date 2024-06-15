import { makeAutoObservable, runInAction } from 'mobx';
import { baseWsUrl } from '../../../config/constants';
import User, { emptyUser } from '../../../models/User';
import {
  DebateRoomWithUserInformation,
  emptyDebateRoom,
} from '../../../models/DebateRoom';

export const stepName = [
  'Before the Debate',
  'Affirmative Constructive',
  'Negative Cross-Examination and Affirmative Answers',
  'Negative Constructive',
  'Affirmative Cross-Examination and Negative Answers',
  'Affirmative Rebuttal',
  'Negative Rebuttal',
  'End of Debate',
];

export default class Client {
  user: User;
  debateRoom: DebateRoomWithUserInformation;
  socket: WebSocket | null = null;

  isLoggedIn = false;
  token = '';

  curStep = 0;
  stepEndTime = new Date();

  state: 'waiting' | 'finished' | 'debating' = 'waiting';
  messages: {
    senderUserName: string;
    message: string;
    senderUserId: number;
    sendTime: Date;
    senderAgree: boolean;
  }[] = [];
  message = '';

  errorMessage: string | null = null;

  wsPath = '';
  debateRoomId = 0;

  constructor(
    debateRoomId: number,
    user: User | null,
    debateRoom: DebateRoomWithUserInformation | null,
  ) {
    this.user = user ?? emptyUser;
    this.wsPath = `${baseWsUrl}/ws/chat`;
    this.debateRoomId = debateRoomId;
    this.debateRoom = debateRoom ?? emptyDebateRoom;

    const token = localStorage.getItem('token');
    if (token !== null && user !== null && debateRoom !== null) {
      this.token = token;
      this.isLoggedIn = true;
      this.connect();
    } else {
      this.isLoggedIn = false;
      this.errorMessage = '로그인이 필요합니다';
    }

    makeAutoObservable(this);
  }

  setMessage(message: string) {
    this.message = message;
  }

  connect() {
    if (!this.isLoggedIn) {
      runInAction(() => {
        this.errorMessage = '로그인이 필요합니다';
        this.socket?.close();
        this.socket = null;
      });
      return;
    }

    runInAction(() => {
      this.errorMessage = null;
    });
    try {
      this.socket = new WebSocket(this.wsPath);

      // catch connection fail
      this.socket.onerror = (error) => {
        console.error('Connection failed');
        runInAction(() => {
          this.socket = null;
          this.errorMessage = `Connection failed : ${this.wsPath}`;
        });
      };

      this.socket.onopen = () => {
        console.log('Connected to server');

        this.socket!.send(
          JSON.stringify({
            type: 'ENTER',
            debateRoomId: this.debateRoomId,
            message: '',
            token: this.token,
          }),
        );
      };

      this.socket.onmessage = (event: any) => {
        const data = JSON.parse(event.data);
        console.log('Message received:', data);
        runInAction(() => {
          if (data.messageType === 'StepChange') {
            if (event.step === 7) {
              this.state = 'finished';
            } else {
              this.state = 'debating';
            }
            this.curStep = data.step;
            this.stepEndTime = new Date(data.stepEndTime);
          } else if (data.messageType === 'ENTER') {
            this.messages = [
              ...this.messages,
              {
                senderUserId: -1,
                message: data.senderUserName + ' entered the room',
                senderUserName: 'System',
                senderAgree: data.senderAgree,
                sendTime: new Date(data.sendTime),
              },
            ];
          } else {
            this.messages = [
              ...this.messages,
              {
                ...data,
                sendTime: new Date(data.sendTime),
              },
            ];
          }
        });
      };

      this.socket.onclose = () => {
        console.log('Connection closed');
        runInAction(() => {
          this.socket = null;
          this.errorMessage = 'Connection closed';
        });
      };
    } catch (e) {
      runInAction(() => {
        this.errorMessage = "Can't connect to server";
      });
    }
  }

  sendMessage() {
    this.socket?.send(
      JSON.stringify({
        type: 'TALK',
        debateRoomId: this.debateRoomId,
        message: this.message,
        token: this.token,
      }),
    );
  }

  exit() {
    this.socket?.close();
    this.socket = null;
  }
}
