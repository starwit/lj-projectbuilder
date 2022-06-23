import {produce} from "immer";
import {defaultRelationship} from "./Relationship";

const newEntity = {
    name: "",
    fields: [],
    relationships: [],
    position: {
        positionX: 0,
        positionY: 0
    }
};

const emptyEntity = {
    id: undefined,
    name: "none",
    fields: [],
    relationships: [],
    position: {
        positionX: 0,
        positionY: 0
    }
};

function lowerFirstChar(string) {
    return (string && string[0].toLowerCase() + string.slice(1)) || "";
}

function updatePosition(entity, draggableData) {
    const updatedEntity = {...entity};
    updatedEntity.position = {};
    updatedEntity.position.positionX = draggableData.x;
    updatedEntity.position.positionY = draggableData.y;
    return updatedEntity;
}

function addFieldToEntity(entity) {
    return produce(entity, draft => {
        if (!draft.fields) {
            draft.fields = [];
        }
        draft.fields.push({
            fieldName: "",
            fieldType: "String",
            fieldValidateRulesPattern: "",
            fieldValidateRulesMin: "",
            fieldValidateRulesMinlength: "",
            fieldValidateRulesMax: "",
            fieldValidateRulesMaxlength: "",
            mandatory: false
        });
    });
}

function addRelationshipToEntity(entity, targetEntities) {
    return produce(entity, draft => {
        if (!draft.relationships) {
            draft.relationships = [];
        }
        const relationship = {...defaultRelationship};
        relationship.otherEntityName = targetEntities[0].name;
        relationship.relationshipName = lowerFirstChar(targetEntities[0].name);
        relationship.otherEntityRelationshipName = lowerFirstChar(draft.name);

        draft.relationships.push(relationship);
    });
}

function toDatabaseEntity(entity) {
    return produce(entity, draft => {
        draft.fields?.forEach(field => {
            field.fieldValidateRules = [];

            if (field.fieldValidateRulesMinlength) {
                field.fieldValidateRules.push("minlength");
            }

            if (field.fieldValidateRulesMaxlength) {
                field.fieldValidateRules.push("maxlength");
            }

            if (field.mandatory) {
                field.fieldValidateRules.push("required");
            }

            if (field.fieldValidateRulesMin) {
                field.fieldValidateRules.push("min");
            }

            if (field.fieldValidateRulesMax) {
                field.fieldValidateRules.push("max");
            }

            if (field.fieldValidateRulesPattern) {
                field.fieldValidateRules.push("pattern");
            }

            if (field.fieldType !== "Enum") {
                field.enumDef = null;
            }
        });
    });
}

function initEntity(entity) {
    return produce(entity, draft => {
        draft.fields?.forEach(field => {
            field.mandatory = field.fieldValidateRules?.includes("required");
        });
    });
}

export {
    newEntity,
    emptyEntity,
    updatePosition,
    addFieldToEntity,
    addRelationshipToEntity,
    toDatabaseEntity,
    initEntity,
    lowerFirstChar
};
