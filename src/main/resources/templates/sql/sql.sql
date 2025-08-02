-- 회원 테이블
CREATE TABLE MEMBER (
    MEMBER_ID         NUMBER          PRIMARY KEY,                  -- 내부 식별자
    MEMBER_LOGIN_ID   VARCHAR2(50)    UNIQUE NOT NULL,              -- 로그인용 ID
    MEMBER_PASSWORD   VARCHAR2(100)   NOT NULL,                     -- 로그인 비밀번호 (암호화 저장)

    NAME              VARCHAR2(100)   NOT NULL,
    PHONE             VARCHAR2(20)    NOT NULL,
    EMAIL             VARCHAR2(50)    UNIQUE NOT NULL,
    GENDER            CHAR(1)         NOT NULL,                     -- 'M' 또는 'F'
    BIRTHDATE         DATE,

    STATUS            NUMBER(1)       DEFAULT 1 NOT NULL,            -- 1:정상, 2:이용중지, 3:탈퇴
    CREATED_AT          DATE DEFAULT SYSDATE NOT NULL,
    UPDATED_AT          DATE
);

CREATE SEQUENCE SEQ_MEMBER_ID
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- 관리자 테이블
CREATE TABLE ADMIN (
    ADMIN_ID                NUMBER          PRIMARY KEY,                -- 관리자ID
    ADMIN_LOGIN_ID          VARCHAR2(50)    UNIQUE      NOT NULL,       -- 로그인용ID
    ADMIN_PASSWORD          VARCHAR2(100)   NOT NULL,                   -- 암호화해서 저장하기
    ADMIN_NAME              VARCHAR2(100)   NOT NULL,
    ROLE                    VARCHAR2(20)    DEFAULT 'MANAGER',          -- 'MANAGER', 'OWNER'
    CREATED_AT              DATE            DEFAULT SYSDATE,
    UPDATED_AT              DATE
);

CREATE SEQUENCE SEQ_ADMIN
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE
;


CREATE TABLE MEMBERSHIP (
    MEMBERSHIP_ID               NUMBER          PRIMARY KEY,
    MEMBER_ID                   NUMBER          NOT NULL,

    MEMBERSHIP_TYPE             VARCHAR2(50)    NOT NULL,
    PERIOD_DAYS                 NUMBER          NOT NULL,
    START_DATE                  DATE            NOT NULL,
    END_DATE                    DATE            NOT NULL,
    PRICE                       NUMBER          NOT NULL,

    MEMBERSHIP_STATUS           VARCHAR2(20)    DEFAULT 'ACTIVE'    NOT NULL,      -- 이용중(ACTIVE),정지(SUSPENDED),환불(REFUND),종료

    CREATED_AT                  DATE            DEFAULT SYSDATE     NOT NULL,
    CREATED_BY			        NUMBER 		    NOT NULL,
    UPDATED_AT                  DATE,
    UPDATED_BY			        NUMBER,
    EXTENDED_END_DATE           DATE,                                               -- 정지일이 반영된 새로운 종료일

    CONSTRAINT FK_MEMBERSHIP_MEMBER FOREIGN KEY (MEMBER_ID)
        REFERENCES MEMBER(MEMBER_ID),
    CONSTRAINT FK_MEMBERSHIP_CREATED_ADMIN FOREIGN KEY (CREATED_BY)
        REFERENCES ADMIN(ADMIN_ID),
    CONSTRAINT FK_MEMBERSHIP_UPDATED_ADMIN FOREIGN KEY (UPDATED_BY)
        REFERENCES ADMIN(ADMIN_ID)
);

CREATE SEQUENCE SEQ_MEMBERSHIP_ID
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;


-- 멤버십 정지 테이블
CREATE TABLE MEMBERSHIP_SUSPEND_HISTORY (
    SUSPEND_ID              NUMBER          PRIMARY KEY,
    MEMBERSHIP_ID           NUMBER          NOT NULL,
    SUSPEND_START_DATE      DATE            NOT NULL,
    SUSPEND_END_DATE        DATE            NOT NULL,
    SUSPEND_REASON          VARCHAR2(200)   NOT NULL,               -- 정지 사유

    CREATED_AT              DATE DEFAULT SYSDATE NOT NULL,          -- 정지 등록일
    CREATED_BY              NUMBER          NOT NULL,               -- 정지 등록 관리자ID
    UPDATED_AT              DATE,                                   -- 최종 수정일
    UPDATED_BY              NUMBER,                                 -- 최종 정지 수정 관리자ID

    CONSTRAINT FK_MEMBERSHIP_SUSPEND FOREIGN KEY (MEMBERSHIP_ID)
        REFERENCES MEMBERSHIP(MEMBERSHIP_ID),
    CONSTRAINT FK_SUSPEND_CREATED_ADMIN FOREIGN KEY (CREATED_BY)
        REFERENCES ADMIN(ADMIN_ID),
    CONSTRAINT FK_SUSPEND_UPDATED_ADMIN FOREIGN KEY (UPDATED_BY)
        REFERENCES ADMIN(ADMIN_ID)
);

CREATE SEQUENCE SEQ_SUSPEND_ID
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- 멤버십 환불 이력 테이블
CREATE TABLE MEMBERSHIP_REFUND_HISTORY (
	REFUND_ID               NUMBER          PRIMARY KEY,
	MEMBERSHIP_ID           NUMBER          NOT NULL,

	REQUESTED_AT            DATE            DEFAULT SYSDATE NOT NULL,       -- 환불 요청일
	REQUESTED_BY            VARCHAR2(50),                                   -- 환불 요청 담당자

	PROCESSED_AT            DATE,                                           -- 처리일(승인 or 거절)
	APPROVED_BY             VARCHAR2(50),                                   -- 승인 처리 담당자
	REJECTED_BY             VARCHAR2(50),                                   -- 환불 담당자

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