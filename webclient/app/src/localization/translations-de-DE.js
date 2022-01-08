const translationsDeDE = {
    "app.title": "App Überschrift",
    "button.create": "Erstellen",
    "button.update": "Updaten",
    "button.delete": "Löschen",
    "button.submit": "Bestätigen",
    "button.next": "Bestätigen",
    "button.edit": "Bearbeiten",
    "button.back": "Zurück",
    "button.cancel": "Abbrechen",
    "button.ok": "OK",
    "button.save": "Speichern",
    "button.retry": "Erneut versuchen",
    "button.no": "Nein",
    "button.yes": "Ja",
    "button.loadtemplate": "Aus Git-Repo aktualisieren",
    "form.create": "{{entity}} anlegen",
    "form.update": "{{entity}} updaten",
    "alert.error": "Fehler",

    "###GENERATION###": "Generierung",

    "home.title": "Home",

    "appTemplateDeleteDialog.title": "Template löschen",
    "appTemplateDeleteDialog.message": "Wollen Sie das Template wirklich löschen?",
    "appTemplateErrorDialog.title": "Fehler",
    "appTemplateErrorDialog.deletemessage": "Das Template konnte nicht gelöscht werden.",
    "appTemplateSuccessDialog.title": "Template Aktualisierung erfolgreich",
    "appTemplateSuccessDialog.message": "Die Templates wurden erfolgreich aus dem Git-Repository geladen.",

    "entityCard.newEntity": "Neue Domain",
    "entityCard.noFields": "Keine Felder angelegt",
    "entityCard.name": "Name",
    "entityCard.dataType": "Datentyp",

    "entityDialog.noRelations": "Keine Relationen angelegt.",
    "entityDialog.noFields": "Keine Felder angelegt.",
    "entityDialog.domainIsLoading": "Domain wird geladen.",
    "entityDialog.editDomain": "Domain bearbeiten.",
    "entityDialog.tab.fields": "Felder",
    "entityDialog.tab.relations": "Relationen",
    "entityDialog.name": "Name",
    "entityDialog.name.error": "Groß und Kleinschreibung mit Zahlen erlaubt. Erstes Zeichen muss ein großer Buchstabe sein.",

    "fieldDialog.newField": "Neues Feld",
    "fieldDialog.name": "Name",
    "fieldDialog.name.error": "Groß und Kleinschreibung mit Zahlen erlaubt. Erstes Zeichen muss ein kleiner Buchstabe sein.",
    "fieldDialog.dataType": "Datentyp",
    "fieldDialog.description": "Beschreibung",
    "fieldDialog.restrictions": "Restriktionen",
    "fieldDialog.pattern": "Pattern",
    "fieldDialog.min": "Minimum",
    "fieldDialog.max": "Maximum",
    "fieldDialog.mandatoryField": "Pflichtfeld.",

    "appHeader.apps": "Apps",
    "appHeader.apptemplates": "Templates",
    "appHeader.store": "Store",

    "relationshipAccordion.newRelation": "Neue Relation",
    "relationshipAccordion.relationType": "Relationstyp",
    "relationshipAccordion.sourceField": "Quell-Feld",
    "relationshipAccordion.source": "Quelle",
    "relationshipAccordion.target": "Ziel",
    "relationshipAccordion.targetDomain": "Ziel-Domain",
    "relationshipAccordion.targetField": "Ziel-Feld",

    "templateCard.select": "Auswählen",
    "templateCard.selected": "Ausgewählt",
    "templateCard.branch": "Branch",
    "templateCard.config": "Template Konfiguration",

    "home.yourApps": "Deine Apps",
    "home.loading": "Deine Apps werden geladen",
    "home.loadingError": "Deine Apps konnten nicht geladen werden.",

    "appEditor.section.template.title": "Template",
    "appEditor.section.erDesigner.title": "ER-Designer",
    "appEditor.section.conclusion.title": "Abschluss",
    "appEditor.section.general.title": "General",

    "entityDesigner.code": "Code",

    "appTemplateOverview.title": "Templates",
    "appTemplateDialog.new.title": "Template erstellen",
    "appTemplateDialog.title": "{{appTemplateName}} bearbeiten",
    "appTemplateDialog.isLoading": "Die Seite wird noch geladen",
    "appTemplateDialog.location": "Git-Repository",
    "appTemplateDialog.branch": "Branch",
    "appTemplateDialog.credentialsRequired": "privates Repo mit Login",
    "appTemplateDialog.description": "Beschreibung",

    "appTemplateAuthDialog.title": "Login Git Template Repository",
    "appTemplateAuthDialog.user": "Nutzername",
    "appTemplateAuthDialog.password": "Passwort",

    "appOverview.app.isLoading": "App wird geladen...",
    "appOverview.app.doesNotExist": "Die angegebene App exisitert nicht",

    "generalSection.nameOfApp": "Name der App",
    "generalSection.nameOfApp.error": "Zugelassen sind Groß- / Kleinbuchstaben und Zahlen",
    "generalSection.packageNameOfApp": "Package Name",
    "generalSection.packageNameOfApp.error": "Zugelassen sind Groß- / Kleinbuchstaben und Zahlen",
    "generalSection.hello": "Hallo!",
    "generalSection.enterInformation": "Bitte gib als ersten Schritt die allgemeinen Informationen zu deiner App ein.",
    "generalSection.clickNext": "Klicke dann auf Weiter auf der oberen rechten Seite.",

    "templateSection.loading": "Templates werden geladen...",
    "templateSection.loading.error": "Templates konnten nicht geladen werden",

    "default.welcome": "Herzlich Willkommen",
    "default.error.403.title": "Fehlende Berechtigungen",
    "default.error.403": "Sie besitzen keine Berechtigungen zur Anzeige dieser Seite",
    "default.error.title": "Fehler",
    "default.error.500": "Interner Fehler, bitte kontaktieren Sie Ihren Admin.",

    "error.git.directorynotexists": "Ein interner Fehler ist aufgetreten. Bitte wenden Sie sich an den IT-Support.",
    "error.git.access.denied": "Sie benötigen Rechte zum Ausführen von git clone. In Github verwenden Sie das Recht 'repo'.",
    "error.git.access.tokenusagerequired": "Der Support für Nutzer/Passwort Authetifizierung ist ausgelaufen. Sie müssen einen Personal Access Token verwenden.",
    "error.git.access.invaliduserpassword": "Nuter oder Passwort sind ungültig.",
    "error.git.exit": "Fehler beim Abrufen der Daten vom Git-Repository.",
    "error.appcheckout.giturlisinvalid": "Ungültige Git-Repository URL",
    "error.appcheckout.jsonmapping.git": "Die Templatekonfiguration konnte nicht heruntergeladen werden. Bitte überprüfen Sie den Namen der Konfigurationsdatei. Sie muss template-config.json heißen.",
    "error.git.access.repositorynotfound": "Das angegebene Git-Repository konnte nicht gefunden werden. Bitte überpüfen Sie Repository-URL und Branch-Namen."
};
export default translationsDeDE;