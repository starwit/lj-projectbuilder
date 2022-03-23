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
    "button.done": "Done",
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

    "apps.title": "Apps",
    "apps.empty": "No apps created. Please add one on the lower right.",
    "apps.loading": "apps are loading",
    "apps.loadingError": "Apps could not be loaded.",

    "apptemplates.title": "Templates",
    "apptemplates.empty":
        "No Templates created. Please add one on the lower right.",
    "apptemplates.loading": "templates are loading",
    "apptemplates.loadingError": "Templates could not be loaded.",

    "app.loading": "app is loading",
    "app.LoadingError": "An error occurred while loading the app.",
    "app.doesNotExist": "The provided App does not exist",
    "app.delete.title": "Delete App",
    "app.delete.message": "Are you sure to delete this App?",
    "app.name": "Name of App",
    "app.name.hint": "Upper and lower cased chars and numbers are allowed",
    "app.packageName": "Package name",
    "app.packageName.hint":
        "Upper and lower cased chars and numbers are allowed",
    "app.template.select": "Select",
    "app.template.selected": "Selected",
    "app.entities.empty": "no entities",

    "app.section.general": "General",
    "app.section.general.hello": "Hello!",
    "app.section.general.enterInformation":
        "Please enter as a first step generation information to your app.",
    "app.section.general.clickNext":
        "Afterwards, please click next on the upper right side to continue.",
    "app.section.template": "Template",
    "app.section.entityDiagram": "Entity-Relationship Diagram",
    "app.section.conclusion": "Conclusion",

    "entity.new": "New Entity",
    "entity.edit": "Edit {{entityName}}",
    "entity.loading": "entity is loading",
    "entity.code": "Code",
    "entity.name": "Name",
    "entity.name.hint":
        "Upper and lower case letters are allowed with numbers. First character must be a upper case letter.",
    "entity.fields": "Fields",
    "entity.fields.empty": "no fields",
    "entity.relations": "Relations",
    "entity.relations.empty": "no relations",

    "field.new": "New field",
    "field.fieldName": "Name",
    "field.fieldName.hint":
        "Upper and lower case letters are allowed with numbers. First character must be a lower case letter.",
    "field.fieldType": "Type",
    "field.restrictions": "Restrictions",
    "field.pattern": "Pattern",
    "field.min": "Minimum",
    "field.max": "Maximum",
    "field.required": "Mandatory.",

    "relationship.new": "New relation",
    "relationship.relationshipType": "Relation type",
    "relationship.sourceEntity": "Source Entity",
    "relationship.sourceField": "Relationship Name",
    "relationship.sourceField.hint":
        "Name of field with type {{entityName}}. Upper and lower case letters are allowed with numbers. First character must be a lower case letter.",
    "relationship.source": "from",
    "relationship.target": "to",
    "relationship.targetEntity": "Target Entity",
    "relationship.targetField": "Relationship Name",
    "relationship.targetField.hint":
        "Name of field with type {{entityName}}. Upper and lower case letters are allowed with numbers. First character must be a lower case letter.",
    "relationship.targetEntity.empty": "not available",

    "appTemplate.delete.title": "Delete Template",
    "appTemplate.delete.message": "Do you want to delete the template?",
    "appTemplate.error.title": "Error",
    "appTemplate.error.message": "Template could not be deleted.",
    "appTemplate.success.title": "Success Template Upload",
    "appTemplate.success.message": "Templates were loaded successfully.",

    "apptemplate.new": "New Template",
    "apptemplate.edit": "Edit {{appTemplateName}}",
    "apptemplate.loading": "template is loading",
    "apptemplate.location": "Git-Repository",
    "apptemplate.location.hint":
        "Location has to be a valid git-repository URL",
    "appTemplate.branch": "Branch",
    "appTemplate.config": "Template Configuration",
    "appTemplate.credentialsRequired": "private repo with Login",
    "appTemplate.description": "Description",
    "appTemplate.branch.hint":
        "Branch name contain letters, nubmers and characters /_- with a max length of 100",

    "gitAuth.title": "Login Git Template Repository",
    "gitAuth.username": "User",
    "gitAuth.username.hint":
        "User should not be empty and can contain letters, numbers and special characters -!@#$%^&",
    "gitAuth.password": "Password",
    "gitAuth.password.hint":
        "Password / PAT should not be empty and can contain letters, numbers and special characters -!@#$%^&",

    "error.git.directorynotexists":
        "An internal error occured. Please contact IT-support.",
    "error.git.access.denied":
        "Not enough rights. For Github you need at least the right 'repo'",
    "error.git.access.tokenusagerequired":
        "Support for password authentication was removed. A personal access token is required.",
    "error.git.access.invaliduserpassword":
        "Invalid username or password. Only letters, numbers and !@#$%^&()*./_- are allowed.",
    "error.git.exit": "Error to get data from Git repository",
    "error.appcheckout.giturlisinvalid":
        "Invalid Git repository URL. Only letters, numbers and ./_- are allowed.",
    "error.appcheckout.usernameisinvalid":
        "Invalid username or password. Only letters, numbers and !@#$%^&()*./_- are allowed.",
    "error.appcheckout.passworisinvalid":
        "Invalid username or password. Only letters, numbers and !@#$%^&()*./_- are allowed.",
    "error.appcheckout.branchisinvalid":
        "Invalid branch. Only letters, numbers, and /_- are allowed.",
    "error.appcheckout.jsonmapping.git":
        "Template configuration could not be loaded. Please check config file (template-config.json).",
    "error.git.access.repositorynotfound":
        "Git repository could not be found. Please check repository name and branch name.",
    "error.accessdenied": "You are not allowed to execute this action.",
    "error.general.delete": "This element could not be deleted.",
    "error.general.create": "This element could not be created.",
    "error.general.update": "This element could not be updated.",
    "error.general.get": "This element could not be loaded.",
    "error.serverOffline": "The server seems to be offline.",
    "error.userOffline": "You appear to be offline.",
    "error.unknown": "An unknown error occurred.",
    "error.apptemplate.notfound": "The Template could not be found.",
    "error.app.notfound": "This App could not be found.",
    "error.entity.notfound": "This element could not be found.",
    "error.wrongAppId": "This app id is wrong.",
    "error.internalServerError": "An Server error occurred.",
    "error.invalidDefinition": "The given definition is invalid.",
    "error.unauthorized": "You are not authorized to perform this action.",
    "error.wrongInputValue": "The input valid is invalid.",
    "error.badrequest": "This request is invalid.",
    "error.notfound": "This element could not be found.",
    "error.notexists": "This element does not exist.",
};
export default translationsEnEN;
