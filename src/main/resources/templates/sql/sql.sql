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
    RECORDED_AT             DATE        DEFAULT SYSDATE,
    RECORDED_BY             VARCHAR2(50),                           -- 등록자 (관리자 ID 또는 이름)

    CONSTRAINT FK_MEMBERSHIP_SUSPEND FOREIGN KEY (MEMBERSHIP_ID)
        REFERENCES MEMBERSHIP(MEMBERSHIP_ID)
);

CREATE SEQUENCE SEQ_SUSPEND_HISTORY
    START WITH 1
    INCREMENT BY 1;

-- 멤버십 환불 이력 테이블
CREATE TABLE MEMBERSHIP_REFUND_HISTORY (
	REFUND_ID               NUMBER          PRIMARY KEY,
	MEMBERSHIP_ID           NUMBER          NOT NULL,

	REQUESTED_AT            DATE            DEFAULT SYSDATE NOT NULL,       -- 환불 요청일
	REQUESTED_BY            VARCHAR2(50),                                   -- 환불 요청 담당자

	PROCESSED_AT            DATE,                                           -- 처리일(승인 or 거절)
	APPROVED_BY             VARCHAR2(50),                                   -- 승인 처리 담당자
	REJECTED_BY             VARCHAR2(50),                                   -- 환불 등록일자

    REFUND_REASON           VARCHAR2(500),                                  -- 환불사유
    REJECT_REASON           VARCHAR2(500),                                  -- 반려이유
	REFUND_AMOUNT           NUMBER,
	REFUND_STATUS           NUMBER(1)       DEFAULT 0 NOT NULL,             -- 0 요청됨, 1 검토중, 2 완료, 3 반려

	CONSTRAINT FK_MEMBERSHIP_REFUND FOREIGN KEY (MEMBERSHIP_ID)
		REFERENCES MEMBERSHIP(MEMBERSHIP_ID)
);

CREATE SEQUENCE SEQ_REFUND_HISTORY
	START WITH 1
	INCREMENT BY 1;

-- 환불 이력 로그 테이블
CREATE TABLE MEMBERSHIP_REFUND_LOG (
    LOG_ID             NUMBER           PRIMARY KEY,
    REFUND_ID          NUMBER           NOT NULL,                       -- 환불 이력과 연결
    ACTION_TYPE        NUMBER(1)        NOT NULL,                       -- 0: 요청, 1: 승인, 2: 거절, 3: 시스템, 9: 기타
    ACTION_DETAIL      VARCHAR2(500),                                   -- 설명 또는 사유
    ACTION_BY          VARCHAR2(50)     NOT NULL,                       -- 담당자 (user or admin)
    ACTION_AT          DATE             DEFAULT SYSDATE NOT NULL,       -- 수행 시각

    CONSTRAINT FK_REFUND_LOG FOREIGN KEY (REFUND_ID)
        REFERENCES MEMBERSHIP_REFUND_HISTORY(REFUND_ID)
);

CREATE SEQUENCE SEQ_REFUND_LOG
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;


-- 관리자 테이블
CREATE TABLE ADMIN (
    ADMIN_ID        NUMBER          PRIMARY KEY,                -- 관리자ID
    USER_ID         VARCHAR2(50)    UNIQUE      NOT NULL,       -- 로그인용ID
    PASSWORD        VARCHAR2(100)   NOT NULL,                   -- 암호화해서 저장하기
    ADMIN_NAME      VARCHAR2(100)   NOT NULL,
    ROLE            VARCHAR2(20)    DEFAULT 'MANAGER',          -- 'MANAGER', 'OWNER'
    CREATED_AT      DATE            DEFAULT SYSDATE,
    UPDATED_AT      DATE            DEFAULT SYSDATE
);

CREATE SEQUENCE SEQ_ADMIN
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE
;