내 드라이브
exp sein/sein7955@shedb file='D:\01.Project\02.EHS\database 작업\dump\20170620she.dmp' full=y;

CREATE TABLESPACE Z01_TBS DATAFILE 
  'D:\dwcd\oradata\z01\Z01_TBS.DBF' 
  SIZE 1G
AUTOEXTEND ON NEXT 100M MAXSIZE UNLIMITED
LOGGING
ONLINE
PERMANENT
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO
FLASHBACK ON;

alter session set "_ORACLE_SCRIPT"=true;

출처: http://taisou.tistory.com/583 [Release Center]

CREATE USER Z01S IDENTIFIED BY "z01sadm"
 DEFAULT TABLESPACE "Z01_TBS"
 TEMPORARY TABLESPACE "TEMP"
 PROFILE DEFAULT
 QUOTA UNLIMITED ON "Z01_TBS";


grant connect, resource to Z01S;

alter user Z01S default tablespace Z01_TBS quota unlimited on users;

출처: http://taisou.tistory.com/583 [Release Center]

출처: http://taisou.tistory.com/583 [Release Center]


CREATE TABLESPACE Z01_TBS DATAFILE 
  'D:\dwcd\oradata\dwcd\SHE_DATA.DBF' 
  SIZE 2G
AUTOEXTEND ON NEXT 1000M MAXSIZE UNLIMITED
LOGGING
ONLINE
PERMANENT
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO
FLASHBACK ON;

CREATE USER ehs IDENTIFIED BY "ehs"
 DEFAULT TABLESPACE "Z01_TBS"
 TEMPORARY TABLESPACE "TEMP"
 PROFILE DEFAULT
 QUOTA UNLIMITED ON "Z01_TBS";

grant connect, resource to ehs;

alter user ehs default tablespace Z01_TBS quota unlimited on users;

 CREATE USER ERMUSER_SHE IDENTIFIED BY "ehs"
 DEFAULT TABLESPACE "Z01_TBS"
 TEMPORARY TABLESPACE "TEMP"
 PROFILE DEFAULT
 QUOTA UNLIMITED ON "Z01_TBS";

grant connect, resource to ERMUSER_SHE;

alter user ERMUSER_SHE default tablespace Z01_TBS quota unlimited on users;


CREATE USER RSKTOSHE IDENTIFIED BY "ehs"
 DEFAULT TABLESPACE "Z01_TBS"
 TEMPORARY TABLESPACE "TEMP"
 PROFILE DEFAULT
 QUOTA UNLIMITED ON "Z01_TBS";

grant connect, resource to RSKTOSHE;

alter user RSKTOSHE default tablespace Z01_TBS quota unlimited on users;

imp ehs/ehs@dwcd file='D:\01.Project\02.EHS\database 작업\dump\20170614she.dmp' full=y;


exp sein/sein7955@SHEDB file='C:\temp\soil20170403.dmp' full=y

exp z01s/z01sadm@dwcd file='C:\temp\dwcd.dmp' full=y

DROP TABLE Z01_SYS;
CREATE TABLE Z01_SYS (
    SYS_CD         VARCHAR2(20)     NOT NULL,
    SYS_NM         VARCHAR2 (40)    NOT NULL,
    DISP_ORD       VARCHAR2 (3)     NOT NULL,
    USE_OPEN_YN    VARCHAR2 (1)     NOT NULL,
    USE_PREVENT_YN VARCHAR2 (1)     NOT NULL,
    DB_USER_ID     VARCHAR2 (15),
    DB_USER_PASSWD VARCHAR2 (20),
    DB_SVR_NM      VARCHAR2 (15),
    IDLE_TIME      VARCHAR2 (3)     NOT NULL,
    DEV_TOOL       VARCHAR2 (1)     NOT NULL,
    A_REM          VARCHAR2 (150),
    B_REM          VARCHAR2 (40),
    C_REM          VARCHAR2 (40),
    D_REM          VARCHAR2 (40),
    E_REM          VARCHAR2 (40),
    REM            VARCHAR2 (60),
    SYS_INFO       VARCHAR2 (500),
    LOGIN_INFO_YN  VARCHAR2 (1) NOT NULL,
    AUTO_ROLE      VARCHAR2 (1),
	REG_ID         VARCHAR2(20),
	REG_TM         DATE,
	UPT_ID         VARCHAR2(20),
	UPT_TM         DATE,
    CONSTRAINT PK_Z01_SYS PRIMARY KEY (SYS_CD)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;


COMMENT ON COLUMN Z01_SYS.SYS_CD	          IS 'SYSTEM CODE';
COMMENT ON COLUMN Z01_SYS.SYS_NM                  IS 'SYSTEM NAME';
COMMENT ON COLUMN Z01_SYS.DISP_ORD                IS 'ORDER NO';
COMMENT ON COLUMN Z01_SYS.USE_OPEN_YN             IS 'OPEN Y/N';
COMMENT ON COLUMN Z01_SYS.USE_PREVENT_YN          IS 'PREVENT Y/N';
COMMENT ON COLUMN Z01_SYS.DB_USER_ID              IS 'DB USER ID';
COMMENT ON COLUMN Z01_SYS.DB_USER_PASSWD          IS 'DB PWD';
COMMENT ON COLUMN Z01_SYS.DB_SVR_NM               IS 'DB SERVER 이름';
COMMENT ON COLUMN Z01_SYS.IDLE_TIME               IS 'IDLE TIME';



DROP TABLE Z01_MENU;

CREATE TABLE Z01_MENU (
    SYS_CD         VARCHAR2 (20)     NOT NULL,
    MENU_CD        VARCHAR2 (20)     NOT NULL ,
    MENU_NM        VARCHAR2 (50) ,
    MENU_ENM       VARCHAR2 (50) ,
    MENU_CNM       VARCHAR2 (50) ,
    UP_MENU_CD     VARCHAR2 (20) ,
    ETC1           VARCHAR2 (30) ,
    ETC2           VARCHAR2 (30) ,
    ETC3           VARCHAR2 (30) ,
    USE_YN         VARCHAR2 (2) ,
    URL            VARCHAR2 (100) ,
	LEAF_YN        VARCHAR2 (2) ,
	MW_CLSS        VARCHAR2 (2) ,
	IMG_SRC        VARCHAR2 (200) ,
    POP_YN         VARCHAR2 (2) ,
    ORDER_NO       VARCHAR2 (4) ,
	REG_ID         VARCHAR2(20),
	REG_TM         DATE,
	UPT_ID         VARCHAR2(20),
	UPT_TM         DATE,
    CONSTRAINT PK_Z01_MENU_12 PRIMARY KEY (SYS_CD, MENU_CD)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;


COMMENT ON COLUMN Z01_MENU.SYS_CD                  IS 'SYSTEM CODE';
COMMENT ON COLUMN Z01_MENU.MENU_CD                IS 'MENU CODE';
COMMENT ON COLUMN Z01_MENU.MENU_NM                IS 'MENU NAME';
COMMENT ON COLUMN Z01_MENU.MENU_ENM               IS 'MENU ENG NAME';
COMMENT ON COLUMN Z01_MENU.MENU_CNM               IS 'MENU CHA NAME';
COMMENT ON COLUMN Z01_MENU.UP_MENU_CD           IS '상위메뉴';
COMMENT ON COLUMN Z01_MENU.ETC1                   IS '기타1';
COMMENT ON COLUMN Z01_MENU.ETC2                   IS '기타2';
COMMENT ON COLUMN Z01_MENU.ETC3                   IS '기타3';
COMMENT ON COLUMN Z01_MENU.USE_YN                 IS 'USE_YN';
COMMENT ON COLUMN Z01_MENU.LEAF_YN                IS '최종유무';
COMMENT ON COLUMN Z01_MENU.MW_CLSS                IS 'MENU OR WINDOW CLSS';
COMMENT ON COLUMN Z01_MENU.IMG_SRC                IS 'LINK IMAGE URL';
COMMENT ON COLUMN Z01_MENU.URL                    IS 'URL';
COMMENT ON COLUMN Z01_MENU.ORDER_NO               IS 'ORDER_NO';


DROP TABLE Z01_SYS_USER;

CREATE TABLE Z01_SYS_USER (
    SYS_CD         VARCHAR2 (20)     NOT NULL,
    ROLE_CD        VARCHAR2 (5)      NOT NULL,
    ITEM_CLSS      VARCHAR2 (1)      NOT NULL,
    USER_ITEM      VARCHAR2 (40)     NOT NULL,
    INCL_CLSS      VARCHAR2 (1),
    SYS_STAFF_YN   VARCHAR2 (1) ,
    REM            VARCHAR2 (60) ,
	REG_ID         VARCHAR2(20),
	REG_TM         DATE,
	UPT_ID         VARCHAR2(20),
	UPT_TM         DATE,
    CONSTRAINT PK_Z01_SYS_USER_1234 PRIMARY KEY (SYS_CD, ROLE_CD, ITEM_CLSS, USER_ITEM)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;


COMMENT ON COLUMN Z01_SYS_USER.SYS_CD                  IS 'SYSTEM CODE';
COMMENT ON COLUMN Z01_SYS_USER.ROLE_CD                IS 'ROLE CODE';
COMMENT ON COLUMN Z01_SYS_USER.ITEM_CLSS              IS 'ITEM CLSS';
COMMENT ON COLUMN Z01_SYS_USER.USER_ITEM              IS 'USER ITEM';
COMMENT ON COLUMN Z01_SYS_USER.INCL_CLSS              IS 'INCL CLSS';
COMMENT ON COLUMN Z01_SYS_USER.SYS_STAFF_YN           IS 'SYS STAFF YN';
COMMENT ON COLUMN Z01_SYS_USER.REM                    IS 'REMARK';
COMMENT ON COLUMN Z01_SYS_USER.UPDT_DATE              IS 'UPDT DATE';

DROP TABLE Z01_USER;

CREATE TABLE Z01_USER (
    USER_ID       VARCHAR2 (100) NOT NULL,
    USER_NM       VARCHAR2 (100) NOT NULL,
	PASSWORD      VARCHAR2 (100),
	ENABLED       VARCHAR2 (1),
	ACCOUNTNONEXPIRED        VARCHAR2 (1),
	CREDENTIALSNONEXPIRED    VARCHAR2 (1),
	ACCOUNTNONLOCKED         VARCHAR2 (1),
	PWSETYN                  VARCHAR2 (1),
	PWSETDTTM                DATE,
	PWFAILCNT                VARCHAR2 (1),
	LASTLOGINDTTM	         DATE,
	REG_ID         VARCHAR2(20),
	REG_TM         DATE,
	UPT_ID         VARCHAR2(20),
	UPT_TM         DATE,     
    MAIL          VARCHAR2 (100),
    EX_MAIL       VARCHAR2 (100),
    TEL           VARCHAR2 (50),
    MOBILE        VARCHAR2 (50),
    POST          VARCHAR2 (10),
    ADDRESS       VARCHAR2 (200),
    SSO_FG        VARCHAR2 (1),
    LANG_CD   VARCHAR2 (5),
    GMT_CD    VARCHAR2 (3),
    CONSTRAINT PK_Z01_USER_1 PRIMARY KEY (USER_ID)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;


COMMENT ON COLUMN Z01_USER.USER_ID	              IS 'USER ID EQ EMPNO';
COMMENT ON COLUMN Z01_USER.USER_NM                    IS 'USER NAME';


DROP TABLE Z01_DEPT;

CREATE TABLE Z01_DEPT (
    DEPT_CD        VARCHAR2 (20) NOT NULL,
    DEPT_NM        VARCHAR2 (100) NOT NULL,
    DEPT_E_NM      VARCHAR2 (100),
    DEPT_C_NM      VARCHAR2 (100),
    DEPT_LEVEL     VARCHAR2 (2),
    UP_DEPT_CD     VARCHAR2 (20),
    DEPT_SORT      VARCHAR2 (10),
    USE_FG         VARCHAR2 (1),
    MANAGER_ID     VARCHAR2 (20),
    DEPT_POST      VARCHAR2 (10),
    DEPT_ADDRESS   VARCHAR2 (200),
    DEPT_TEL       VARCHAR2 (50),
    DEPT_FAX       VARCHAR2 (50),
    REC_DOC_FG     VARCHAR2 (1),
    OU_FG          VARCHAR2 (2),
    AD_FG          VARCHAR2 (1),
    BD_FG          VARCHAR2 (2),
    INS_DATE       DATE ,
    UPD_DATE       DATE ,
    COM_CD         VARCHAR2 (20),
    COM_NM         VARCHAR2 (100),
    COM_E_NM       VARCHAR2 (100),
    MANAGER_DUTY   VARCHAR2 (50),
    ORG_LEVEL      VARCHAR2 (50),
    SECT_CD        VARCHAR2 (10),
    COM_TYPE       VARCHAR2 (50),
    CORP_CD        VARCHAR2 (50),
    CONSTRAINT PK_Z01_DEPT_1 PRIMARY KEY (DEPT_CD)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;


COMMENT ON COLUMN Z01_DEPT.DEPT_CD	          IS 'DEPT CODE';
COMMENT ON COLUMN Z01_DEPT.DEPT_NM                IS 'DEPT NAME';
COMMENT ON COLUMN Z01_DEPT.DEPT_LEVEL             IS 'DEPT LEVEL';
COMMENT ON COLUMN Z01_DEPT.UP_DEPT_CD             IS '상위 부서코드';
COMMENT ON COLUMN Z01_DEPT.DEPT_SORT              IS 'DEPT SORT';

DROP TABLE Z01_ROLE;

CREATE TABLE Z01_ROLE (
    SYS_CD         VARCHAR2(20) NOT NULL,
    ROLE_CD        VARCHAR2 (5) NOT NULL,
    ROLE_NM        VARCHAR2 (20),
    APPL_ORD       VARCHAR2 (3),
    REGI_ENABLE_YN VARCHAR2 (1),
    USE_OPEN_YN    VARCHAR2 (1) NOT NULL,
    REM            VARCHAR2 (60),
    CONSTRAINT PK_Z01_ROLE_12 PRIMARY KEY (SYS_CD, ROLE_CD)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;


COMMENT ON COLUMN Z01_ROLE.SYS_CD                   IS 'SYSTEM CODE';
COMMENT ON COLUMN Z01_ROLE.ROLE_CD                     IS 'ROLE CODE';
COMMENT ON COLUMN Z01_ROLE.ROLE_NM                     IS 'ROLE NAME';
COMMENT ON COLUMN Z01_ROLE.APPL_ORD                    IS 'APPLY ORDER 적용순서';
COMMENT ON COLUMN Z01_ROLE.REGI_ENABLE_YN              IS 'REGI_ENABLE_YN';



DROP TABLE Z01_ROLE_ITEM;

CREATE TABLE Z01_ROLE_ITEM (
    SYS_CD      VARCHAR2 (20) NOT NULL,
    ROLE_CD     VARCHAR2 (5)  NOT NULL,
    ITEM_CLSS   VARCHAR2 (10) NOT NULL,
    ROLE_ITEM   VARCHAR2 (20) NOT NULL,
    WIN_PRIV    VARCHAR2 (10) NOT NULL,
    CONSTRAINT PK_Z01_ROLE_ITEM_1_2_3_4 PRIMARY KEY (SYS_CD, ROLE_CD, ITEM_CLSS, ROLE_ITEM)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;


COMMENT ON COLUMN Z01_ROLE_ITEM.SYS_CD	               IS 'SYSTEM CODE';
COMMENT ON COLUMN Z01_ROLE_ITEM.ROLE_CD                IS 'ROLE CODE';
COMMENT ON COLUMN Z01_ROLE_ITEM.ITEM_CLSS              IS 'ITEM CLSS';
COMMENT ON COLUMN Z01_ROLE_ITEM.ROLE_ITEM              IS 'ROLE ITEM';
COMMENT ON COLUMN Z01_ROLE_ITEM.WIN_PRIV               IS 'WINDOW PRIV';


DROP TABLE Z01_SYSTEM_LOG;

CREATE TABLE Z01_SYSTEM_LOG (
    SYS_CD      VARCHAR2 (20) NOT NULL,
    CREATE_DT   DATE      NOT NULL ,
    MODE_CLSS   VARCHAR2 (2) NOT NULL,
    PROGRAM     VARCHAR2 (40) NOT NULL,
    MSG_CD      VARCHAR2 (2) NOT NULL,
    MSG_TXT     VARCHAR2(4000),
    A_REM       VARCHAR2 (150),
    B_REM       VARCHAR2 (40),
    C_REM       VARCHAR2 (40),
    CONSTRAINT PK_Z01_SYSTEM_LOG PRIMARY KEY (SYS_CD, CREATE_DT, MODE_CLSS,
    PROGRAM)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;


COMMENT ON COLUMN Z01_SYSTEM_LOG.SYS_CD	               IS 'SYSTEM CODE';
COMMENT ON COLUMN Z01_SYSTEM_LOG.CREATE_DT             IS 'CREATE DATE';
COMMENT ON COLUMN Z01_SYSTEM_LOG.MODE_CLSS             IS 'MODE CLSS';
COMMENT ON COLUMN Z01_SYSTEM_LOG.PROGRAM               IS 'PROGRAM';
COMMENT ON COLUMN Z01_SYSTEM_LOG.MSG_CD                IS 'MSG CD';
COMMENT ON COLUMN Z01_SYSTEM_LOG.MSG_TXT               IS 'MSG TEXT';
COMMENT ON COLUMN Z01_SYSTEM_LOG.A_REM                 IS 'A REMARK';
COMMENT ON COLUMN Z01_SYSTEM_LOG.B_REM                 IS 'B REMARK';
COMMENT ON COLUMN Z01_SYSTEM_LOG.C_REM                 IS 'C REMARK';

DROP TABLE Z01_BOARD;

CREATE TABLE Z01_BOARD (
    SYS_CD    VARCHAR2 (20) NOT NULL,
    BBSID     VARCHAR2 (10) NOT NULL,
    SEQ       NUMBER NOT NULL ,
    REF       NUMBER NOT NULL ,
    STEP      NUMBER NOT NULL ,
    LEV       NUMBER NOT NULL ,
    WRITER    VARCHAR2 (50) NOT NULL,
    SUBJECT   VARCHAR2 (500) NOT NULL,
    PASSWORD  VARCHAR2 (10),
    EMAIL     VARCHAR2 (50),
    READ_CNT  NUMBER NOT NULL ,
    FILE_ID   VARCHAR2 (20),
    DOWNLOAD  NUMBER,
    HTML      VARCHAR2 (1),
    HOMEPAGE  VARCHAR2 (200),
    STS       VARCHAR2 (1),
    WHEN_DT   DATE ,
    IP        VARCHAR2 (24),
    CAT_ID    VARCHAR2 (10) DEFAULT 'ETC' NOT NULL,
    WRITER_ID VARCHAR2 (7),
    CONSTRAINT PK_Z01_BOARD_123 PRIMARY KEY (SYS_CD, BBSID, SEQ)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;


COMMENT ON COLUMN Z01_BOARD.SYS_CD	          IS 'SYSTEM CODE';
COMMENT ON COLUMN Z01_BOARD.BBSID                 IS 'CREATE DATE';
COMMENT ON COLUMN Z01_BOARD.SEQ                   IS 'SEQ';
COMMENT ON COLUMN Z01_BOARD.REF                   IS 'PROGRAM';
COMMENT ON COLUMN Z01_BOARD.STEP                  IS 'MSG CD';
COMMENT ON COLUMN Z01_BOARD.LEV                   IS 'LEV';
COMMENT ON COLUMN Z01_BOARD.WRITER                IS 'WRITER';
COMMENT ON COLUMN Z01_BOARD.SUBJECT               IS 'SUBJECT';


DROP TABLE Z01_BOARD_CAT;

CREATE TABLE Z01_BOARD_CAT (
    SYS_CD      VARCHAR2 (20) NOT NULL,
    BBSID       VARCHAR2 (10) NOT NULL,
    CAT_ID      VARCHAR2 (10) NOT NULL,
    CAT_NM      VARCHAR2 (30) NOT NULL,
    ORD_USE_VAL NUMBER ,
    CONSTRAINT PK_Z01_BOARD_CAT_1_2 PRIMARY KEY (SYS_CD, BBSID, CAT_ID)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;


COMMENT ON COLUMN Z01_BOARD_CAT.SYS_CD	              IS 'SYSTEM CODE';
COMMENT ON COLUMN Z01_BOARD_CAT.BBSID                 IS 'CREATE DATE';
COMMENT ON COLUMN Z01_BOARD_CAT.CAT_ID                IS 'MODE CLSS';
COMMENT ON COLUMN Z01_BOARD_CAT.CAT_NM                IS 'PROGRAM';
COMMENT ON COLUMN Z01_BOARD_CAT.ORD_USE_VAL           IS 'ORDER USE VAL';


DROP TABLE Z01_BOARD_CMMTS;

CREATE TABLE Z01_BOARD_CMMTS (
    SYS_CD    VARCHAR2 (20) NOT NULL,
    BBSID     VARCHAR2 (10) NOT NULL,
    SEQ       NUMBER NOT NULL ,
    CMMTS     VARCHAR2(4000),
    WHEN_DT   DATE ,
    IP        VARCHAR2 (30),
    WRITER_ID VARCHAR2 (30),
    CMTSEQ    NUMBER ,
    WRITER    VARCHAR2 (30),
    CONSTRAINT PK_Z01_BOARD_CMMTS PRIMARY KEY (SYS_CD, BBSID, SEQ)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;


COMMENT ON COLUMN Z01_BOARD_CMMTS.SYS_CD            IS 'SYSTEM CODE';
COMMENT ON COLUMN Z01_BOARD_CMMTS.BBSID             IS 'BBSID';
COMMENT ON COLUMN Z01_BOARD_CMMTS.CMMTS             IS 'COMMENTS';
COMMENT ON COLUMN Z01_BOARD_CMMTS.WHEN_DT           IS 'WHEN DATE';
COMMENT ON COLUMN Z01_BOARD_CMMTS.IP                IS 'IP ADDR';

DROP TABLE Z01_BOARD_LIST;

CREATE TABLE Z01_BOARD_LIST (
    SYS_CD              VARCHAR2 (20) NOT NULL,
    BBSID               VARCHAR2 (10) NOT NULL,
    BBSNAME             VARCHAR2 (100),
    BBSCOMMENT          VARCHAR2 (2000),
    LOGIN_WRITEABLE_YN  VARCHAR2 (1),
    TITLE_URL           VARCHAR2 (100),
    MAX_SEQ             NUMBER NOT NULL ,
    MAX_REF             NUMBER NOT NULL ,
    BBS_CNT             NUMBER NOT NULL ,
    MGR_ER_NM           VARCHAR2 (30),
    EMAIL_SEND_YN       VARCHAR2 (1),
    REPLY_EMAIL_SEND_YN VARCHAR2 (1),
    LIST_CNT            NUMBER NOT NULL ,
    SUBLIST_CNT         NUMBER NOT NULL ,
    EMAIL_TOP_MSG       VARCHAR2 (500),
    EMAIL_BOTTOM_MSG    VARCHAR2 (500),
    REPLY_TOP_MSG       VARCHAR2 (500),
    REPLY_BOTTOM_MSG    VARCHAR2 (500),
    NAV_TXT             VARCHAR2 (200),
    CONSTRAINT PK_Z01_BOARD_LIST_12 PRIMARY KEY (SYS_CD, BBSID)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;


COMMENT ON COLUMN Z01_BOARD_LIST.SYS_CD	                IS 'SYSTEM CODE';
COMMENT ON COLUMN Z01_BOARD_LIST.BBSID                  IS 'BBSID';
COMMENT ON COLUMN Z01_BOARD_LIST.BBSNAME                IS 'BBSNAME';
COMMENT ON COLUMN Z01_BOARD_LIST.BBSCOMMENT             IS 'BBSCOMMENT';
COMMENT ON COLUMN Z01_BOARD_LIST.LOGIN_WRITEABLE_YN     IS '접속자 쓰기 여부';
COMMENT ON COLUMN Z01_BOARD_LIST.TITLE_URL              IS '타이틀 URL';
COMMENT ON COLUMN Z01_BOARD_LIST.MAX_SEQ                IS 'MAX SEQUENCE';
COMMENT ON COLUMN Z01_BOARD_LIST.MAX_REF                IS 'MAX REFERENCE';
COMMENT ON COLUMN Z01_BOARD_LIST.BBS_CNT                IS 'BBS_CNT';
COMMENT ON COLUMN Z01_BOARD_LIST.MGR_ER_NM              IS 'MGR_ER_NM';
COMMENT ON COLUMN Z01_BOARD_LIST.EMAIL_SEND_YN          IS '작성시 관리자 메일 발송여부';
COMMENT ON COLUMN Z01_BOARD_LIST.REPLY_EMAIL_SEND_YN    IS '작성시 관리자 메일 발송여부';
COMMENT ON COLUMN Z01_BOARD_LIST.LIST_CNT               IS 'LIST 보여주는 갯수';
COMMENT ON COLUMN Z01_BOARD_LIST.SUBLIST_CNT            IS 'IFRAME LIST 갯수';
COMMENT ON COLUMN Z01_BOARD_LIST.EMAIL_TOP_MSG          IS 'EMAIL TOP MESSAGE';
COMMENT ON COLUMN Z01_BOARD_LIST.EMAIL_BOTTOM_MSG       IS 'EMAIL BOTTOM MESSAGE';
COMMENT ON COLUMN Z01_BOARD_LIST.REPLY_TOP_MSG          IS 'REPLY TOT MESSAGE';
COMMENT ON COLUMN Z01_BOARD_LIST.REPLY_BOTTOM_MSG       IS 'REPLY BOTTOM MESSAGE';
COMMENT ON COLUMN Z01_BOARD_LIST.NAV_TXT                IS 'NAVIGATION TITLE';


DROP TABLE Z01_CODE_MASTER;

CREATE TABLE Z01_CODE_MASTER (
    SYS_CD     VARCHAR2 (20) NOT NULL,
    CLASS_CD   VARCHAR2 (20) NOT NULL,
    CLASS_NM   VARCHAR2 (100),
    CLASS_E_NM VARCHAR2 (100),
    CLASS_C_NM VARCHAR2 (100),
    ORDER_NO   VARCHAR2 (2),
    CLASS_DESC VARCHAR2 (100),
    USE_YN     VARCHAR2 (1) NOT NULL,
    REG_ID     VARCHAR2 (10),
    REG_TM     DATE ,
    UPDATE_ID  VARCHAR2 (10),
    UPDATE_TM  DATE ,
    CONSTRAINT PK_Z01_CODE_MASTER_1_2 PRIMARY KEY (SYS_CD, CLASS_CD)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;


COMMENT ON COLUMN Z01_CODE_MASTER.SYS_CD                   IS 'SYSTEM CODE';
COMMENT ON COLUMN Z01_CODE_MASTER.CLASS_CD                 IS 'CLASS_CD';
COMMENT ON COLUMN Z01_CODE_MASTER.CLASS_NM                 IS 'CLASS NAME';
COMMENT ON COLUMN Z01_CODE_MASTER.CLASS_E_NM               IS 'CLASS ENGLISH NAME';
COMMENT ON COLUMN Z01_CODE_MASTER.CLASS_C_NM               IS 'CLASS CHINA NAME';
COMMENT ON COLUMN Z01_CODE_MASTER.ORDER_NO                 IS 'ORDER NO';
COMMENT ON COLUMN Z01_CODE_MASTER.CLASS_DESC               IS 'CLASS DESCRIPTION';
COMMENT ON COLUMN Z01_CODE_MASTER.USE_YN                   IS 'USE YN';
COMMENT ON COLUMN Z01_CODE_MASTER.REG_ID                   IS 'REG ID';
COMMENT ON COLUMN Z01_CODE_MASTER.UPDATE_ID                IS 'UPDATE ID';


DROP TABLE Z01_CODE_DETAIL;

CREATE TABLE Z01_CODE_DETAIL (
    SYS_CD        VARCHAR2 (20) NOT NULL,
    CLASS_CD      VARCHAR2 (20) NOT NULL,
    CLASS_DTL_CD  VARCHAR2 (20) NOT NULL,
    CLASS_DTL_NM  VARCHAR2 (100),
    CLASS_DTL_ENM VARCHAR2 (100),
    ORDER_NO      VARCHAR2 (5),
    ETC_CD1       VARCHAR2 (100),
    ETC_CD2       VARCHAR2 (100),
    ETC_CD3       VARCHAR2 (100),
    ETC_CD4       VARCHAR2 (100),
    USE_YN        VARCHAR2 (1) NOT NULL,
    REG_ID        VARCHAR2 (10),
    REG_TM        DATE ,
    UPDATE_ID     VARCHAR2 (10),
    UPDATE_TM     DATE ,
    CLASS_DTL_CNM VARCHAR2 (100),
    CONSTRAINT PK_Z01_CODE_DETAIL_12 PRIMARY KEY (SYS_CD, CLASS_CD, CLASS_DTL_CD),
    CONSTRAINT FK_Z01_CODE_DETAIL_12 FOREIGN KEY (SYS_CD, CLASS_CD) REFERENCES Z01_CODE_MASTER(SYS_CD, CLASS_CD)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;


COMMENT ON COLUMN Z01_CODE_DETAIL.SYS_CD                   IS 'SYSTEM CODE';
COMMENT ON COLUMN Z01_CODE_DETAIL.CLASS_CD                 IS 'CLASS_CD';
COMMENT ON COLUMN Z01_CODE_DETAIL.CLASS_DTL_CD             IS 'CLASS DETAIL CD';
COMMENT ON COLUMN Z01_CODE_DETAIL.CLASS_DTL_NM             IS 'DETAIL CODE NAME';
COMMENT ON COLUMN Z01_CODE_DETAIL.CLASS_DTL_ENM            IS 'DETAIL CODE NAME';
COMMENT ON COLUMN Z01_CODE_DETAIL.ORDER_NO                 IS 'ORDER NO';
COMMENT ON COLUMN Z01_CODE_DETAIL.ETC_CD1                  IS 'ETC CD1';
COMMENT ON COLUMN Z01_CODE_DETAIL.ETC_CD2                  IS 'ETC CD2';
COMMENT ON COLUMN Z01_CODE_DETAIL.ETC_CD3                  IS 'ETC CD3';
COMMENT ON COLUMN Z01_CODE_DETAIL.ETC_CD4                  IS 'ETC CD4';
COMMENT ON COLUMN Z01_CODE_DETAIL.USE_YN                   IS 'USE YN';
COMMENT ON COLUMN Z01_CODE_DETAIL.REG_ID                   IS 'REG ID';
COMMENT ON COLUMN Z01_CODE_DETAIL.UPDATE_ID                IS 'UPDATE ID';

DROP TRIGGER Z01_SYS_ATR;

CREATE TABLE Z01_MSSG_I (
    SYS_CD        VARCHAR2 (20) NOT NULL,
    MSSG_ID       VARCHAR2 (20) NOT NULL,
    NTN_CD        VARCHAR2 (20) NOT NULL,
    MSSG_CN       VARCHAR2 (100),
    MSSG_CLSS_CD  VARCHAR2 (100),
    ORDER_NO      VARCHAR2 (5),
    ETC_CD1       VARCHAR2 (100),
    ETC_CD2       VARCHAR2 (100),
    ETC_CD3       VARCHAR2 (100),
    ETC_CD4       VARCHAR2 (100),
    USE_YN        VARCHAR2 (1) NOT NULL,
    REG_ID        VARCHAR2 (10),
    REG_TM        DATE,
    UPDATE_ID     VARCHAR2 (10),
    UPDATE_TM     DATE,
    CONSTRAINT PK_Z01_MSSG_I_123 PRIMARY KEY (SYS_CD, MSSG_ID, NTN_CD)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;

CREATE TABLE Z01_LOGIN_HIST (
	LOGIN_SEQ      VARCHAR2 (20) NOT NULL,
    SYS_CD         VARCHAR2 (20) NOT NULL,
    USER_ID        VARCHAR2 (100) NOT NULL,
    USER_IP        VARCHAR2 (40),
	LOGIN_DT       DATE,
    CONSTRAINT PK_Z01_LOGIN_HIST_1 PRIMARY KEY (LOGIN_SEQ)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;

DROP TABLE Z01_ACCESS_LOG;
CREATE TABLE Z01_ACCESS_LOG (
	ACCESS_SEQ     VARCHAR2 (20) NOT NULL,
	REQ_URL        VARCHAR2 (200) NOT NULL,
    SYS_CD         VARCHAR2 (20) NOT NULL,
    USER_ID        VARCHAR2 (100) NOT NULL,
    USER_IP        VARCHAR2 (40),
	PRGMCD         VARCHAR2 (40),
	REG_DT         DATE,
    CONSTRAINT PK_Z01_ACCESS_LOG_1 PRIMARY KEY (ACCESS_ID)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;





CREATE OR REPLACE TRIGGER Z01_SYS_TR
     AFTER INSERT OR DELETE ON Z01_SYS 
     FOR EACH ROW

     BEGIN
     
        IF INSERTING THEN
            INSERT INTO Z01_ROLE
              (
                  SYS_CD, ROLE_CD, ROLE_NM, APPL_ORD, REGI_ENABLE_YN, REG_ID, REG_TM
              )
             VALUES
             (
                 :NEW.SYS_CD, 'DEV', 'IT MANAGER', '0', 'Y', 'SYSTEM', SYSDATE 
             );
             
            INSERT INTO Z01_ROLE
              (
                  SYS_CD, ROLE_CD, ROLE_NM, APPL_ORD, REGI_ENABLE_YN, REG_ID, REG_TM
              )
             VALUES
             (
                 :NEW.SYS_CD, 'MGR', 'IT MANAGER', '0', 'Y', 'SYSTEM', SYSDATE 
             );
        ELSIF DELETING THEN
            DELETE FROM Z01_ROLE_ITEM
             WHERE SYS_CD = :OLD.SYS_CD;
        END IF;
     END;
     /

CREATE OR REPLACE TRIGGER Z01_BOARD_BTR
        BEFORE INSERT ON Z01_BOARD
        FOR EACH ROW
    BEGIN
        IF :NEW.LEV > 0 THEN
            UPDATE Z01_BOARD_LIST
               SET MAX_SEQ = :NEW.SEQ,
                   BBS_CNT = BBS_CNT + 1
             WHERE BBSID = :NEW.BBSID;
        ELSE
            UPDATE Z01_board_list
               SET MAX_SEQ = :NEW.SEQ,
                   MAX_REF = :NEW.REF,
                   BBS_CNT = BBS_CNT + 1
             WHERE BBSID = :NEW.BBSID;
        END IF;
    END;
    /

    
-- Z01S.Z01_IOT_INFO definition

CREATE TABLE Z01_IOT_INFO 
   (	
    SITE_CD VARCHAR2(10) NOT NULL ENABLE, 
	FACTORY_CD VARCHAR2(10) NOT NULL ENABLE, 
	IOT_ID VARCHAR2(20) NOT NULL ENABLE, 
	IOT_NM VARCHAR2(200), 
	FILE_PATHA VARCHAR2(200), 
	FILE_PATHB VARCHAR2(200), 
	FILE_PATHC VARCHAR2(200), 
	FILE_PATHD VARCHAR2(200), 
	LOC_INFO VARCHAR2(400), 
	STATUS VARCHAR2(100), 
	CONTENT VARCHAR2(1000), 
	USE_YN VARCHAR2(2) DEFAULT 'Y', 
	REG_ID VARCHAR2(20), 
	REG_TM DATE, 
	UPT_ID VARCHAR2(20), 
	UPT_TM DATE, 
	CONSTRAINT PK_Z01_IOT_INFO PRIMARY KEY (SITE_CD, FACTORY_CD, IOT_ID)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;

COMMENT ON COLUMN Z01_IOT_INFO.SITE_CD IS 'SITE CODE';
COMMENT ON COLUMN Z01_IOT_INFO.FACTORY_CD IS '공장CD';
COMMENT ON COLUMN Z01_IOT_INFO.IOT_ID IS 'IOT ID';
COMMENT ON COLUMN Z01_IOT_INFO.IOT_NM IS 'IOT 이름';
COMMENT ON COLUMN Z01_IOT_INFO.FILE_PATHA IS '파일경로A';
COMMENT ON COLUMN Z01_IOT_INFO.FILE_PATHB IS '파일경로B';
COMMENT ON COLUMN Z01_IOT_INFO.FILE_PATHC IS '파일경로C';
COMMENT ON COLUMN Z01_IOT_INFO.FILE_PATHD IS '파일경로D';
COMMENT ON COLUMN Z01_IOT_INFO.LOC_INFO IS '위치경로';
COMMENT ON COLUMN Z01_IOT_INFO.STATUS IS '상태';
COMMENT ON COLUMN Z01_IOT_INFO.CONTENT IS '내용';
COMMENT ON COLUMN Z01_IOT_INFO.USE_YN IS '사용유무';    
    


CREATE TABLE Z01_IOT_MASTER 
   (	
    SITE_CD VARCHAR2(10) NOT NULL ENABLE, 
	FACTORY_CD VARCHAR2(10) NOT NULL ENABLE, 
	IOT_ID VARCHAR2(20) NOT NULL ENABLE, 
	IOT_NM VARCHAR2(200), 
	FILE_PATHA VARCHAR2(200), 
	FILE_PATHB VARCHAR2(200), 
	FILE_PATHC VARCHAR2(200), 
	FILE_PATHD VARCHAR2(200), 
	LOC_INFO VARCHAR2(400), 
	STATUS VARCHAR2(100), 
	CONTENT VARCHAR2(1000), 
	USE_YN VARCHAR2(2) DEFAULT 'Y', 
	REG_ID VARCHAR2(20), 
	REG_TM DATE, 
	UPT_ID VARCHAR2(20), 
	UPT_TM DATE, 
	CONSTRAINT PK_Z01_IOT_MASTER PRIMARY KEY (SITE_CD, FACTORY_CD, IOT_ID)
)
TABLESPACE Z01_TBS
PCTFREE 10
PCTUSED 0
INITRANS 1
MAXTRANS 255
STORAGE (
    INITIAL 64 K
    NEXT 1024 K
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
)
LOGGING
NOCACHE
MONITORING
NOPARALLEL
;