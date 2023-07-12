DROP TABLE IF EXISTS blog_search_history CASCADE;
DROP TABLE IF EXISTS blog_search_cnt CASCADE;

CREATE TABLE blog_search_history (
	seq INT(11) PRIMARY KEY AUTO_INCREMENT COMMENT 'PK',
	source ENUM('K','N') NOT NULL DEFAULT 'K' COMMENT '출처',
	query VARCHAR(100) NOT NULL COMMENT '검색을 원하는 질의어',
	reg_dttm DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '검색시간',
	index idx00 (query),
	index idx01 (reg_dttm)
) COMMENT '블로그 검색 기록';

CREATE TABLE blog_search_cnt (
	query VARCHAR(100) PRIMARY KEY COMMENT '검색을 원하는 질의어',
	cnt INT(11) NOT NULL DEFAULT 0 COMMENT '검색횟수'
) COMMENT '블로그 검색 통계용';