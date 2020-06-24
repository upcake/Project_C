--회원 관리 테이블------------------------------------------------------------------------
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


--복용 기록 테이블----------------------------------------------------------------------------------
CREATE TABLE medicine_record(
    record_email VARCHAR2(50),
    record_alarm_name VARCHAR2(50) NOT NULL,
    record_take_time VARCHAR2(30) NOT NULL,
    CONSTRAINT record_email_fk FOREIGN KEY(record_email) REFERENCES manage_member(member_email)
);

INSERT INTO medicine_record
VALUES ('aaaaa@naver.com', '타이레놀 2정', TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO medicine_record
VALUES ('aaaaa@naver.com', '타이레놀 2정', TO_CHAR(SYSDATE + 1, 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO medicine_record
VALUES ('aaaaa@naver.com', '타이레놀 2정', TO_CHAR(SYSDATE + 2, 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO medicine_record
VALUES ('bbbbb@google.com', '아스피린', TO_CHAR(SYSDATE - 15, 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO medicine_record
VALUES ('bbbbb@google.com', '아스피린', TO_CHAR(SYSDATE - 14, 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO medicine_record
VALUES ('bbbbb@google.com', '아스피린', TO_CHAR(SYSDATE - 10, 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO medicine_record
VALUES ('ccccc@naver.com', '게보린 1', TO_CHAR(SYSDATE - 10, 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO medicine_record
VALUES ('ccccc@naver.com', '게보린 1', TO_CHAR(SYSDATE - 9, 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO medicine_record
VALUES ('ccccc@naver.com', '게보린 1', TO_CHAR(SYSDATE - 8, 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO medicine_record
VALUES ('ccccc@naver.com', '게보린 1', TO_CHAR(SYSDATE - 7, 'YYYY-MM-DD HH24:MI:SS'));

DELETE FROM medicine_record;

DROP TABLE medicine_record;

SELECT * FROM medicine_record;


--비상 연락망 테이블---------------------------------------------------------------------------
CREATE TABLE emergency_contact(
    emergency_email VARCHAR2(50),
    emergency_name VARCHAR2(30) NOT NULL,
    emergency_phonenum VARCHAR2(20) NOT NULL,
    CONSTRAINT emergency_email_fk FOREIGN KEY(emergency_email) REFERENCES manage_member(member_email)
);

INSERT INTO emergency_contact
VALUES ('aaaaa@naver.com', '어머니', '0101111234');

INSERT INTO emergency_contact
VALUES ('aaaaa@naver.com', '아버지', '01012341111');

INSERT INTO emergency_contact
VALUES ('bbbbb@google.com', '남편', '01022222345');

INSERT INTO emergency_contact
VALUES ('bbbbb@google.com', '친구1', '01012342222');

INSERT INTO emergency_contact
VALUES ('bbbbb@google.com', '친구2', '01023452345');

INSERT INTO emergency_contact
VALUES ('ccccc@naver.com', '아들', '01033333456');

DELETE FROM emergency_contact;

DROP TABLE emergency_contact;

SELECT * FROM emergency_contact;