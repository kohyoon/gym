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
    MEMBERSHIP_STATUS           NUMBER(1)       DEFAULT 1 NOT NULL, -- 1이용중 2정지 3환불 4종료
    CREATED_AT                  DATE            DEFAULT SYSDATE,
    UPDATED_AT                  DATE            DEFAULT SYSDATE,
    SUSPEND_START_DATE          DATE,                               -- 정지 시작일
    SUSPEND_END_DATE            DATE,                               -- 정지 종료일
    REMAINING_DAYS              NUMBER(3),                          -- 정지 시점 기준 남은 이용일 수
    EXTENDED_END_DATE           DATE,                               -- 정지 반영 후 새로운 종료일
    CONSTRAINT FK_MEMBER_ID FOREIGN KEY (MEMBER_ID)
        REFERENCES MEMBER(MEMBER_ID)
);

CREATE SEQUENCE SEQ_MEMBERSHIP_ID
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;


-- 멤버십 정지 이력 테이블
CREATE TABLE MEMBERSHIP_SUSPEND_HISTORY (
    HISTORY_ID              NUMBER      PRIMARY KEY,
    MEMBERSHIP_ID           NUMBER      NOT NULL,
    SUSPEND_START_DATE      DATE        NOT NULL,
    SUSPEND_END_DATE        DATE        NOT NULL,
    RECORDED_AT             DATE        DEFAULT SYSDATE,            -- 정지 등록일자
    RECORDED_BY             VARCHAR2(50),                           -- 등록자 (관리자 ID 또는 이름)

    CONSTRAINT FK_MEMBERSHIP_SUSPEND FOREIGN KEY (MEMBERSHIP_ID)
        REFERENCES MEMBERSHIP(MEMBERSHIP_ID)
);

CREATE SEQUENCE SEQ_SUSPEND_HISTORY
    START WITH 1
    INCREMENT BY 1;