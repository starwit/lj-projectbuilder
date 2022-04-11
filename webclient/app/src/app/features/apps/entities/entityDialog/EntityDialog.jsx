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
import {LoadingButton} from "@mui/lab";
import {
    emptyEntity,
    newEntity,
    addFieldToEntity,
    addRelationshipToEntity,
    toDatabaseEntity,
    initEntity
} from "../DefaultEntities";
import FieldTypes from "./FieldTypes";
import {useImmer} from "use-immer";

function EntityDialog(props) {
    const {entityId, onClose, handleSave, entities, open} = props;
    const [value, setValue] = useState(0);
    const [entity, setEntity] = useImmer({});
    const [hasFormError, setHasFormError] = useState(false);
    const [isSaving, setIsSaving] = useState(false);
    const entityEditorStyles = EntityDialogStyles();
    const {t} = useTranslation();

    useEffect(() => {
        let currEntity = {};
        if (entityId) {
            currEntity = entities.find(entity_ => entity_.id === entityId);
        } else {
            currEntity = newEntity;
        }
        setEntity(initEntity(currEntity));
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
        }
        if (newTargetEntities.length === 0) {
            const emptyTarget = emptyEntity;
            emptyTarget.name = t("relationship.targetEntity.empty");
            newTargetEntities.push(emptyTarget);
        }
        return newTargetEntities;
    }

    function a11yProps(index) {
        return {
            "id": `simple-tab-${index}`,
            "aria-controls": `simple-tabpanel-${index}`
        };
    }

    function prepareSave() {
        setIsSaving(true);
        handleSave(toDatabaseEntity(entity))
            .then(() => {
                onClose();
                setIsSaving(false);
            })
            .catch(() => {
                setIsSaving(false);
            });
    }

    function addRelationship() {
        setEntity(addRelationshipToEntity(entity, getTargetEntities()));
    }

    function addField() {
        setEntity(addFieldToEntity(entity));
    }

    function deleteRelationship(index) {
        setEntity(draft => {draft.relationships.splice(index, 1);});
    }

    function deleteField(index) {
        setEntity(draft => {draft.fields.splice(index, 1);});
    }

    function handleTabChange(event, newValue) {
        setValue(newValue);
    }

    function handleEntityTitleText(event) {
        setEntity(draft => {draft.name = event.target.value;});
    }

    function editFieldProperty(key, value, index) {
        setEntity(draft => {draft.fields[index][key] = value;});
    }

    function editRelationshipProperty(key, value, index) {
        setEntity(draft => {
            draft.relationships[index][key] = value;
            if (key === "otherEntityName") {
                draft.relationships[index].relationshipName = lowerFirstChar(value);
            }
        });
    }

    function renderRelations() {
        if (!entity.relationships || entity.relationships.length <= 0) {
            return (
                <div className={entityEditorStyles.statementWrapper}>
                    <Statement message={t("entity.relations.empty")}/>
                </div>
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
                <div className={entityEditorStyles.statementWrapper}>
                    <Statement message={t("entity.fields.empty")}/>
                </div>
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
        <Dialog open={!!entityId || (open && !entityId)} maxWidth={"xl"} fullWidth>
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
                <ValidatedTextField
                    isCreate={!entityId}
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
            </Container>
        </Dialog>
    );
}

export default EntityDialog;
