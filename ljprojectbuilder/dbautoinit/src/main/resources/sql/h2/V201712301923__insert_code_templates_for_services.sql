-- all templates for category SERVICE
INSERT INTO CATEGORY (NAME) VALUES ('SERVICE');
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'Service.java', 'service/service.ftl', '${projecthome}/${project.targetPath}/${project.title}/persistence/src/main/java/de/${project.packagePrefix?lower_case}/${project.title?lower_case}/ejb/', false, true, false, 'DOMAIN', 
	SELECT ID FROM CATEGORY WHERE NAME LIKE 'SERVICE'
	);
	
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'ServiceImpl.java', 'service/serviceImpl.ftl', '${projecthome}/${project.targetPath}/${project.title}/persistence/src/main/java/de/${project.packagePrefix?lower_case}/${project.title?lower_case}/ejb/impl/', false, true, false, 'DOMAIN', 
	SELECT ID FROM CATEGORY WHERE NAME LIKE 'SERVICE'
	);
	
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'ServiceTest.java', 'service/serviceTest.ftl', '${projecthome}/${project.targetPath}/${project.title}/persistence/src/test/java/de/${project.packagePrefix?lower_case}/${project.title?lower_case}/ejb/', false, true, false, 'DOMAIN', 
	SELECT ID FROM CATEGORY WHERE NAME LIKE 'SERVICE'
	);	

INSERT INTO CODETEMPLATE_PROJECT (
	CODETEMPLATE_ID, PROJECT_ID
) VALUES (
	SELECT ID FROM CODETEMPLATE WHERE FILE_NAME_SUFFIX LIKE 'Service.java',
	SELECT ID FROM PROJECT WHERE TITLE LIKE 'CompetencyMatrix'
);

INSERT INTO CODETEMPLATE_PROJECT (
	CODETEMPLATE_ID, PROJECT_ID
) VALUES (
	SELECT ID FROM CODETEMPLATE WHERE FILE_NAME_SUFFIX LIKE 'ServiceImpl.java',
	SELECT ID FROM PROJECT WHERE TITLE LIKE 'CompetencyMatrix'
);

INSERT INTO CODETEMPLATE_PROJECT (
	CODETEMPLATE_ID, PROJECT_ID
) VALUES (
	SELECT ID FROM CODETEMPLATE WHERE FILE_NAME_SUFFIX LIKE 'ServiceTest.java',
	SELECT ID FROM PROJECT WHERE TITLE LIKE 'CompetencyMatrix'
);