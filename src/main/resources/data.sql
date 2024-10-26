INSERT INTO users (name) VALUES ('CPU');
INSERT INTO users (name) VALUES ('ほんだ');
INSERT INTO users (name) VALUES ('いがき');
INSERT INTO matches (user1,user2,user1Hand,user2Hand,winner) VALUES (2,1,'Gu','Choki','user1の勝利');
INSERT INTO matches (user1,user2,user1Hand,user2Hand,winner) VALUES (2,1,'Gu','Gu','引き分け');
INSERT INTO matches (user1,user2,user1Hand,user2Hand,winner) VALUES (2,1,'Gu','Pa','user2の勝利');
INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (1,2,'Gu','FALSE');
INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (2,3,'Choki','FALSE');
