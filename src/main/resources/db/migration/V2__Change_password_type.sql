DROP TABLE IF EXISTS POST_COUNT;
DROP TABLE IF EXISTS TAGGED_ARTICLE;
DROP TABLE IF EXISTS ARTICLE;
DROP TABLE IF EXISTS CATEGORY;
DROP TABLE IF EXISTS COMMENT;
DROP TABLE IF EXISTS POST;
DROP TABLE IF EXISTS MEMBER;
DROP TABLE IF EXISTS TAG;

CREATE TABLE CATEGORY (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL,
    mapped_article_count BIGINT NOT NULL
) ENGINE=INNODB CHARSET=UTF8MB4;

CREATE TABLE MEMBER (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password LONGTEXT NOT NULL,
    role VARCHAR(255) NOT NULL
) ENGINE=INNODB CHARSET=UTF8MB4;

CREATE TABLE ARTICLE (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    writer_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content LONGTEXT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES CATEGORY(id) ON UPDATE CASCADE,
    FOREIGN KEY (writer_id) REFERENCES MEMBER(id) ON UPDATE CASCADE
) ENGINE=INNODB CHARSET=UTF8MB4;

CREATE TABLE POST (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    writer_id BIGINT NOT NULL,
    FOREIGN KEY (writer_id) REFERENCES MEMBER(id) ON UPDATE CASCADE
) ENGINE=INNODB CHARSET=UTF8MB4;

CREATE TABLE POST_COUNT (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    hits BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    version BIGINT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES POST(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB CHARSET=UTF8MB4;

CREATE TABLE COMMENT (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    content LONGTEXT NOT NULL,
    writer_id BIGINT NOT NULL,
    FOREIGN KEY (writer_id) REFERENCES MEMBER(id) ON UPDATE CASCADE,
    FOREIGN KEY (post_id) REFERENCES POST(id) ON UPDATE CASCADE
) ENGINE=INNODB CHARSET=UTF8MB4;

CREATE TABLE TAG (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
) ENGINE=INNODB CHARSET=UTF8MB4;

CREATE TABLE TAGGED_ARTICLE (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    article_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    FOREIGN KEY (article_id) REFERENCES ARTICLE(id) ON UPDATE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES TAG(id) ON UPDATE CASCADE
) ENGINE=INNODB CHARSET=UTF8MB4;