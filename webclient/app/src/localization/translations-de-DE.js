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
    "button.done": "Fertig",
    "button.retry": "Erneut versuchen",
    "button.download": "Herunterladen",
    "button.no": "Nein",
    "button.yes": "Ja",
    "button.showMore": "Mehr anzeigen",
    "button.close": "Schließen",
    "button.loadtemplate": "Aus Git-Repo aktualisieren",
    "select.groups": "Gruppen",
    "form.create": "{{entity}} anlegen",
    "form.update": "{{entity}} aktualisieren",
    "alert.error": "Fehler",

    "apps.title": "Apps",
    "apps.empty": "Es wurden keine Apps angelegt. Bitte unten Rechts eine App hinzufügen.",
    "apps.loading": "die Apps werden geladen",
    "apps.loadingError": "Die Apps konnten nicht geladen werden.",

    "apptemplates.title": "Templates",
    "apptemplates.empty": "Es wurden keine Templates angelegt. Bitte fügen Sie unten rechts ein Template hinzu.",
    "apptemplates.loading": "die Templates werden geladen",
    "apptemplates.loadingError": "Die Templates konnten nicht geladen werden.",

    "app.loading": "Apps wird geladen",
    "app.LoadingError": "Es ist ein Fehler beim Laden aufgetreten.",
    "app.doesNotExist": "Die angegebene App exisitert nicht.",
    "app.delete.title": "App löschen",
    "app.delete.message": "Wollen Sie diese App wirklich löschen?",
    "app.name": "Name der App",
    "app.name.hint": "Zugelassen sind Groß- / Kleinbuchstaben und Zahlen",
    "app.packageName": "Package Name",
    "app.packageName.hint": "Zugelassen sind Groß- / Kleinbuchstaben und Zahlen",
    "app.template.select": "Auswählen",
    "app.template.selected": "Ausgewählt",
    "app.entities.empty": "keine Entitäten vorhanden",

    "app.section.general": "General",
    "app.section.general.hello": "Hallo!",
    "app.section.general.enterInformation": "Bitte geben Sie als ersten Schritt die allgemeinen Informationen zu Ihrer App ein.",
    "app.section.general.clickNext": "Klicken Sie dann auf Weiter auf der oberen rechten Seite.",
    "app.section.template": "Template",
    "app.section.entityDiagram": "Entity-Relationship-Diagramm",
    "app.section.conclusion": "Abschluss",

    "entity.new": "Neue Entität",
    "entity.edit": "{{entityName}} bearbeiten",
    "entity.loading": "Entität wird geladen",
    "entity.code": "Code",
    "entity.name": "Name",
    "entity.name.hint": "Groß und Kleinschreibung mit Zahlen erlaubt. Erstes Zeichen muss ein großer Buchstabe sein.",
    "entity.fields": "Felder",
    "entity.fields.empty": "keine Felder angelegt",
    "entity.relations": "Relationen",
    "entity.relations.empty": "keine Relationen angelegt",
    "entity.center": "Zentrieren",

    "field.new": "Neues Feld",
    "field.fieldName": "Name",
    "field.fieldName.hint": "Groß und Kleinschreibung mit Zahlen erlaubt. Erstes Zeichen muss ein kleiner Buchstabe sein.",
    "field.fieldType": "Datentyp",
    "field.restrictions": "Restriktionen",
    "field.pattern": "Pattern",
    "field.min": "Minimum",
    "field.max": "Maximum",
    "field.required": "Pflichtfeld.",

    "relationship.new": "Neue Relation",
    "relationship.relationshipType": "Relationstyp",
    "relationship.sourceEntity": "Entität - Quelle der Relation",
    "relationship.sourceField": "Relationsname",
    "relationship.sourceField.hint": "Name des Feldes vom Typ {{entityName}}. Groß und Kleinschreibung mit Zahlen erlaubt. Erstes Zeichen muss ein kleiner Buchstabe sein.",
    "relationship.source": "von",
    "relationship.target": "zu",
    "relationship.targetEntity": "Entität - Ziel der Relation",
    "relationship.targetField": "Relationsname",
    "relationship.targetField.hint": "Name des Feldes vom Typ {{entityName}}. Groß und Kleinschreibung mit Zahlen erlaubt. Erstes Zeichen muss ein kleiner Buchstabe sein.",
    "relationship.targetEntity.empty": "nicht verfügbar",

    "appTemplate.delete.title": "Template löschen",
    "appTemplate.delete.message": "Wollen Sie das Template wirklich löschen?",
    "appTemplate.error.title": "Fehler",
    "appTemplate.error.message": "Das Template konnte nicht gelöscht werden.",
    "appTemplate.success.title": "Template Aktualisierung erfolgreich",
    "appTemplate.success.message": "Die Templates wurden erfolgreich aus dem Git-Repository geladen.",

    "apptemplate.new": "Neues Template",
    "apptemplate.edit": "{{appTemplateName}} bearbeiten",
    "apptemplate.loading": "Das Template wird geladen",
    "apptemplate.location": "Git-Repository",
    "apptemplate.location.hint": "Bitte geben Sie eine Git-Repository-URL ein",
    "appTemplate.branch": "Branch",
    "appTemplate.branch.hint": "Der Branch Name kann 100 Zeichen lang sein und kann Buchstaben, Zahlen und die Zeichen /_- enthalten.",
    "appTemplate.config": "Template Konfiguration",
    "appTemplate.credentialsRequired": "privates Repo mit Login",
    "appTemplate.description": "Beschreibung",

    "appConclusion.generationError.title": "Fehler beim generieren.",
    "appConclusion.generationError.message": "Beim generieren der App ist folgender Fehler aufgetreten:",
    "appConclusion.generationError.snackbar": "Es sind Template Fehler aufgetreten.",

    "gitAuth.title": "Login Git Template Repository",
    "gitAuth.username": "Nutzername",
    "gitAuth.username.hint": "Nutzer darf nicht leer sein und kann Buchstaben, Ziffern und ./_- enthalten",
    "gitAuth.password": "Passwort",
    "gitAuth.password.hint": "Passwort / PAT darf nicht leer sein und kann Buchstaben, Ziffern und Sonderzeichen enthalten",

    "error.git.directorynotexists": "Ein interner Fehler ist aufgetreten. Bitte wenden Sie sich an den IT-Support.",
    "error.git.access.denied": "Sie benötigen Rechte zum Ausführen von git clone. In Github verwenden Sie das Recht 'repo'.",
    "error.git.access.tokenusagerequired": "Der Support für Nutzer/Passwort Authetifizierung ist ausgelaufen. Sie müssen einen Personal Access Token verwenden.",
    "error.git.access.invaliduserpassword": "Nutzer oder Passwort sind ungültig. Es sind nur Buchstaben, Zahlen und !@#$%^&()*./_- erlaub.",
    "error.git.exit": "Fehler beim Abrufen der Daten vom Git-Repository.",
    "error.appcheckout.giturlisinvalid": "Ungültige Git-Repository URL. Es sind nur Buchstaben, Zahlen und ./_- erlaubt.",
    "error.appcheckout.usernameisinvalid": "Nutzer oder Passwort sind ungültig. Es sind nur Buchstaben, Zahlen und !@#$%^&()*./_- erlaub.",
    "error.appcheckout.passworisinvalid": "Nutzer oder Passwort sind ungültig. Es sind nur Buchstaben, Zahlen und !@#$%^&()*./_- erlaub.",
    "error.appcheckout.branchisinvalid": "Der Branchname is ungültig. Er darf nur Buchstaben, Zahlen und /_- enthalten.",
    "error.appcheckout.jsonmapping.git": "Die Templatekonfiguration konnte nicht heruntergeladen werden. Bitte überprüfen Sie den Namen der Konfigurationsdatei. Sie muss template-config.json heißen.",
    "error.git.access.repositorynotfound": "Das angegebene Git-Repository konnte nicht gefunden werden. Bitte überpüfen Sie Repository-URL und Branch-Namen.",
    "error.accessdenied": "Sie sind für diese Aktion nicht berechtigt.",
    "error.general.delete": "Dieses Element konnte nicht gelöscht werden.",
    "error.general.create": "Dieses Element konnte nicht erstellt werden.",
    "error.general.update": "Dieses Element konnte nicht aktualisiert werden.",
    "error.general.get": "Dieses Element konnte nicht geladen werden.",
    "error.serverOffline": "Der Server scheint offline zu sein.",
    "error.userOffline": "Sie scheinen offline zu sein.",
    "error.unknown": "Ein unbekannter Fehler ist aufgetreten.",
    "error.apptemplate.notfound": "Das Template konnte nicht gefunden werden.",
    "error.app.notfound": "Die App konnte nicht gefunden werden.",
    "error.entity.notfound": "Das Element konnte nicht gefunden werden.",
    "error.wrongAppId": "Die App ID ist falsch.",
    "error.internalServerError": "Ein Server Fehler ist aufgetreten.",
    "error.invalidDefinition": "Die angegebene Definition ist nicht gültig.",
    "error.unauthorized": "Sie haben keine Berechtigung.",
    "error.wrongInputValue": "Die angegebenen Daten sind falsch.",
    "error.badrequest": "Die Anfrage ist fehlerhaft.",
    "error.notfound": "Das Element wurde nicht gefunden.",
    "error.notexists": "Das Element existiert nicht.",
    "error.generation.template": "Das Projekt konnte nicht generiert werden.",
    "error.apptemplate.delete.appexists": "Das Template wird noch in einer App verwendet.",
    "error.optimisticLock": "Das Objekt oder deren Abhängigkeiten wurden versucht gleichzeitig zu speichern. Bitte speichern sie erneut."
};
export default translationsDeDE;
