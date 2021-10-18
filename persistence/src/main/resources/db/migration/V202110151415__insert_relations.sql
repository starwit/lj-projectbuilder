CREATE TABLE RELATIONSHIP
  (
     ID          BIGINT NOT NULL AUTO_INCREMENT,
     TYPE        VARCHAR(255) NOT NULL,
     OTHER_ENTITY_NAME VARCHAR(255) NOT NULL,
     OTHER_ENTITY_RELATIONNAME VARCHAR(255) NOT NULL,
     NAME        VARCHAR(255) NOT NULL,
     OWNER boolean NOT NULL,
     PRIMARY KEY (ID)
  );
CREATE UNIQUE INDEX RELATIONSHIP_INDEX ON RELATIONSHIP(ID);

CREATE TABLE DOMAIN_RELATIONSHIPS
  (
     DOMAIN_ID BIGINT NOT NULL,
     RELATIONSHIPS_ID    BIGINT NOT NULL,
     PRIMARY KEY (DOMAIN_ID, RELATIONSHIPS_ID)
  );
CREATE INDEX D_R_RELATIONSHIP_INDEX ON DOMAIN_RELATIONSHIPS(RELATIONSHIPS_ID);
CREATE INDEX D_R_ATTRIBUTE_INDEX ON DOMAIN_RELATIONSHIPS(DOMAIN_ID);

ALTER TABLE DOMAIN_RELATIONSHIPS
  ADD CONSTRAINT FK_DOMAIN_REL_REL FOREIGN KEY (RELATIONSHIPS_ID)
  REFERENCES RELATIONSHIP (ID);

ALTER TABLE DOMAIN_RELATIONSHIPS
  ADD CONSTRAINT FK_DOMAIN_REL_DOMAIN  FOREIGN KEY (DOMAIN_ID)
  REFERENCES DOMAIN (ID);
