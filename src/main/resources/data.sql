INSERT INTO users (name) VALUES ('CPU');
INSERT INTO users (name) VALUES ('ほんだ');
INSERT INTO users (name) VALUES ('いがき');

INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive,winner) VALUES (2,1,'Gu','Choki','FALSE','user1の勝利');
INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive,winner) VALUES (2,1,'Gu','Gu','FALSE','引き分け');
INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive,winner) VALUES (2,1,'Gu','Pa','FALSE','user2の勝利');

INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (1,2,'Gu','FALSE');
INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (2,3,'Choki','FALSE');
