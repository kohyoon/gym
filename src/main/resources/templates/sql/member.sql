-- 회원 테이블
CREATE TABLE MEMBER (
    MEMBER_ID       NUMBER          PRIMARY KEY,
    NAME            VARCHAR2(100)   NOT NULL,
    PHONE           VARCHAR2(20)    NOT NULL,
    EMAIL           VARCHAR2(50)    NOT NULL UNIQUE,
    GENDER          CHAR(1)         NOT NULL, -- 'M' 또는 'F'
    BIRTHDATE       DATE,
    MEMBER_REGDATE  DATE DEFAULT SYSDATE NOT NULL,
    STATUS          NUMBER(1)       DEFAULT 1
);

CREATE SEQUENCE SEQ_MEMBER_ID
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- 멤버십 테이블
CREATE TABLE MEMBERSHIP (
    MEMBERSHIP_ID               NUMBER          PRIMARY KEY,
    MEMBER_ID                   NUMBER          NOT NULL,
    MEMBERSHIP_TYPE             VARCHAR2(50)    NOT NULL,
    PERIOD_DAYS                 NUMBER          NOT NULL,
    START_DATE                  DATE            NOT NULL,
    END_DATE                    DATE            NOT NULL,
    PRICE                       NUMBER          NOT NULL,
    MEMBERSHIP_STATUS           NUMBER(1)       DEFAULT 1 NOT NULL, -- 1이용중 2종료 3환불
    CREATED_AT                  DATE            DEFAULT SYSDATE,
    UPDATED_AT                  DATE            DEFAULT SYSDATE,
    CONSTRAINT FK_MEMBER_ID FOREIGN KEY (MEMBER_ID)
        REFERENCES MEMBER(MEMBER_ID)
);

CREATE SEQUENCE SEQ_MEMBERSHIP_ID
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;
