--회원 관리 테이블
CREATE TABLE manage_member(
    member_email VARCHAR2(50),
    member_password VARCHAR2(50) NOT NULL,
    member_nickname VARCHAR2(30) NOT NULL,
    member_phonenum VARCHAR2(20) NOT NULL,
    CONSTRAINT member_email_pk PRIMARY KEY(member_email),
    CONSTRAINT member_nickname_uq UNIQUE(member_nickname)
);

DROP TABLE manage_member;

INSERT INTO manage_member
VALUES ('aaaaa@naver.com', '@@aaa123', '아아AAA', '010-1111-1111');

INSERT INTO manage_member
VALUES ('bbbbb@google.com', '@@bbb123', '나나BBB', '010-2222-2222');

INSERT INTO manage_member
VALUES ('ccccc@naver.com', '@@ccc123', '다다CCC', '010-3333-3333');

SELECT * FROM manage_member;

--복용 기록 테이블
CREATE TABLE medicine_record(
    record_email VARCHAR2(50),
    record_alarm_name VARCHAR2(50) NOT NULL,
    record_take_time VARCHAR2(30) NOT NULL,
    CONSTRAINT record_email_pk PRIMARY KEY(record_email),
    CONSTRAINT record_email_fk FOREIGN KEY(record_email) REFERENCES manage_member(member_email)
);

INSERT INTO medicine_record
VALUES ('aaaaa@naver.com', '타이레놀 2정', SYSDATE);

SELECT * FROM medicine_record;