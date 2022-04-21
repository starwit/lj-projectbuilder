import {produce} from "immer";
import RegexConfig from "../../regexConfig";

const newApp =
    {id: null, template: null, entities: [],
        general: {isNew: true, isValid: false, appName: "", packageName: "", assignedGroups: []}};

function toDatabaseApp(app) {
    const data = {};
    data.id = app.id;
    data.baseName = app.general.appName;
    data.packageName = app.general.packageName;
    data.template = app.template;
    data.entities = app.entities;
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

function updateGeneral(app, event) {
    if (event?.target?.name === "appName" || event?.target?.name === "packageName") {
        return produce(app, draft => {
            draft.general[event.target.name] = event.target.value;
            draft.general.isValid = isValid(draft.general);
        });
    } else {
        return produce(app, draft => {draft.general["groups"] = items;});
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
    toDatabaseApp};
