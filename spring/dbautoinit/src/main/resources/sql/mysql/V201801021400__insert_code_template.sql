-- all templates for category ENTITY
INSERT INTO CATEGORY (NAME) VALUES ('ENTITY');
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'Entity.java', 'entity/entity.ftl', '${projecthome}/${project.targetPath}/${project.title}/persistence/src/main/java/de/${project.packagePrefix?lower_case}/${project.title?lower_case}/entity/', false, true, false, 'DOMAIN', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'ENTITY')
	);
	
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'persistence.xml', 'entity/persistence.ftl', '${projecthome}/${project.targetPath}/${project.title}/persistence/src/test/resources/META-INF/', false, false, false, 'ADDITIONAL_CONTENT',
	(SELECT ID FROM CATEGORY WHERE NAME like 'ENTITY')
	);

-- all templates for category SERVICE
INSERT INTO CATEGORY (NAME) VALUES ('SERVICE');
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'Service.java', 'service/service.ftl', '${projecthome}/${project.targetPath}/${project.title}/persistence/src/main/java/de/${project.packagePrefix?lower_case}/${project.title?lower_case}/ejb/', false, true, false, 'DOMAIN', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'SERVICE')
	);
	
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'ServiceImpl.java', 'service/serviceImpl.ftl', '${projecthome}/${project.targetPath}/${project.title}/persistence/src/main/java/de/${project.packagePrefix?lower_case}/${project.title?lower_case}/ejb/impl/', false, true, false, 'DOMAIN', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'SERVICE')
	);
	
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'ServiceTest.java', 'service/serviceTest.ftl', '${projecthome}/${project.targetPath}/${project.title}/persistence/src/test/java/de/${project.packagePrefix?lower_case}/${project.title?lower_case}/ejb/', false, true, false, 'DOMAIN', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'SERVICE')
	);	
	
-- all templates for category REST
INSERT INTO CATEGORY (NAME) VALUES ('REST');
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'Rest.java', 'rest/rest.ftl', '${projecthome}/${project.targetPath}/${project.title}/rest/src/main/java/de/${project.packagePrefix?lower_case}/${project.title?lower_case}/api/rest/', false, true, false, 'DOMAIN', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'REST')
);
	
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'RestfulApplication.java', 'rest/restfulApplication.ftl', '${projecthome}/${project.targetPath}/${project.title}/rest/src/main/java/de/${project.packagePrefix?lower_case}/${project.title?lower_case}/api/restapp/', false, false, false, 'GLOBAL', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'REST')
);

-- all templates for category FRONTEND
INSERT INTO CATEGORY (NAME) VALUES ('FRONTEND');
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'.all.html', 'webclient/all.ftl', '${projecthome}/${project.targetPath}/${project.title}/webclient/src/main/webapp/viewcomponents/', true, false, true, 'DOMAIN', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'FRONTEND')
);

-- DOMAIN
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'AllCtrl.js', 'webclient/AllCtrl.ftl', '${projecthome}/${project.targetPath}/${project.title}/webclient/src/main/webapp/viewcomponents/', true, false, true, 'DOMAIN', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'FRONTEND')
);	
	
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'.single.html', 'webclient/single.ftl', '${projecthome}/${project.targetPath}/${project.title}/webclient/src/main/webapp/viewcomponents/', true, false, true, 'DOMAIN', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'FRONTEND')
);

INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'SingleCtrl.js', 'webclient/SingleCtrl.ftl', '${projecthome}/${project.targetPath}/${project.title}/webclient/src/main/webapp/viewcomponents/', true, false, true, 'DOMAIN', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'FRONTEND')
);

INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'.routes.js', 'webclient/routes.ftl', '${projecthome}/${project.targetPath}/${project.title}/webclient/src/main/webapp/viewcomponents/', true, false, true, 'DOMAIN', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'FRONTEND')
);

INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'.module.js', 'webclient/module.ftl', '${projecthome}/${project.targetPath}/${project.title}/webclient/src/main/webapp/viewcomponents/', true, false, true, 'DOMAIN', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'FRONTEND')
);

INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'.connector.factory.js', 'webclient/connector.ftl', '${projecthome}/${project.targetPath}/${project.title}/webclient/src/main/webapp/shared/restfacade/', false, false, true, 'DOMAIN', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'FRONTEND')
);

-- ADDITIONAL_CONTENT
INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'index.html', 'webclient/scripts.ftl', '${projecthome}/${project.targetPath}/${project.title}/webclient/src/main/webapp/', false, false, false, 'ADDITIONAL_CONTENT', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'FRONTEND')
);

INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'app.module.js', 'webclient/app.module.ftl', '${projecthome}/${project.targetPath}/${project.title}/webclient/src/main/webapp/', false, false, false, 'ADDITIONAL_CONTENT', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'FRONTEND')
);

INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'menu.html', 'webclient/menu.ftl', '${projecthome}/${project.targetPath}/${project.title}/webclient/src/main/webapp/', false, false, false, 'ADDITIONAL_CONTENT', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'FRONTEND')
);

INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'translations-de-DE.json', 'webclient/translations-de-DE.ftl', '${projecthome}/${project.targetPath}/${project.title}/webclient/src/main/webapp/localization/', false, false, false, 'ADDITIONAL_CONTENT', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'FRONTEND')
);

INSERT INTO CODETEMPLATE (
	FILE_NAME_SUFFIX, TEMPLATE_PATH, TARGET_PATH, CREATE_DOMAIN_DIR, FIRST_UPPER, LOWER_CASE, TEMPLATE_TYPE, CATEGORY_ID
	) VALUES (
	'translations-en-US.json', 'webclient/translations-en-US.ftl', '${projecthome}/${project.targetPath}/${project.title}/webclient/src/main/webapp/localization/', false, false, false, 'ADDITIONAL_CONTENT', 
	(SELECT ID FROM CATEGORY WHERE NAME LIKE 'FRONTEND')
);
