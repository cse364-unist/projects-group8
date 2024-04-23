# Movin Project
## features
1. real-time **web socket chatting service** (debateRoom)
2. **GPT moderator** who summarize opinions between agree and disagree (**need openai.api.key**)
3. vote & game money distribution service (if agree win, people, who voted agree, will get more money)

## Not features (but required for testing)
1. login authentication
2. swagger ui 
3. web socket programming ui 

- you can test the server in swagger (http://localhost:8080/swagger-ui/index.html)
- you can test the websocket programming in the react-prototype (..)

our project is Movie based discussion service. Additionally, vote & game money service will be provided by server. 

## user API
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
## debateRoom API
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
## movie API
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

## chat-gpt API 
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

## chat API (just for testing)
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

## authentication API
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
