# Milestone3
## Docker Settings
you can build the image & run tomcat server in background by using 
- docker build -t milestone3 /path/to/dockerfile
- docker run -d -p 8080:8080 milestone3

NOTE) we use 
1. MySQL
2. Spring boot application
3. React for front page
4. tomcat for deployment
<img width="1512" alt="스크린샷 2024-06-16 오후 11 46 35" src="https://github.com/cse364-unist/projects-group8/assets/113850887/9ad3c91a-b91a-45f5-b93c-86b69f8760ba">


—————————————————
## Trouble on above case, you can run jar file in docker container.
At the trouble case where tomcat war file doesn't work, then you can run the run.sh in the /root/project/ in docker container. It will stop the tomcat server and run the jar file. At that time, because jar file doesn't include the frontend webpage, you should follow fe_dev instruction for testing website in web browser. This run.sh will drop the movin database and recreate database for jar application. And database's ID will be changed from "root" into "group8" which is the datasource.username in github project. (it is different from war file). you don't need to worry about anything. just run the run.sh :)

—————————————————————-
## CI - Continuous Integration
contiguous integration will be done when you 'push' or 'pull request' on master branch. it will run jacoco:report & mvn package which will test & build. you can find the CI log in the action section. You can find the workflow file in the .github/workflows folder. And in the workflow file, you can check the step name "Build and test project with Maven" which will test & build the jar file.

## Login & Register User
You can register and login in main page. \
<img width="1512" alt="KakaoTalk_20240616_193433610" src="https://github.com/cse364-unist/projects-group8/assets/113990328/bf453433-a31b-499e-8eed-3329850f97b6">
## Main Page
<img width="1512" alt="KakaoTalk_20240616_193706152" src="https://github.com/cse364-unist/projects-group8/assets/113990328/d5860448-bd8f-4c18-8b38-42873667b8ee">
<img width="1512" alt="KakaoTalk_20240616_193909966" src="https://github.com/cse364-unist/projects-group8/assets/113990328/25f1785b-77df-4dc9-a451-c3c499075c3e">
This is our main page figure.

In main page, user can access discussion room, HOT movie and Explore sections.
![alt text](image.png)


You can check the list of discussion rooms you have joined under **"Your Discussion Room"** \
You can also view a list of movies currently being discussed under **"Discussing Active Movie"**. \
You can also check out the list of trending movies based on ratings under **"HOT Movies"**. \
And also explore other movies under **"Explore"** section.

## Movie Detail page
If you click Movie thumbnail button, you will see this page.
<img width="1512" alt="KakaoTalk_20240616_193722176" src="https://github.com/cse364-unist/projects-group8/assets/113990328/f516c1fd-10ed-4cff-ad8c-508a7e1363e2">
In this page, you can participate other debate rooms and create new debate rooms. \
You can check the list of discussion rooms that are awaiting votes after the discussion has ended under **"Wating for vote"**. \
You can check the list of discussion rooms where you can participate in **"You can join"** section. \
Also you can create your debate room to **"Create new Discussion"** button.
## Join debate room popup
If you choose debate room which you want to participate in, you will see this popup.
<img width="1512" alt="KakaoTalk_20240616_193848488" src="https://github.com/cse364-unist/projects-group8/assets/113990328/e5cf0f82-ebdd-4569-bc68-f171bdc37a2a">
This popup alert you really join this debate room. \
You can join debate room by paying 100 game money.

Join In the debate room, join is a kind of reservation rather than participation. Clicking join does not mean you can participate in the discussion right away, but clicking join will make a reservation and you can see the discussion room you reserved in your discussion room on the main page. After the designated start time, real-time discussion begins when all reserved users press start and enter the discussion room.


## Debate chat room page <Feature1: Real-time debating, Feature2 : ChatGptModerator - Moderator & summary>
If you join debate room, you will chat room to debate other participants.
<img width="1512" alt="KakaoTalk_20240616_194033055" src="https://github.com/cse364-unist/projects-group8/assets/113990328/a0258726-4457-4bfb-bd1d-9106c361d14f">
In this room, participants will be assigned speaking turns by the GPT moderator, similar to a real debate. \
Agreements and Disagreements will have a designated time to express their opinions, after which further speech will not be allowed. \
Additionally, the GPT moderator will summarize both sides' views after the debate concludes.

## Create debate room popup
If you click **"Create new Discussion"** button, you will see this popup.
<img width="1512" alt="KakaoTalk_20240616_193731703" src="https://github.com/cse364-unist/projects-group8/assets/113990328/e1718c2f-fe5f-4abf-933c-dd3461d2805f">
You can enter your debate room title, discussion point and starting time.
## Vote page <Feature2 : ChatGptModerator - show Summary result>
If you click one of list in **"Wating for vote"** section, you will see this page.
<img width="1512" alt="KakaoTalk_20240616_194229869" src="https://github.com/cse364-unist/projects-group8/assets/113990328/4d31e141-ff0e-4fa0-9bf4-3707af76dadc">
You can vote for agree or disagree after reading chat logs.
## Vote page popup
If you select agree or disagree, you will see this popup.
<img width="1512" alt="KakaoTalk_20240616_194458559" src="https://github.com/cse364-unist/projects-group8/assets/113990328/ca6bc319-b3c5-4277-a3cc-a11392306978">
This popup alerts you your selection agree or disagree and you must pay 100 game money to vote in this room.
## My page <Feature3: Game Money System - check remain game money>
In this page, you can see your user name and game money.
<img width="1512" alt="KakaoTalk_20240616_194828544" src="https://github.com/cse364-unist/projects-group8/assets/113990328/36c062c7-eba6-490a-8c90-cedd9cb88526">







# Common Scenario of Movin 
0. Prerequisite
Since web cookies are shared within a single browser, it is not possible to run multiple accounts simultaneously. Therefore,  should log in with user1 in one browser and user2 in another browser (Safari and Chrome, respectively).
1. make the user1, user2 (with ID test1, test2)
<img width="1512" alt="KakaoTalk_20240616_193433610" src="https://github.com/cse364-unist/projects-group8/assets/113990328/bf453433-a31b-499e-8eed-3329850f97b6">
<img width="1512" alt="KakaoTalk_20240616_193623567" src="https://github.com/cse364-unist/projects-group8/assets/113990328/3b292089-20e3-495d-bd84-12ea5b9d9e4a">
2. click arbitrary movie which is in the movie chart
3. we click the "Escape from Shawshank" movie
<img width="1512" alt="KakaoTalk_20240616_193722176" src="https://github.com/cse364-unist/projects-group8/assets/113990328/f516c1fd-10ed-4cff-ad8c-508a7e1363e2">
4. click the "Create New Discussion" button and fill in the "title", "Discussion points", and "Start time"(if you want to test, set it to past time) 
<img width="1512" alt="KakaoTalk_20240616_193731703" src="https://github.com/cse364-unist/projects-group8/assets/113990328/e1718c2f-fe5f-4abf-933c-dd3461d2805f">
<img width="1512" alt="KakaoTalk_20240616_193804880" src="https://github.com/cse364-unist/projects-group8/assets/113990328/8a60e698-fd4a-44b9-bb02-39b303768207">

5. you can see the created New Discussion Room.
<img width="1512" alt="KakaoTalk_20240616_193816965" src="https://github.com/cse364-unist/projects-group8/assets/113990328/a5da19da-84c2-4177-9139-38d643e57de5">

6. And now user1 join the DebateRoom with Agree with deduction of 100 gold.
<img width="1512" alt="KakaoTalk_20240616_193835116" src="https://github.com/cse364-unist/projects-group8/assets/113990328/7e662b7b-f315-4189-a43c-84289245a13c">

7. And next user2 join the DebateROom with Disagree with deduction of 100 gold also.
<img width="1512" alt="KakaoTalk_20240616_193943232" src="https://github.com/cse364-unist/projects-group8/assets/113990328/e5cf0f82-ebdd-4569-bc68-f171bdc37a2a">

8. Start Debate !!! 
<img width="1512" alt="KakaoTalk_20240616_193943232" src="https://github.com/cse364-unist/projects-group8/assets/113990328/7f4452ef-d86f-4d6f-ba03-e4fdb5594ad8">

9. When the all Debate session finished, chatgpt summarizes the debate and notice debators.
<img width="1512" alt="KakaoTalk_20240616_194033055" src="https://github.com/cse364-unist/projects-group8/assets/113990328/a0258726-4457-4bfb-bd1d-9106c361d14f">

10. When you click the "Go to Vote Page" at the below of chatgpt summarization, you
can vote for this debate. In this page you can see the previous date session with summarization
<img width="1512" alt="KakaoTalk_20240616_194149736" src="https://github.com/cse364-unist/projects-group8/assets/113990328/4fe0ec7e-4d1c-4dc8-893a-1c7cff8582cd">
<img width="1512" alt="KakaoTalk_20240616_194229869" src="https://github.com/cse364-unist/projects-group8/assets/113990328/4d31e141-ff0e-4fa0-9bf4-3707af76dadc">
11. In this case, user1 votes for Agree with 100 gold deduction.
<img width="1512" alt="KakaoTalk_20240616_194238487" src="https://github.com/cse364-unist/projects-group8/assets/113990328/8f541a7f-173d-4194-bde9-8731ae6801a7">

12. In this case, user2 votes for Disagree with 100 gold deduction.
<img width="1512" alt="KakaoTalk_20240616_194458559" src="https://github.com/cse364-unist/projects-group8/assets/113990328/ca6bc319-b3c5-4277-a3cc-a11392306978">

13. user3 Registers
<img width="1512" alt="KakaoTalk_20240616_194415369" src="https://github.com/cse364-unist/projects-group8/assets/113990328/2178bfac-1fe2-4ca2-961c-f08b2e9876fa">

14. In this case, user3 votes for Disagree with 100 gold deduction.
<img width="1512" alt="KakaoTalk_20240616_194458559" src="https://github.com/cse364-unist/projects-group8/assets/113990328/ca6bc319-b3c5-4277-a3cc-a11392306978">

15. And you can see the discussion room for vote.
<img width="1512" alt="KakaoTalk_20240616_194443897" src="https://github.com/cse364-unist/projects-group8/assets/113990328/35dbbb04-0f9b-4ff1-9c5a-8ae21a5b618c">

16. Ending debaterooms and distrubte game moeny to winner team.
<img width="1512" alt="KakaoTalk_20240616_194705377" src="https://github.com/cse364-unist/projects-group8/assets/113990328/db86a6fa-f70d-4216-9928-f963d952a301">

Originally, when the time for debate participants to vote was over, a winning team was automatically determined based on the number of voters and money was distributed. However, since this is a demo application, for ease of testing, user3 used the end button to end the discussion and settle the settlement.

# Result

User1 participated as the agreejoined user, user2 participated as the disagreejoineduser, user1 voted for the agree side, user2 voted for the disagree side, and user3 voted for the disagree side.
The total money raised was 500 gold, and the disagree team won with 2 votes for the opposing side and 1 vote for the agreeing side. <br>
A total of 500 gold will be distributed 250 to each of the two members of the opposing team, user1 and user2.


you can see the game moeny on each user's my page

- user 1 : 500 - 100 - 100 => 400 
- user 2 : 500 - 100 - 100 + 250 => 550
- user 3 : 500 - 100 + 250 => 650

<img width="1512" alt="KakaoTalk_20240616_194828544" src="https://github.com/cse364-unist/projects-group8/assets/113990328/36c062c7-eba6-490a-8c90-cedd9cb88526">
<img width="1512" alt="KakaoTalk_20240616_194942063" src="https://github.com/cse364-unist/projects-group8/assets/113990328/3409f223-963a-45be-b223-e0bb880bc643">
<img width="1512" alt="KakaoTalk_20240616_194753383" src="https://github.com/cse364-unist/projects-group8/assets/113990328/f228c7ba-525d-4532-8a78-841fc6498d12">
