CREATE TABLE ATTRIBUTE
  (
     ID          BIGINT NOT NULL AUTO_INCREMENT,
     TYPE        VARCHAR(255) NOT NULL,
     DESCRIPTION VARCHAR(255),
     MAXIMUM     INTEGER,
     MINIMUM     INTEGER,
     NAME        VARCHAR(255) NOT NULL,
     PATTERN     VARCHAR(255),
     PRIMARY KEY (ID)
  );
CREATE UNIQUE INDEX ATTRIBUTE_INDEX ON ATTRIBUTE(ID);

CREATE TABLE DOMAIN
  (
     ID          BIGINT NOT NULL AUTO_INCREMENT,
     DESCRIPTION VARCHAR(255),
     NAME        VARCHAR(255) NOT NULL,
     SELECTED    BIT NOT NULL,
     PROJECT_ID  BIGINT NOT NULL,
     PRIMARY KEY (ID)
  );
CREATE UNIQUE INDEX DOMAIN_INDEX ON DOMAIN(ID);

CREATE TABLE DOMAIN_ATTRIBUTES
  (
     DOMAIN_ENTITY_ID BIGINT NOT NULL,
     ATTRIBUTES_ID    BIGINT NOT NULL,
     PRIMARY KEY (DOMAIN_ENTITY_ID, ATTRIBUTES_ID)
  );
CREATE UNIQUE INDEX D_A_ATTRIBUTE_INDEX ON DOMAIN_ATTRIBUTES(ATTRIBUTES_ID);
CREATE INDEX D_A_DOMAIN_INDEX ON DOMAIN_ATTRIBUTES(DOMAIN_ENTITY_ID);


CREATE TABLE PROJECT
  (
     ID            BIGINT NOT NULL AUTO_INCREMENT,
     DESCRIPTION   VARCHAR(255),
     PACKAGEPREFIX VARCHAR(100) NOT NULL,
     TARGETPATH    VARCHAR(100),
     TITLE         VARCHAR(100) NOT NULL,
     TEMPLATE_ID   BIGINT NOT NULL,
     PRIMARY KEY (ID)
  );
CREATE UNIQUE INDEX PROJECT_INDEX ON PROJECT(ID);

CREATE TABLE PROJECTTEMPLATE
  (
     ID                   BIGINT NOT NULL AUTO_INCREMENT,
     BRANCH               VARCHAR(100),
     CREDENTIALS_REQUIRED BIT,
     DESCRIPTION          VARCHAR(255),
     LOCATION             VARCHAR(100) NOT NULL,
     PREFIX               VARCHAR(100) NOT NULL,
     TITLE                VARCHAR(100) NOT NULL,
     PRIMARY KEY (ID)
  );
CREATE UNIQUE INDEX TEMPLATE_INDEX ON PROJECTTEMPLATE(ID);
CREATE INDEX PROJECT_TEMPLATE_INDEX ON PROJECT(TEMPLATE_ID);
CREATE INDEX DOMAIN_PROJECT_INDEX ON DOMAIN(PROJECT_ID);


CREATE TABLE CATEGORY
  (
     ID   BIGINT NOT NULL AUTO_INCREMENT,
     NAME VARCHAR(255) NOT NULL,
     PRIMARY KEY (ID)
  );
CREATE UNIQUE INDEX CATEGORY_INDEX ON CATEGORY(ID);

CREATE TABLE CODETEMPLATE
  (
     ID                 BIGINT NOT NULL AUTO_INCREMENT,
     CREATE_DOMAIN_DIR  BIT NOT NULL,
     FILE_NAME_SUFFIX   VARCHAR(100) NOT NULL,
     LOWER_CASE         BIT NOT NULL,
     TARGET_PATH        VARCHAR(255) NOT NULL,
     TEMPLATE_PATH      VARCHAR(255) NOT NULL,
     TEMPLATE_TYPE      VARCHAR(255) NOT NULL,
     FIRST_UPPER        BIT NOT NULL,
     CATEGORY_ID        BIGINT NOT NULL,
     PROJECTTEMPLATE_ID BIGINT,
     PRIMARY KEY (ID)
  );
CREATE UNIQUE INDEX CODETEMPLATE_INDEX ON CODETEMPLATE(ID);

CREATE TABLE CODETEMPLATE_PROJECT
  (
     CODETEMPLATE_ID BIGINT NOT NULL,
     PROJECT_ID      BIGINT NOT NULL,
     PRIMARY KEY (CODETEMPLATE_ID, PROJECT_ID)
  );

ALTER TABLE CODETEMPLATE
  ADD CONSTRAINT FK_CODETEMPLATE_CATEGORY FOREIGN KEY (CATEGORY_ID)
  REFERENCES CATEGORY (ID);

ALTER TABLE CODETEMPLATE
  ADD CONSTRAINT FK_CODETEMPL_PROJECTTEMPL FOREIGN KEY (PROJECTTEMPLATE_ID)
  REFERENCES PROJECTTEMPLATE (ID);

ALTER TABLE CODETEMPLATE_PROJECT
  ADD CONSTRAINT FK_CODETEMPLATE_PROJECT FOREIGN KEY (PROJECT_ID) REFERENCES
  PROJECT (ID);

ALTER TABLE CODETEMPLATE_PROJECT
  ADD CONSTRAINT FK_CODETEMPL_PROJ_CODETEMPL FOREIGN KEY (CODETEMPLATE_ID)
  REFERENCES CODETEMPLATE (ID);

ALTER TABLE DOMAIN
  ADD CONSTRAINT FK_DOMAIN_PROJECT FOREIGN KEY (PROJECT_ID) REFERENCES
  PROJECT (ID);

ALTER TABLE DOMAIN_ATTRIBUTES
  ADD CONSTRAINT FK_DOMAIN_ATTR_ATTR FOREIGN KEY (ATTRIBUTES_ID)
  REFERENCES ATTRIBUTE (ID);

ALTER TABLE DOMAIN_ATTRIBUTES
  ADD CONSTRAINT FK_DOMAIN_ATTR_DOMAIN  FOREIGN KEY (DOMAIN_ENTITY_ID)
  REFERENCES DOMAIN (ID);

ALTER TABLE PROJECT
  ADD CONSTRAINT FK_PROJECT_PROJECTTEMPL FOREIGN KEY (TEMPLATE_ID)
  REFERENCES PROJECTTEMPLATE (ID);
