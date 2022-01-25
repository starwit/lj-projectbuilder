import React, {useEffect} from "react";
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
import {Add, CheckBoxOutlineBlank, Close} from "@mui/icons-material";
import LoadingSpinner from "../loadingSpinner/LoadingSpinner";
import FieldAccordion from "../fieldAccordion/FieldAccordion";
import RelationshipAccordion from "../relationshipAccordion/RelationshipAccordion";
import EntityDialogStyles from "./EntityDialogStyles";
import TabPanel from "../tabPanel/TabPanel";
import Statement from "../statement/Statement";
import {useTranslation} from "react-i18next";
import ValidatedTextField from "../validatedTextField/ValidatedTextField";
import RegexConfig from "../../../regexConfig";
import { defaultRelationship } from "../relationshipAccordion/Relationship";
import {LoadingButton} from "@mui/lab";
import { nullEntity } from "../entityCard/Entity";

function EntityDialog(props) {

    const [value, setValue] = React.useState(0);
    const [entity, setEntity] = React.useState(null);
    const [hasFormError, setHasFormError] = React.useState(false);
    const [isSaving, setIsSaving] = React.useState(false);
    const entityEditorStyles = EntityDialogStyles();
    const {t} = useTranslation();
    const {entityId, onClose, handleSave, entities} = props;
    
    useEffect(() => {
        setEntity({...entities.find(entity => entity.id === entityId)})
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

        })
        setHasFormError(hasError);


    }, [entity])


    function getTargetEntities() {
        let newTargetEntities = [];
        if (entities && entities.length > 1) {
            newTargetEntities = entities.filter(e => e.name !== entity.name);
        } else {
            let emptyEntity = nullEntity;
            emptyEntity.name = t("relationship.targetEntity.empty");
            newTargetEntities.push(emptyEntity)
        }
        return newTargetEntities;

    };

    const dataTypes = [
        {
            id: 1,
            name: "String"
        },
        {
            id: 2,
            name: "Integer",
            allowMin: true,
            allowMax: true
        },
        {
            id: 3,
            name: "Double",
            allowMin: true,
            allowMax: true
        },
        {
            id: 4,
            name: "Date"
        },
        {
            id: 5,
            name: "Time"
        },
        {
            id: 6,
            name: "Timestamp"
        },
        {
            id: 7,
            name: "BigDecimal",
            allowMin: true,
            allowMax: true
        },
        {
            id: 8,
            name: "Enum"
        }
    ];

    function lowerFirstChar(s:string) {
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
                onClose()
                setIsSaving(false);
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
        // TODO Maybe add an ID to entity
        newEntity.fields.push(
            {
                fieldName: "",
                description: "",
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
        let relationship = defaultRelationship;
        relationship.otherEntityName = targetEntities[0].name;
        relationship.relationshipName = lowerFirstChar(targetEntities[0].name);
        relationship.otherEntityRelationshipName = lowerFirstChar(newEntity.name);

        newEntity.relationships.push(relationship);
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
            value = dataTypes.find(dataType => dataType.id === value);
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
                    <Statement icon={<CheckBoxOutlineBlank/>} message={t("entityDialog.noRelations")}/>
                </div>
            )
        }
        return entity.relationships.map((relationship, index) => {
            return (
                <RelationshipAccordion
                    relationship = {relationship}
                    targetEntities={getTargetEntities()}
                    editRelationshipProperty={(key, value) => editRelationshipProperty(key, value, index)}
                    currentEntity={entity}
                />
            )
        })
    }

    function renderFields() {
        if (!entity.fields || entity.fields.length <= 0) {
            return (
                <div className={entityEditorStyles.statementWrapper}>
                    <Statement icon={<CheckBoxOutlineBlank/>} message={t("entityDialog.noFields")}/>
                </div>
            )
        }

        return entity.fields.map((field, index) => {
            const {
                mandatory,
                fieldValidateRulesMin,
                fieldValidateRulesMax,
                fieldValidateRulesPattern,
                description,
                fieldName,
                fieldType
            } = entity.fields[index];
            return (
                <FieldAccordion
                    editFieldProperty={(key, value) => editFieldProperty(key, value, index)}
                    dataTypes={dataTypes}
                    dataType={fieldType}
                    description={description}
                    pattern={fieldValidateRulesPattern}
                    min={fieldValidateRulesMin}
                    max={fieldValidateRulesMax}
                    mandatory={mandatory}
                    name={fieldName}
                />
            )
        })

    }

    if (!entity) {
        return <LoadingSpinner message={t("entityDialog.domainIsLoading")}/>
    }

    return (
        <Dialog open={!!entityId} maxWidth={"xl"} fullWidth>
            <DialogTitle className={entityEditorStyles.dialogHeaderBar}>
                <Typography noWrap variant={"h6"} >{t("entityDialog.editDomain")}</Typography>
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
                    fullWidth
                    label={t("entityDialog.name")}
                    value={entity.name}
                    onChange={handleEntityTitleText}
                    helperText={t("entityDialog.name.error")}
                    regex={RegexConfig.entityTitle}
                />
                <Box className={entityEditorStyles.tabBox}>
                    <Tabs value={value} onChange={handleTabChange} aria-label="basic tabs example">
                        <Tab label={t("entityDialog.tab.fields")} {...a11yProps(0)} />
                        <Tab label={t("entityDialog.tab.relations")} {...a11yProps(1)} />
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