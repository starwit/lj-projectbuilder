import {produce} from "immer";
import RegexConfig from "../../regexConfig";

const newApp =
    {id: null, template: null, entities: [], ennums: [],
        general: {isNew: true, isValid: false, appName: "", packageName: "", assignedGroups: ["public"]}};

function toDatabaseApp(app) {
    const data = {};
    data.id = app.id;
    data.baseName = app.general.appName;
    data.packageName = app.general.packageName;
    data.template = app.template;
    data.entities = app.entities;
    data.enums = app.enums;
    data.groupsToAssign = app.general.assignedGroups;
    return data;
}

function updateApp(app, data) {
    return produce(app, draft => {
        draft.id = data.id;
        draft.general = {appName: "", packageName: "", assignedGroups: []};
        draft.general.appName = data.baseName;
        draft.general.packageName = data.packageName;
        draft.general.assignedGroups = data.groupsToAssign;
        draft.general.isNew = !data.id;
        draft.general.isValid = isValid(draft.general);
        draft.template = data.template;
        draft.entities = data.entities;
        draft.enums = data.enums;
    });
}

function updateTemplate(app, template) {
    return produce(app, draft => {
        draft.template = template;
    });
}

function updateEntities(app, entities) {
    return produce(app, draft => {
        draft.entities = entities;
    });
}

function updateEnums(app, enums) {
    return produce(app, draft => {
        draft.enums = enums;
    });
}

function updateGeneral(app, event) {
    if (event?.target?.name === "appName" || event?.target?.name === "packageName") {
        return produce(app, draft => {
            draft.general[event.target.name] = event.target.value;
            draft.general.isValid = isValid(draft.general);
        });
    } else if (event?.target?.name === "assignedGroups") {
        return produce(app, draft => {draft.general["assignedGroups"] = event.target.value;});
    }
}

function isValid(general) {
    return RegexConfig.applicationBaseName.test(general.appName) ||
            RegexConfig.packageName.test(general.packageName);
}

export {
    newApp,
    updateApp,
    updateTemplate,
    updateGeneral,
    updateEntities,
    updateEnums,
    toDatabaseApp};
