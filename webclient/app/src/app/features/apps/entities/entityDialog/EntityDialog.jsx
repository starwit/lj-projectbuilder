import React, {useEffect, useMemo, useState} from "react";
import {
    Box,
    Button,
    Container,
    Dialog,
    DialogActions,
    DialogTitle,
    IconButton,
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
import {emptyEntity, newEntity} from "../Entity";
import EntityRest from "../../../../services/EntityRest";
import DataTypes from "../../../../config/DataTypes";


function EntityDialog(props) {
    const {entityId, onClose, handleSave, entities, appId, handleUpdateEntities, open} = props;
    const [value, setValue] = useState(0);
    const [entity, setEntity] = useState(null);
    const [hasFormError, setHasFormError] = useState(false);
    const [isSaving, setIsSaving] = useState(false);
    const entityEditorStyles = EntityDialogStyles();
    const {t} = useTranslation();
    const entityRest = useMemo(() => new EntityRest(), []);

    useEffect(() => {
        if (entityId) {
            setEntity({...entities.find(entity_ => entity_.id === entityId)});
        } else {
            setEntity({...newEntity})
        }
    }, [entityId, entities])

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
            if (!RegexConfig.relationship.test(relationship.relationshipName)
                || !RegexConfig.relationship.test(relationship.otherEntityRelationshipName)
                || !RegexConfig.entityTitle.test(relationship.otherEntityName)) {
                hasError = true;
            }
        });
        setHasFormError(hasError);


    }, [entity])


    function getTargetEntities() {
        let newTargetEntities = [];
        if (entities?.length > 1) {
            newTargetEntities = entities.filter(e => e.name !== entity.name);
        } else {
            let emptyTarget = emptyEntity;
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
            id: `simple-tab-${index}`,
            'aria-controls': `simple-tabpanel-${index}`,
        };
    }

    function prepareSave() {

        setIsSaving(true);
        handleSave(entity)
            .then(() => {
                entityRest.findAllEntitiesByApp(appId)
                    .then((response) => {
                        handleUpdateEntities(response.data);
                        onClose();
                        setIsSaving(false);
                    })
            })
            .catch(() => {
                setIsSaving(false);
            });
    }

    function addField() {

        let newEntity = {...entity};
        if (!newEntity.fields) {
            newEntity.fields = [];
        }
        newEntity.fields.push(
            {
                fieldName: "",
                fieldType: "",
                fieldValidateRulesPattern: "",
                fieldValidateRulesMin: "",
                fieldValidateRulesMax: "",
                mandatory: false
            }
        );
        setEntity(newEntity);
    }

    function addRelationship() {

        let newEntity = {...entity};

        if (!newEntity.relationships) {
            newEntity.relationships = [];
        }

        let targetEntities = getTargetEntities();
        let relationship = {...defaultRelationship};
        relationship.otherEntityName = targetEntities[0].name;
        relationship.relationshipName = lowerFirstChar(targetEntities[0].name);
        relationship.otherEntityRelationshipName = lowerFirstChar(newEntity.name);

        newEntity.relationships.push(relationship);
        setEntity(newEntity);
    }

    function deleteRelationship(index) {
        let newEntity = {...entity};
        newEntity.relationships.splice(index, 1);
        setEntity(newEntity);
    }

    const handleTabChange = (event, newValue) => {
        setValue(newValue);
    };

    function handleEntityTitleText(event) {
        const newEntity = {...entity};
        newEntity.name = event.target.value;
        setEntity(newEntity);
    }

    function editFieldProperty(key, value, index) {
        const newEntity = {...entity};
        if (key === "dataType") {
            value = DataTypes.find(dataType => dataType.id === value);
        }
        newEntity.fields[index][key] = value;
        setEntity(newEntity)
    }

    function editRelationshipProperty(key, value, index) {
        const newEntity = {...entity};
        newEntity.relationships[index][key] = value;
        if (key === "otherEntityName") {
            newEntity.relationships[index].relationshipName = lowerFirstChar(value);
        }
        setEntity(newEntity)
    }


    function renderRelations() {
        if (!entity.relationships || entity.relationships.length <= 0) {
            return (
                <div className={entityEditorStyles.statementWrapper}>
                    <Statement message={t("entity.relations.empty")}/>
                </div>
            )
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
            )
        })
    }

    function renderFields() {
        if (!entity.fields || entity.fields.length <= 0) {
            return (
                <div className={entityEditorStyles.statementWrapper}>
                    <Statement message={t("entity.fields.empty")}/>
                </div>
            )
        }

        return entity.fields.map((field, index) => {
            const {
                mandatory,
                fieldValidateRulesMin,
                fieldValidateRulesMax,
                fieldValidateRulesPattern,
                fieldName,
                fieldType
            } = entity.fields[index];
            return (
                <FieldAccordion
                    editFieldProperty={(key, value) => editFieldProperty(key, value, index)}
                    dataTypes={DataTypes}
                    dataType={fieldType}
                    pattern={fieldValidateRulesPattern}
                    min={fieldValidateRulesMin}
                    max={fieldValidateRulesMax}
                    mandatory={mandatory}
                    name={fieldName}
                    isCreate={!fieldName}
                />
            )
        })
    }

    if (!entity) {
        return <LoadingSpinner message={t("entity.loading")}/>
    }

    return (
        <Dialog open={!!entityId || (open && entity.isNewEntity)} maxWidth={"xl"} fullWidth>
            <DialogTitle className={entityEditorStyles.dialogHeaderBar}>
                <Typography
                    noWrap
                    variant={"h6"}
                    component={"p"}
                >
                    {t("entity.edit", {entityName: entity.name})}
                </Typography>
                <div className={entityEditorStyles.flex}/>
                <IconButton
                    aria-label="close"
                    onClick={onClose}
                >
                    <Close/>
                </IconButton>
            </DialogTitle>
            <Container>
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
                    <Tabs value={value} onChange={handleTabChange} aria-label="basic tabs example">
                        <Tab label={t("entity.fields")} {...a11yProps(0)} />
                        <Tab label={t("entity.relations")} {...a11yProps(1)} />
                    </Tabs>
                    <TabPanel value={value} index={0}>
                        {renderFields()}
                        <Button fullWidth startIcon={<Add/>} onClick={addField}>{t("button.create")}</Button>
                    </TabPanel>
                    <TabPanel value={value} index={1}>
                        {renderRelations()}
                        <Button fullWidth startIcon={<Add/>} onClick={addRelationship}>{t("button.create")}</Button>
                    </TabPanel>
                </Box>
                <DialogActions>
                    <LoadingButton
                        onClick={prepareSave}
                        disabled={hasFormError}
                        loading={isSaving}
                    >
                        {t("button.save")}
                    </LoadingButton>
                </DialogActions>
            </Container>
        </Dialog>
    )
}

export default EntityDialog;
