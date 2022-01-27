const translationsEnEN = {
    "app.title": "App Title",
    "button.create": "Create",
    "button.update": "Update",
    "button.delete": "Delete",
    "button.submit": "Confirm",
    "button.next": "Next",
    "button.edit": "Edit",
    "button.back": "Back",
    "button.save": "Save",
    "button.retry": "Retry",
    "button.download": "Download",
    "button.cancel": "Cancel",
    "button.yes": "Yes",
    "button.no": "No",
    "button.ok": "OK",
    "button.loadtemplate": "Refresh from Git-Repo",
    "select.groups": "Groups",
    "form.create": "create {{entity}}",
    "form.update": "update {{entity}}",
    "alert.error": "Error",

    "###GENERATION###": "Generierung",

    "home.title": "Home",
    "home.noApps": "No apps created. Please add one on the lower right.",

    "appCard.sureToDelete.title": "Delete app",
    "appCard.sureToDelete.message": "This operation cannot be reversed.",

    "appTemplateDeleteDialog.title": "Delete Template",
    "appTemplateDeleteDialog.message": "Do you want to delete the template?",
    "appTemplateErrorDialog.title": "Error",
    "appTemplateErrorDialog.deleteMessage": "Template could not be deleted.",
    "appTemplateSuccessDialog.title": "Success Template Upload",
    "appTemplateSuccessDialog.message": "Templates were loaded successfully.",

    "entityCard.newEntity": "New Domain",
    "entityCard.noFields": "No fields",
    "entityCard.name": "Name",
    "entityCard.dataType": "Data type",

    "entityDialog.noRelations": "No relations.",
    "entityDialog.noFields": "No fields.",
    "entityDialog.domainIsLoading": "Domain is loading.",
    "entityDialog.editDomain": "Edit domain.",
    "entityDialog.tab.fields": "Fields",
    "entityDialog.tab.relations": "Relations",
    "entityDialog.name": "Name",
    "entityDialog.name.error": "Upper and lower case letters are allowed with numbers. First character must be a upper case letter.",

    "fieldDialog.newField": "New field",
    "fieldDialog.name": "Name",
    "fieldDialog.name.error": "Upper and lower case letters are allowed with numbers. First character must be a lower case letter.",
    "fieldDialog.dataType": "Data type",
    "fieldDialog.description": "Description",
    "fieldDialog.restrictions": "Restrictions",
    "fieldDialog.pattern": "Pattern",
    "fieldDialog.min": "Minimum",
    "fieldDialog.max": "Maximum",
    "fieldDialog.mandatoryField": "Mandatory.",

    "appHeader.apps": "Apps",
    "appHeader.apptemplates": "Templates",
    "appHeader.store": "Store",

    "relationship.newRelation": "New relation",
    "relationship.relationType": "Relation type",
    "relationship.sourceEntity": "Source Entity",
    "relationship.sourceField": "Relationship Name",
    "relationship.sourceField.description": "Name of field with type {{entityName}}. Upper and lower case letters are allowed with numbers. First character must be a lower case letter.",
    "relationship.source": "from",
    "relationship.target": "to",
    "relationship.targetEntity": "Target Entity",
    "relationship.targetField": "Relationship Name",
    "relationship.targetField.description": "Name of field with type {{entityName}}. Upper and lower case letters are allowed with numbers. First character must be a lower case letter.",
    "relationship.targetEntity.empty": "not available",

    "templateCard.select": "Select",
    "templateCard.selected": "Selected",
    "templateCard.branch": "Branch",
    "templateCard.config": "Template Configuration",

    "appEditor.section.template.title": "Template",
    "appEditor.section.erDesigner.title": "ER-Designer",
    "appEditor.section.conclusion.title": "Conclusion",
    "appEditor.section.general.title": "General",
    "appEditor.loading": "App wird geladen",
    "app.erdiagram": "ER-Diagram",

    "appTemplateOverview.title": "Templates",
    "appTemplateDialog.new.title": "Create Template",
    "appTemplateDialog.title": "Edit {{appTemplateName}}",
    "appTemplateDialog.isLoading": "loading...",
    "appTemplateDialog.location": "Git-Repository",
    "appTemplateDialog.branch": "Branch",
    "appTemplateDialog.credentialsRequired": "private repo with Login",
    "appTemplateDialog.description": "Description",
    "appTemplateDialog.location.error": "Location has to be a valid git-repository URL",
    "appTemplateDialog.branch.error": "Branch name contain letters, nubmers and characters /_- with a max length of 100",    

    "appTemplateAuthDialog.title": "Login Git Template Repository",
    "appTemplateAuthDialog.user": "User",
    "appTemplateAuthDialog.password": "Password",

    "appOverview.app.isLoading": "App is loading",
    "appOverview.app.doesNotExist": "The provided App does not exist",
    "appOverview.app.error": "An error occurred while loading the app.",

    "appTemplateAuthDialog.user.error": "User should not be empty and can contain letters, numbers and special characters -!@#$%^&",
    "appTemplateAuthDialog.password.error": "Password / PAT should not be empty and can contain letters, numbers and special characters -!@#$%^&",
  
    "generalSection.hello": "Hello!",
    "generalSection.enterInformation": "Please enter as a first step generation information to your app.",
    "generalSection.clickNext": "Afterwards, please click next on the upper right side to continue.",

    "generalSection.nameOfApp": "Name of App",
    "generalSection.nameOfApp.error": "Upper and lower cased chars and numbers are allowed",
    "generalSection.packageNameOfApp": "Package name",
    "generalSection.packageNameOfApp.error": "Upper and lower cased chars and numbers are allowed",

    "templateSection.loading": "Templates loading...",
    "templateSection.loading.error": "Templates could not be loaded",

    "entityDesigner.code": "Code",

    "home.yourApps": "Your Apps",
    "home.loading": "Your apps are loading",
    "home.loadingError": "Your apps could not be loaded.",

    "default.welcome": "Welcome",
    "default.error.403.title": "No Right",
    "default.error.403": "Login worked but you are not allowed.",
    "default.error.title": "Error",
    "default.error.500": "Internal Server Error. Please contact your admin.",

    "error.git.directorynotexists": "An internal error occured. Please contact IT-support.",
    "error.git.access.denied": "Not enough rights. For Github you need at least the right 'repo'",
    "error.git.access.tokenusagerequired": "Support for password authentication was removed. A personal access token is required.",
    "error.git.access.invaliduserpassword": "Invalid username or password. Only letters, numbers and !@#$%^&()*./_- are allowed.",
    "error.git.exit": "Error to get data from Git repository",
    "error.appcheckout.giturlisinvalid": "Invalid Git repository URL. Only letters, numbers and ./_- are allowed.",
    "error.appcheckout.usernameisinvalid":"Invalid username or password. Only letters, numbers and !@#$%^&()*./_- are allowed.",
    "error.appcheckout.passworisinvalid": "Invalid username or password. Only letters, numbers and !@#$%^&()*./_- are allowed.",
    "error.appcheckout.branchisinvalid": "Invalid branch. Only letters, numbers, and /_- are allowed.",
    "error.appcheckout.jsonmapping.git": "Template configuration could not be loaded. Please check config file (template-config.json).",
    "error.git.access.repositorynotfound": "Git repository could not be found. Please check repository name and branch name.",
    "error.accessdenied": "You are not allowed to execute this action."
};
export default translationsEnEN;
