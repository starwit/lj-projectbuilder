-- all templates for category REST
INSERT INTO CATEGORY (NAME) VALUES ('REST');
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'Rest.java', 'rest/rest.ftl', '${projecthome}/${project.targetPath}/${project.title}/rest/src/main/java/de/${project.packagePrefix?lower_case}/${project.title?lower_case}/api/rest/', false, true, false, 'DOMAIN', 
	SELECT ID FROM CATEGORY WHERE NAME LIKE 'REST'
);
	
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'RestfulApplication.java', 'rest/restfulApplication.ftl', '${projecthome}/${project.targetPath}/${project.title}/rest/src/main/java/de/${project.packagePrefix?lower_case}/${project.title?lower_case}/api/restapp/', false, false, false, 'GLOBAL', 
	SELECT ID FROM CATEGORY WHERE NAME LIKE 'REST'
);

INSERT INTO CODETEMPLATE_PROJECT (
	CODETEMPLATE_ID, PROJECT_ID
) VALUES (
	SELECT ID FROM CODETEMPLATE WHERE FILE_NAME_SUFFIX LIKE 'Rest.java',
	SELECT ID FROM PROJECT WHERE TITLE LIKE 'CompetencyMatrix'
);

INSERT INTO CODETEMPLATE_PROJECT (
	CODETEMPLATE_ID, PROJECT_ID
) VALUES (
	SELECT ID FROM CODETEMPLATE WHERE FILE_NAME_SUFFIX LIKE 'RestfulApplication.java',
	SELECT ID FROM PROJECT WHERE TITLE LIKE 'CompetencyMatrix'
);