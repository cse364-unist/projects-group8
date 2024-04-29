# Movin Project
## 1. Key Features
1. real-time **web socket debating service** (debateRoom)
2. **GPT moderator** who summarize opinions between agree and disagree (**need openai.api.key**)
3. vote & game money distribution service (if agree win, people, who voted agree, will get more money)

## 2. Not Key Features, But Implemented for necessity
1. login authentication
2. swagger ui 
3. front-end webpage for testing web socket related features (real-time chatting for debating) 

- you can test the server in swagger (http://localhost:8080/swagger-ui/index.html)
- you can test the websocket programming in the react-prototype

our project is Movie based discussion service. Additionally, vote & game money service will be provided by server. 

# Not Key Feature APIs
## 1. user API
Firstly, user should register their ID into server and login. (swagger can help this stage)
- **localhost:8080/users/register**\
request : 
<pre>
<code>
{
  "userName": "string",
  "password": "string",
  "email": "string"
  }
</code>
</pre>
response : 
<pre>
<code>
{
  "id": 0,
  "name": "string",
  "joinedDebateRooms": [
  {
  "voted": true,
  "voteAgree": true,
  "joined": true,
  "agree": true,
  "movie": {
  "id": 0,
  "thumbnailUrl": "string",
  "name": "string"
  },
  "title": "string",
  "topic": "string",
  "stateType": "OPEN",
  "startTime": "2024-04-23T07:28:33.552Z",
  "duration": 0,
  "maxUserNumber": 0,
  "agreeJoinedUserNumber": 0,
  "disagreeJoinedUserNumber": 0,
  "summarize": "string",
  "chat": {
  "id": 0,
  "debateRoomId": 0,
  "userId": 0,
  "userName": "string",
  "message": "string",
  "chatType": "AGREE",
  "date": "2024-04-23T07:28:33.552Z"
  }
  }
  ],
  "money": 0,
  "lastAttendance": "2024-04-23T07:28:33.552Z"
  }
</code>
</pre>
- **localhost:8080/users/my**\
request : no parameter (when already authenticated)\
response : 
<pre>
<code>
{
  "id": 1,
  "name": "string",
  "joinedDebateRooms": [],
  "money": 0,
  "lastAttendance": null
  }
</code>
</pre>

**you should authenticate by using this jwt. insert the jwt token into "Authorize" button.**
## 2. debateRoom API
movie is preloaded when the server is built. (movieId = 1 or 2 is occupied by them)

- **localhost:8080/debateRooms/{id}/vote**\
request body:
<pre>
<code>
  {
  "vote": true
  }
</code>
</pre>
response body:
<pre>
<code>
  {
  "voted": true,
  "voteAgree": true,
  "joined": true,
  "agree": true,
  "movie": {
  "id": 0,
  "thumbnailUrl": "string",
  "name": "string"
  },
  "title": "string",
  "topic": "string",
  "stateType": "OPEN",
  "startTime": "2024-04-23T07:40:33.544Z",
  "duration": 0,
  "maxUserNumber": 0,
  "agreeJoinedUserNumber": 0,
  "disagreeJoinedUserNumber": 0,
  "summarize": "string",
  "chat": {
  "id": 0,
  "debateRoomId": 0,
  "userId": 0,
  "userName": "string",
  "message": "string",
  "chatType": "AGREE",
  "date": "2024-04-23T07:40:33.545Z"
  }
  }
</code>
</pre>
- **localhost:8080/debateRooms/{id}/join**\
request body:
<pre>
<code>
  {
  "agree": true
  }
</code>
</pre>
response body:
<pre>
<code>
  {
  "voted": true,
  "voteAgree": true,
  "joined": true,
  "agree": true,
  "movie": {
  "id": 0,
  "thumbnailUrl": "string",
  "name": "string"
  },
  "title": "string",
  "topic": "string",
  "stateType": "OPEN",
  "startTime": "2024-04-23T07:42:00.469Z",
  "duration": 0,
  "maxUserNumber": 0,
  "agreeJoinedUserNumber": 0,
  "disagreeJoinedUserNumber": 0,
  "summarize": "string",
  "chat": {
  "id": 0,
  "debateRoomId": 0,
  "userId": 0,
  "userName": "string",
  "message": "string",
  "chatType": "AGREE",
  "date": "2024-04-23T07:42:00.469Z"
  }
  }
</code>
</pre>
- **localhost:8080/debateRooms/{id}/end**\
request parameter(path variable): existing debateRoomId\
response body:
<pre>
<code>
  {
  "vote": true,
  "agree": true,
  "title": "string",
  "topic": "string",
  "state": "OPEN",
  "startTime": "2024-04-23T07:42:44.362Z",
  "duration": 0,
  "maxUserNumber": 0,
  "agreeJoinedUserNumber": 0,
  "disagreeJoinedUserNumber": 0,
  "summarize": "string",
  "chats": [
  {
  "id": 0,
  "debateRoomId": 0,
  "userId": 0,
  "userName": "string",
  "message": "string",
  "chatType": "AGREE",
  "date": "2024-04-23T07:42:44.362Z"
  },...
  ]
  }
</code>
</pre>
- **localhost:8080/debateRooms/create**\
request body:
<pre>
<code>
  {
  "title": "string",
  "topic": "string",
  "startTime": "2024-04-23T07:34:00.042Z",
  "movieId": 1
  }
</code>
</pre>
response body : 1 (or any long number which is newly created debateRoom's id)

- **localhost:8080/debateRooms**\
request : request parameter should be existing movieId\
response body:
<pre>
<code>
  {
  "additionalProp1": [
  {
  "id": 0,
  "title": "string",
  "topic": "string",
  "stateType": "OPEN",
  "movieId": 0,
  "startTime": "2024-04-23T07:45:16.730Z",
  "duration": 0,
  "maxUserNumber": 0,
  "agreeJoinedUserNumber": 0,
  "disagreeJoinedUserNumber": 0,
  "totalMoney": 0,
  "summarize": "string"
  }
  ],
  "additionalProp2": [
  {
  "id": 0,
  "title": "string",
  "topic": "string",
  "stateType": "OPEN",
  "movieId": 0,
  "startTime": "2024-04-23T07:45:16.730Z",
  "duration": 0,
  "maxUserNumber": 0,
  "agreeJoinedUserNumber": 0,
  "disagreeJoinedUserNumber": 0,
  "totalMoney": 0,
  "summarize": "string"
  }
  ],
  "additionalProp3": [
  {
  "id": 0,
  "title": "string",
  "topic": "string",
  "stateType": "OPEN",
  "movieId": 0,
  "startTime": "2024-04-23T07:45:16.730Z",
  "duration": 0,
  "maxUserNumber": 0,
  "agreeJoinedUserNumber": 0,
  "disagreeJoinedUserNumber": 0,
  "totalMoney": 0,
  "summarize": "string"
  }
  ]
  }
</code>
</pre>
- **localhost:8080/debateRooms/{id}**\
request parameter(path variable) : debateRoomId\
response body :
<pre>
<code>
  {
  "voted": true,
  "voteAgree": true,
  "joined": true,
  "agree": true,
  "movie": {
  "id": 0,
  "thumbnailUrl": "string",
  "name": "string"
  },
  "title": "string",
  "topic": "string",
  "stateType": "OPEN",
  "startTime": "2024-04-23T07:46:46.455Z",
  "duration": 0,
  "maxUserNumber": 0,
  "agreeJoinedUserNumber": 0,
  "disagreeJoinedUserNumber": 0,
  "summarize": "string",
  "chats": [
  {
  "id": 0,
  "debateRoomId": 0,
  "userId": 0,
  "userName": "string",
  "message": "string",
  "chatType": "AGREE",
  "date": "2024-04-23T07:46:46.455Z"
  }
  ]
  }
</code>
</pre>
## 3. movie API
- **localhost:8080/movies/search**\
request body:
<pre>
<code>
  {
  "keyword": "string",
  "page": 0
  }
</code>
</pre>
response body:
<pre>
<code>
  {
  "additionalProp1": [
  {
  "id": 0,
  "thumbnailUrl": "string",
  "name": "string"
  }
  ],
  "additionalProp2": [
  {
  "id": 0,
  "thumbnailUrl": "string",
  "name": "string"
  }
  ],
  "additionalProp3": [
  {
  "id": 0,
  "thumbnailUrl": "string",
  "name": "string"
  }
  ]
  }
</code>
</pre>
- **localhost:8080/movies/{id}**\
request parameter(path variable) : existing movie's id\
response body :
<pre>
<code>
  {
  "id": 0,
  "title": "string",
  "genre": "string",
  "avgRating": 0,
  "thumbnailUrl": "string",
  "description": "string"
  }
</code>
</pre>
- **localhost:8080/movies/mainPage**\
request : no parameter\
response :
<pre>
<code>
  {
  "additionalProp1": [
  {
  "id": 0,
  "title": "string",
  "genre": "string",
  "avgRating": 0,
  "thumbnailUrl": "string",
  "description": "string"
  }
  ],
  "additionalProp2": [
  {
  "id": 0,
  "title": "string",
  "genre": "string",
  "avgRating": 0,
  "thumbnailUrl": "string",
  "description": "string"
  }
  ],
  "additionalProp3": [
  {
  "id": 0,
  "title": "string",
  "genre": "string",
  "avgRating": 0,
  "thumbnailUrl": "string",
  "description": "string"
  }
  ]
  }
</code>
</pre>

## 4. chat-gpt API 
- **localhost:8080/chats/summarize**\
request body :
<pre>
<code>
  {
  "debateRoomId": 0
  }
</code>
</pre>
response body : list of string where first is agree summary & second is disagree summary
<pre>
<code>
  [
  "agree opinions' summary",
  "disagree opinions' summary"
  ]
</code>
</pre>

## 5. chat API (just for testing)
- **localhost:8080/chats/create**\
request body :
<pre>
<code>
  {
  "debateRoomId": 0,
  "message": "string",
  "chatType": "AGREE"
  }
</code>
</pre>
response body : 1 (just long number which is newly created chat's id)

## 6. authentication API
- **localhost:8080/auth/v1/login**\
request body :
<pre>
<code>
  {
  "userName": "string",
  "password": "string"
  }
</code>
</pre>
response body: (token is **jwt**)
<pre>
<code>
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dLCJzdWIiOiJzdHJpbmciLCJpYXQiOjE3MTM4NTczNjQsImV4cCI6MTcxMzg2MDk2NH0.H1rMhD4-DgQgIcKGvKutrDSd1EAtxY3r36YRfJG3l94"
  } 
</code>
</pre>

# Feature1: Real-time debating
실시간으로 진행되는 토론 기능 구현을 위해 web socket 통신을 사용하였습니다.
여러 client가 동시에 접속하여 정의한 프로토콜에 따라 통신을 해야하기에, swagger나 command line으로 테스트하기 어렵습니다.
따라서, real-time web socket debating service를 테스트하기 위한 간단한 front-end 페이지를 제작했습니다.
다음은 프론트엔드 웹페이지를 실행하고, 테스트 하는 instruction입니다.

## 1. Download and run the test web page

### Pre-requirements:
node.js (https://nodejs.org/en/download), npm

### 1-1. Downlaod the `movin-debate-tester` project 

> link:
https://drive.google.com/file/d/1-YcvnWym7dW1XXMF8ysJq0USb-TD-Dv0/view?usp=sharing

### 1-2. Open the project
```shell
npm install
npm start
```

### 1-3. The web page will look like: 

![img_1.png](imgs/img_1.png)

## 2. Run the server and call some apis for the setting
We will register two users, and create a debateRoom for testing.
Then, we will join two users into the debateRoom. After that, we can join into the real-time debating.

First, run the server(back-end).
Get into the swagger ui (http://localhost:8080/swagger-ui/index.html) and call the following APIs.

**Note** : The database should be empty(initial) before following the steps.
### 2-1. Register two users
Create two users (`user1`, `user2`) by calling the following API.
![img.png](imgs/swagger_create_user2.png)
![img_1.png](imgs/swagger_create_user1.png)

### 2-2. Login for User1
Run /auth/v1/login API for user1. 
![swagger_login_user1.png](imgs/swagger_login_user1.png)

Then, you can get the jwt token.
![img.png](imgs/swagger_login_jwt_user1.png)

Click the "Authorize" button and paste the jwt token into the input box.
And click the "Authorize" button.
![img_1.png](imgs/swgger_authorize_user1.png)

### 2-3. Create a debateRoom
Call the /debateRooms/create API for creating a debateRoom.
(**movieId is 1**)
![swagger_create_debateroom.png](imgs/swagger_create_debateroom.png)

Then, you can see the new debateRoomId in the response. **Which is `1`**.
![swagger_create_debateroom_result.png](imgs/swagger_create_debateroom_result.png)

### 2-4. Join User1 into the debateRoom 1
Id is `1`, and agree is `true`.
![swagger_join_user1.png](swagger_join_user1.png)

### 2-5. Login for User2
Run /auth/v1/login API for user2.
![swagger_login_user2.png](swagger_login_user2.png)

Then, you can get the jwt token.
![swagger_login_jwt_user2.png](swagge_login_jwt_user2.png)

Click the "Authorize" button and paste the jwt token into the input box.
And click the "Authorize" button.
![swagger_authorize_user2.png](swagger_authorize_user2.png)

### 2-6. Join User2 into the debateRoom 1
Id is `1`, and agree is `false`.
![swagger_join_user2.png](swagger_join_user2.png)

## 3. Run the front-end web page
After setting the server, you can run the front-end web page.
Then, you can see the real-time debating page.

### 3-1. Run the front-end web page
Run the front-end web page by using the following command.
```shell
npm start
```

### 3-2. Open the web page
Open the web page by using the following URL.
```
http://localhost:3000
```

### 3-3. Join the debateRoom
Set the `ws path` to `ws://localhost:8080/ws/chat` (default value).
Set the `debateRoomId` to `1` (which is created above).

And set Client1 name to `user1` and password to `user1`.
And click the `로그인`(login) button of Client1.

And set Client2 name to `user2` and password to `user2`.
And click the `로그인`(login) button of Client2.

Then, you can see the real-time debating page.

![img.png](realtime_chatting.png)

### 3-4. Debating
Once every user is participated in the debateRoom, the debate will be automatically started.
In the above case, two users are joined in the debateRoom, and the debate will be started.

The debate will automatically change step by step. (agree -> disagree -> agree -> disagree -> ...)
After the debate is finished, the AI moderator will summarize the debate.

**Note:** There is a bug in the front-end page. There is count down timer, and you can ignore the prefix '540:' in the timer.

If you enter the chat, the chat will be sent to the server, and the server will broadcast the chat to all users in the debateRoom.

The two side are the distinct web socket channels, so this is the demonstration of real-time chatting.



# Feature2 : ChatGptModerator
GPT moderator summarize agree opinions & disagree opinions and then notice on debateRoom.
For each stage, moderator will collect all chats in agree & disagree side.
At that time, GPT API is used for summarizing. 
So feature 1 & feature 2 is related. \
For your stateless testing, we provide simple testing REST API for testing feature 2. These API will be work on real-time debateRoom(feature1) in same way(same method called).\
I don't explain expected response because it is explained above already.
1. login by using : **register** & **login**
- register
<pre>
<code>
curl -X POST http://localhost:8080/users/register -H 'Content-type:application/json' -d '{"userName": "string", "password": "string", "email": "string"}'
</code>
</pre>
- login
<pre>
<code>
curl -X POST http://localhost:8080/auth/v1/login -H 'Content-type:application/json' -d '{"userName": "string", "password": "string"}'
</code>
</pre>
After login, you should copy the jwt token and paste it into the "Authorize" button in swagger.
![img_2.png](imgs/img_2.png) Then, you will be authenticated by server(more opportunity for requesting other API).
2. make debateRoom by using : (movieId should be existing id)
<pre>
<code>
curl -X POST http://localhost:8080/debateRooms/create -H 'Content-type:application/json' -d '{ "title": "string", "topic": "string", "startTime": "2024-04-25T16:00:02.646Z", "movieId": 1}'
</code>
</pre>
and then newly created debateRoomId will be responded. You should remember this id for making chats.  
3. make the chat by using : 
<pre>
<code>
curl -X POST http://localhost:8080/chats/create -H 'Content-type:application/json' -d '{
  "debateRoomId": 1,
  "message": "string",
  "chatType": "AGREE"
}'
</code>
</pre>
You should make chats at least 1 agree chat & disagree chat for testing.
4. finally, summarize chats by using:
<pre>
<code>
curl -X POST http://localhost:8080/chats/summarize -H 'Content-type:application/json' -d '{"debateRoomId": 1}'
</code>
</pre>
This request will make server find all chats in specific debateRoom(id = 1) and then request twice(agree, disagree each) to GPT API by using RestTemplate to summarize chats. ("https://api.openai.com/v1/chat/completions").
For this testing, you should insert the openai.api.key = "GPT API key"(it cannot be pushed into git).
After this curl, you can get the list of two string : **agree summarize** & **disagree summarize** such as:
<pre>
<code>
[
"These opinions highlight the film's accurate depiction of the sinking of the Titanic, praising the meticulous recreation of",
"The movie distorts historical facts and exaggerates the central love story between the main characters, overshadowing"
]
</code>
</pre>
