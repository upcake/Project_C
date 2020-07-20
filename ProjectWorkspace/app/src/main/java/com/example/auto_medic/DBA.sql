--회원 관리 테이블------------------------------------------------------------------------
CREATE TABLE manage_member(
    member_email VARCHAR2(50),
    member_password VARCHAR2(50) NOT NULL,
    member_nickname VARCHAR2(30) NOT NULL,
    member_phonenum VARCHAR2(20) NOT NULL,
    CONSTRAINT member_email_pk PRIMARY KEY(member_email),
    CONSTRAINT member_nickname_uq UNIQUE(member_nickname)
);

ALTER TABLE manage_member ADD(member_profile VARCHAR2(1024) DEFAULT 'http://192.168.0.20:8080/app/resources/default.jpg');

ALTER TABLE manage_member MODIFY(member_profile DEFAULT 'http://192.168.0.20:8080/app/resources/default_profile.jpg');

DROP TABLE manage_member;

DELETE FROM manage_member;

INSERT INTO manage_member
VALUES ('a', 'a', 'a', '010');

INSERT INTO manage_member
VALUES ('aaaaa@naver.com', '@@aaa123', '아아AAA', '010-1111-1111');

INSERT INTO manage_member
VALUES ('bbbbb@google.com', '@@bbb123', '나나BBB', '010-2222-2222');

INSERT INTO manage_member
VALUES ('ccccc@naver.com', '@@ccc123', '다다CCC', '010-3333-3333');

SELECT * FROM manage_member;

SELECT member_password, member_nickname, member_phonenum, member_profile FROM manage_member WHERE member_email = 'aaaaa@naver.com';


--복용 기록 테이블----------------------------------------------------------------------------------
CREATE TABLE medicine_record(
    record_email VARCHAR2(50),
    record_alarm_name VARCHAR2(50) NOT NULL,
    record_take_time VARCHAR2(30) NOT NULL,
    CONSTRAINT record_email_fk FOREIGN KEY(record_email) REFERENCES manage_member(member_email)
);

ALTER TABLE medicine_record MODIFY (record_email NOT NULL);

ALTER TABLE medicine_record ADD (record_number NUMBER);

ALTER TABLE medicine_record MODIFY (record_number NOT NULL);

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

INSERT INTO medicine_record
VALUES ('a', 'ㅁㄴㅇㄹ', TO_CHAR('20-07-07 09:05'));

INSERT INTO medicine_record
VALUES ('a', 'ㅁㄴㅇㄹ', TO_CHAR('20-07-07 12:05'));

INSERT INTO medicine_record
VALUES ('a', 'ㅁㄴㅇㄹ', TO_CHAR('20-07-07 18:05'));

DELETE FROM medicine_record;

DROP TABLE medicine_record;

SELECT * FROM medicine_record;


--비상 연락망 테이블---------------------------------------------------------------------------
CREATE TABLE emergency_contact(
    emergency_email VARCHAR2(50) NOT NULL,
    emergency_name VARCHAR2(30) NOT NULL,
    emergency_phonenum VARCHAR2(20) NOT NULL,
    CONSTRAINT emergency_email_fk FOREIGN KEY(emergency_email) REFERENCES manage_member(member_email)
);

ALTER TABLE emergency_contact MODIFY (emergency_email NOT NULL);

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

--알람 테이블------------------------------------------------------------------------------------
CREATE TABLE alarm(
    alarm_email VARCHAR2(50) NOT NULL,
    alarm_id VARCHAR2(20),
    alarm_title VARCHAR2(100) NOT NULL,
    alarm_sunday CHAR(1) NOT NULL,
    alarm_monday CHAR(1) NOT NULL,
    alarm_tuesday CHAR(1) NOT NULL,
    alarm_wednesday CHAR(1) NOT NULL,
    alarm_thursday CHAR(1) NOT NULL,
    alarm_friday CHAR(1) NOT NULL,
    alarm_saturday CHAR(1) NOT NULL,
    alarm_times CHAR(1) NOT NULL,
    alarm_ringtime1_hour VARCHAR2(2),
    alarm_ringtime1_minute VARCHAR2(2),
    alarm_ringtime2_hour VARCHAR2(2),
    alarm_ringtime2_minute VARCHAR2(2),
    alarm_ringtime3_hour VARCHAR2(2),
    alarm_ringtime3_minute VARCHAR2(2),
    alarm_volume VARCHAR2(3) NOT NULL,
    alarm_bell VARCHAR2(100),
    alarm_vib CHAR(1),
    alarm_repeat VARCHAR2(2),
    CONSTRAINT alarm_id_pk PRIMARY KEY(alarm_id)
);

SELECT * FROM alarm;

DROP TABLE alarm;

COMMIT;

SELECT * FROM alarm ORDER BY alarm_times DESC, alarm_Ringtime1_Hour, alarm_Ringtime1_Minute, alarm_Ringtime2_Hour, alarm_Ringtime2_Minute, alarm_Ringtime3_Hour, alarm_Ringtime3_Minute;