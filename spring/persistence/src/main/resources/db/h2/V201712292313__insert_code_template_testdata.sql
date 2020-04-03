-- all templates for category ENTITY
INSERT INTO CATEGORY (NAME) VALUES ('ENTITY');
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'Entity.java', 'entity/entity.ftl', '${projecthome}/${project.targetPath}/${project.title}/persistence/src/main/java/de/${project.packagePrefix?lower_case}/${project.title?lower_case}/entity/', false, true, false, 'DOMAIN', 
	SELECT ID FROM CATEGORY WHERE NAME LIKE 'ENTITY'
	);
	
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'persistence.xml', 'entity/persistence.ftl', '${projecthome}/${project.targetPath}/${project.title}/persistence/src/test/resources/META-INF/', false, false, false, 'ADDITIONAL_CONTENT',
	SELECT ID FROM CATEGORY WHERE NAME like 'ENTITY'
	);
	
INSERT INTO CODETEMPLATE_PROJECT (
	CODETEMPLATE_ID, PROJECT_ID
) VALUES (
	SELECT ID FROM CODETEMPLATE WHERE FILE_NAME_SUFFIX LIKE 'Entity.java',
	SELECT ID FROM PROJECT WHERE TITLE LIKE 'CompetencyMatrix'
);

INSERT INTO CODETEMPLATE_PROJECT (
	CODETEMPLATE_ID, PROJECT_ID
) VALUES (
	SELECT ID FROM CODETEMPLATE WHERE FILE_NAME_SUFFIX LIKE 'persistence.xml',
	SELECT ID FROM PROJECT WHERE TITLE LIKE 'CompetencyMatrix'
);

-- all templates for category SERVICE
	

-- all templates for category REST
	

-- all templates for category FRONTEND