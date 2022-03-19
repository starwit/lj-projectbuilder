import RegexConfig from "../../../regexConfig";

function hasEntityFieldValidationError(entity) {
    let hasError = false;
    if (!entity) {
        return;
    }

    if (!RegexConfig.entityTitle.test(entity.name)) {
        hasError = true;
    }

    entity.fields?.forEach(field => {
        if (!RegexConfig.fieldName.test(field.fieldName)) {
            hasError = true;
        }

    });

    entity.relationships?.forEach(relationship => {
        if (!RegexConfig.relationship.test(relationship.relationshipName)
            || !RegexConfig.relationship.test(relationship.otherEntityRelationshipName)
            || !RegexConfig.entityTitle.test(relationship.otherEntityName)) {
            hasError = true;
        }
    });

    return hasError
}

export default hasEntityFieldValidationError;