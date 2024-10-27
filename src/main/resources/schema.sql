CREATE TABLE users (
    id IDENTITY,
    name VARCHAR NOT NULL
);
CREATE TABLE matches (
    id IDENTITY,
    user1 INTEGER NOT NULL,
    user2 INTEGER NOT NULL,
    user1Hand VARCHAR NOT NULL,
    user2Hand VARCHAR NOT NULL,
    isActive BOOLEAN DEFAULT FALSE,
    winner VARCHAR NOT NULL --追加
);
CREATE TABLE matchinfo (
    id IDENTITY,
    user1 INTEGER NOT NULL,
    user2 INTEGER NOT NULL,
    user1Hand VARCHAR NOT NULL,
    isActive BOOLEAN DEFAULT FALSE
)
