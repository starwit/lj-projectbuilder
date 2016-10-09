    create table ATTRIBUTE (
        ID bigint generated by default as identity,
        "TYPE" varchar(255) not null,
        DESCRIPTION varchar(255),
        MAXIMUM integer,
        MINIMUM integer,
        NAME varchar(255) not null,
        NULLABLE boolean not null,
        PATTERN varchar(255),
        primary key (ID)
    );

    create table DOMAIN (
        ID bigint generated by default as identity,
        DESCRIPTION varchar(255),
        NAME varchar(255) not null,
        PROJECT_ID bigint not null,
        primary key (ID)
    );
    
    create table DOMAIN_ATTRIBUTE (
        DomainEntity_id bigint not null,
        attributes_id bigint not null
    );
    
    create table PROJECT (
        ID bigint generated by default as identity,
        DESCRIPTION varchar(255),
        PACKAGEPREFIX varchar(100) not null,
        TARGETPATH varchar(100) not null,
        TITLE varchar(100) not null,
        TEMPLATE_ID bigint not null,
        primary key (ID)
    );
    
    create table TEMPLATE (
        ID bigint generated by default as identity,
        TEMPLATELOCATION varchar(100) not null,
        TEMPLATEPREFIX varchar(100) not null,
        TEMPLATE_TITLE varchar(100) not null,
        primary key (ID)
    );
    
    alter table DOMAIN_ATTRIBUTE 
        add constraint UK_398xqpqykyiisf5bq2t7i4k07 unique (attributes_id);
    
    alter table DOMAIN 
        add constraint FKtpeo9g9095mvoydjdgtiiquik 
        foreign key (PROJECT_ID) 
        references PROJECT;
    
    alter table DOMAIN_ATTRIBUTE 
        add constraint FK2ajlpwqnue9omba55n0v260ov 
        foreign key (attributes_id) 
        references ATTRIBUTE;
    
    alter table DOMAIN_ATTRIBUTE 
        add constraint FKsdxn54tjb1k7bqdaaxkywf4wk 
        foreign key (DomainEntity_id) 
        references DOMAIN;
    
    alter table PROJECT 
        add constraint FKbql7ejxh6qndtept9yxovcm2r 
        foreign key (TEMPLATE_ID) 
        references TEMPLATE;
        