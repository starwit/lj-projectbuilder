import React, {useEffect, useState} from "react";
import {
    Box,
    Button,
    Container,
    Dialog,
    DialogActions,
    DialogTitle,
    IconButton,
    Stack,
    Tab,
    Tabs,
    Typography
} from "@mui/material";
import {Add, Close} from "@mui/icons-material";
import LoadingSpinner from "../../../../commons/loadingSpinner/LoadingSpinner";
import FieldAccordion from "../fieldAccordion/FieldAccordion";
import RelationshipAccordion from "../relationshipAccordion/RelationshipAccordion";
import EntityDialogStyles from "./EntityDialogStyles";
import TabPanel from "../../../../commons/tabPanel/TabPanel";
import Statement from "../../../../commons/statement/Statement";
import {useTranslation} from "react-i18next";
import ValidatedTextField from "../../../../commons/validatedTextField/ValidatedTextField";
import RegexConfig from "../../../../../regexConfig";
import {defaultRelationship} from "../Relationship";
import {LoadingButton} from "@mui/lab";
import {emptyEntity, newEntity} from "../DefaultEntities";
import FieldTypes from "./FieldTypes";

function EntityDialog(props) {
    const {entityId, onClose, handleSave, entities, open} = props;
    const [value, setValue] = useState(0);
    const [entity, setEntity] = useState(null);
    const [hasFormError, setHasFormError] = useState(false);
    const [isSaving, setIsSaving] = useState(false);
    const entityEditorStyles = EntityDialogStyles();
    const {t} = useTranslation();

    useEffect(() => {
        if (entityId) {
            const existingEntity = {...entities.find(entity_ => entity_.id === entityId)};
            existingEntity.fields?.forEach(field => {
                field.mandatory = field.fieldValidateRules?.includes("required");
            });
            setEntity(existingEntity);
        } else {
            const newEntity1 = JSON.parse(JSON.stringify(newEntity));
            setEntity(newEntity1);
        }
    }, [entityId, entities]);

    useEffect(() => {
        if (!entity) {
            return;
        }
        let hasError = false;

        if (!RegexConfig.entityTitle.test(entity.name)) {
            hasError = true;
        }

        entity.fields?.forEach(field => {
            if (!RegexConfig.fieldName.test(field.fieldName)) {
                hasError = true;
            }
        });

        entity.relationships?.forEach(relationship => {
            if (
                !RegexConfig.relationship.test(relationship.relationshipName) ||
                !RegexConfig.relationship.test(relationship.otherEntityRelationshipName) ||
                !RegexConfig.entityTitle.test(relationship.otherEntityName)
            ) {
                hasError = true;
            }
        });
        setHasFormError(hasError);
    }, [entity]);

    function getTargetEntities() {
        let newTargetEntities = [];
        if (entities?.length >= 1) {
            newTargetEntities = entities.filter(e => e.name !== entity.name);
        } else {
            const emptyTarget = emptyEntity;
            emptyTarget.name = t("relationship.targetEntity.empty");
            newTargetEntities.push(emptyTarget);
        }
        return newTargetEntities;
    }

    function lowerFirstChar(s) {
        return (s && s[0].toLowerCase() + s.slice(1)) || "";
    }

    function a11yProps(index) {
        return {
            "id": `simple-tab-${index}`,
            "aria-controls": `simple-tabpanel-${index}`
        };
    }

    function deepCopyEntity() {
        return JSON.parse(JSON.stringify(entity));
    }

    function prepareSave() {
        setIsSaving(true);

        const entityModified = {...entity};

        entityModified.fields?.forEach(field => {
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
        });

        handleSave(entityModified)
            .then(() => {
                onClose();
                setIsSaving(false);
            })
            .catch(() => {
                setIsSaving(false);
            });
    }

    function addField() {
        const copiedEntity = deepCopyEntity();
        if (!copiedEntity.fields) {
            copiedEntity.fields = [];
        }
        copiedEntity.fields.push({
            fieldName: "",
            fieldType: "String",
            fieldValidateRulesPattern: "",
            fieldValidateRulesMin: "",
            fieldValidateRulesMinlength: "",
            fieldValidateRulesMax: "",
            fieldValidateRulesMaxlength: "",
            mandatory: false
        });
        setEntity(copiedEntity);
    }

    function addRelationship() {
        const copiedEntity = deepCopyEntity();

        if (!copiedEntity.relationships) {
            copiedEntity.relationships = [];
        }

        const targetEntities = getTargetEntities();
        const relationship = {...defaultRelationship};
        relationship.otherEntityName = targetEntities[0].name;
        relationship.relationshipName = lowerFirstChar(targetEntities[0].name);
        relationship.otherEntityRelationshipName = lowerFirstChar(copiedEntity.name);

        copiedEntity.relationships.push(relationship);
        setEntity(copiedEntity);
    }

    function deleteRelationship(index) {
        const copiedEntity = deepCopyEntity();
        copiedEntity.relationships.splice(index, 1);
        setEntity(copiedEntity);
    }

    function deleteField(index) {
        const copiedEntity = deepCopyEntity();
        copiedEntity.fields.splice(index, 1);
        setEntity(copiedEntity);
    }

    function handleTabChange(event, newValue) {
        setValue(newValue);
    }

    function handleEntityTitleText(event) {
        const copiedEntity = {...entity};
        copiedEntity.name = event.target.value;
        setEntity(copiedEntity);
    }

    function editFieldProperty(key, value, index) {
        const copiedEntity = deepCopyEntity();
        copiedEntity.fields[index][key] = value;
        setEntity(copiedEntity);
    }

    function editRelationshipProperty(key, value, index) {
        const copiedEntity = deepCopyEntity();
        copiedEntity.relationships[index][key] = value;
        if (key === "otherEntityName") {
            copiedEntity.relationships[index].relationshipName = lowerFirstChar(value);
        }
        setEntity(copiedEntity);
    }

    function renderRelations() {
        if (!entity.relationships || entity.relationships.length <= 0) {
            return (
                <Statement message={t("entity.relations.empty")} enableSpacing/>
            );
        }
        return entity.relationships.map((relationship, index) => {
            return (
                <RelationshipAccordion
                    key={index}
                    relationship={relationship}
                    targetEntities={getTargetEntities()}
                    editRelationshipProperty={(key, value) => editRelationshipProperty(key, value, index)}
                    currentEntity={entity}
                    handleDelete={() => deleteRelationship(index)}
                />
            );
        });
    }

    function renderFields() {
        if (!entity.fields || entity.fields.length <= 0) {
            return (
                <Statement message={t("entity.fields.empty")} enableSpacing/>
            );
        }

        return entity.fields.map((field, index) => {
            const {
                mandatory,
                fieldValidateRulesMin,
                fieldValidateRulesMinlength,
                fieldValidateRulesMax,
                fieldValidateRulesMaxlength,
                fieldValidateRulesPattern,
                fieldName,
                fieldType
            } =
                entity.fields[index];
            return (
                <FieldAccordion
                    editFieldProperty={(key, value) => editFieldProperty(key, value, index)}
                    fieldTypes={FieldTypes}
                    fieldType={fieldType}
                    pattern={fieldValidateRulesPattern}
                    min={fieldValidateRulesMin}
                    max={fieldValidateRulesMax}
                    minLength={fieldValidateRulesMinlength}
                    maxLength={fieldValidateRulesMaxlength}
                    mandatory={mandatory}
                    name={fieldName}
                    isCreate={!fieldName}
                    key={index}
                    handleDelete={() => deleteField(index)}
                />
            );
        });
    }

    if (!entity) {
        return <LoadingSpinner message={t("entity.loading")}/>;
    }

    return (
        <Dialog open={!!entityId || (open && entity.isNewEntity)} maxWidth={"xl"} fullWidth>
            <DialogTitle className={entityEditorStyles.dialogHeaderBar}>
                <Typography noWrap variant={"h6"} component={"p"}>
                    {t("entity.edit", {entityName: entity.name})}
                </Typography>
                <div className={entityEditorStyles.flex}/>
                <IconButton aria-label="close" onClick={onClose}>
                    <Close/>
                </IconButton>
            </DialogTitle>
            <Container>
                <Stack spacing={2}>
                    <ValidatedTextField
                        isCreate={entity.isNewEntity}
                        fullWidth
                        label={t("entity.name") + "*"}
                        value={entity.name}
                        onChange={handleEntityTitleText}
                        helperText={t("entity.name.hint")}
                        regex={RegexConfig.entityTitle}
                    />
                    <Box className={entityEditorStyles.tabBox}>
                        <Tabs value={value} onChange={handleTabChange} aria-label="basic tabs example"
                            className={entityEditorStyles.tabHeader}>
                            <Tab label={t("entity.fields")} {...a11yProps(0)} />
                            <Tab label={t("entity.relations")} {...a11yProps(1)} />
                        </Tabs>
                        <TabPanel value={value} index={0}>
                            <Stack spacing={1}>
                                {renderFields()}
                                <Button fullWidth startIcon={<Add/>} onClick={addField}>
                                    {t("button.create")}
                                </Button>
                            </Stack>
                        </TabPanel>
                        <TabPanel value={value} index={1}>
                            <Stack spacing={1}>
                                {renderRelations()}
                                <Button fullWidth startIcon={<Add/>} onClick={addRelationship}>
                                    {t("button.create")}
                                </Button>
                            </Stack>
                        </TabPanel>
                    </Box>
                    <DialogActions>
                        <LoadingButton onClick={prepareSave} disabled={hasFormError} loading={isSaving}>
                            {t("button.save")}
                        </LoadingButton>
                    </DialogActions>
                </Stack>
            </Container>
        </Dialog>
    );
}

export default EntityDialog;
