CREATE DATABASE IF NOT EXISTS record_app;
USE record_app;

CREATE TABLE Users(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    userName VARCHAR(256) NOT NULL,
    accessToken VARCHAR(256),
    password VARCHAR(2048) NOT NULL,
    isValid BOOLEAN NOT NULL
);

CREATE TABLE RecordContent(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    recordContentName VARCHAR(256) NOT NULL,
    createUserId BIGINT NOT NULL,
    isValid BOOLEAN NOT NULL,
    FOREIGN KEY (createUserId) references Users(id)
);

CREATE TABLE Record(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    recordContentId BIGINT NOT NULL,
    recordDate DATE,
    requiredTime INT,
    createUserId BIGINT NOT NULL,
    isDone BOOLEAN NOT NULL,
    isValid BOOLEAN NOT NULL,
    FOREIGN KEY (recordContentId) references RecordContent(id),
    FOREIGN KEY (createUserId) references Users(id)
);

CREATE TABLE RecordMemo(
    recordId BIGINT NOT NULL PRIMARY KEY,
    memo VARCHAR(4096),
    FOREIGN KEY (recordId) references Record(id)
);

