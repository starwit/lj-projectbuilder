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
    TextField,
    Typography
} from "@mui/material";
import {Add, CheckBoxOutlineBlank, Close} from "@mui/icons-material";
import LoadingSpinner from "../../../../../commons/loadingSpinner/LoadingSpinner";
import FieldAccordion from "../../../../../commons/fieldAccordion/FieldAccordion";
import RelationshipAccordion from "../../../../../commons/relationshipAccordion/RelationshipAccordion";
import EntityEditorStyles from "./EntityEditorStyles";
import TabPanel from "../../../../../commons/tabPanel/TabPanel";
import Statement from "../../../../../commons/statement/Statement";

function EntityEditor(props) {

    const [value, setValue] = React.useState(0);
    const [entity, setEntity] = React.useState(null);
    const entityEditorStyles = EntityEditorStyles();

    const {entityId, onClose, handleSave, entities} = props;

    useEffect(() => {

        setEntity({...entities.find(entity => entity.id === entityId)})

    }, [entityId])

    const dataTypes = [
        {
            id: 1,
            name: "string"
        },
        {
            id: 2,
            name: "integer",
            allowMin: true,
            allowMax: true
        },
        {
            id: 3,
            name: "double",
            allowMin: true,
            allowMax: true
        },
        {
            id: 4,
            name: "date"
        },
        {
            id: 5,
            name: "time"
        },
        {
            id: 6,
            name: "timestamp"
        },
        {
            id: 7,
            name: "bigDecimal",
            allowMin: true,
            allowMax: true
        },
        {
            id: 8,
            name: "enum"
        }
    ];

    const relationshipTypes = [
        "one-to-one",
        "one-to-many",
        "many-to-many"
    ];

    function a11yProps(index) {
        return {
            id: `simple-tab-${index}`,
            'aria-controls': `simple-tabpanel-${index}`,
        };
    }

    function addField() {

        let newEntity = {...entity};
        // TODO Maybe add an ID to entity
        newEntity.fields.push(
            {
                name: "",
                description: "",
                dataType: "",
                pattern: "",
                min: "",
                max: "",
                mandatory: false
            }
        );
        setEntity(newEntity);
    }

    function addRelationship() {

        let newEntity = {...entity};
        newEntity.relationships.push(
            {
                "relationshipType": "",
                "otherEntityName": "",
                "otherEntityRelationshipName": "",
                "relationshipName": ""
            }
        )
        setEntity(newEntity);
    }

    const handleChange = (event, newValue) => {
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
        setEntity(newEntity)
    }


    function renderRelations() {
        if (!entity.relationships || entity.relationships.length <= 0) {
            return (
                <div className={entityEditorStyles.statementWrapper}>
                    <Statement icon={<CheckBoxOutlineBlank/>} message={"Keine Relationen angelegt"}/>
                </div>
            )
        }
        return entity.relationships.map((relationship, index) => {
            const {otherEntityRelationshipName, otherEntityName, relationshipName} = relationship;
            return (
                <RelationshipAccordion
                    otherEntityRelationshipName={otherEntityRelationshipName}
                    otherEntityName={otherEntityName}
                    relationshipName={relationshipName}
                    entities={entities}
                    editRelationshipProperty={(key, value) => editRelationshipProperty(key, value, index)}
                    currentEntity={entity}
                    relationshipTypes={relationshipTypes}
                />
            )
        })
    }

    function renderFields() {
        if (!entity.fields || entity.fields.length <= 0) {
            return (
                <div className={entityEditorStyles.statementWrapper}>
                    <Statement icon={<CheckBoxOutlineBlank/>} message={"Keine Felder angelegt"}/>
                </div>
            )
        }

        return entity.fields.map((field, index) => {
            const {dataType, mandatory, min, max, pattern, description, name} = entity.fields[index];
            return (
                <FieldAccordion
                    editFieldProperty={(key, value) => editFieldProperty(key, value, index)}
                    dataTypes={dataTypes}
                    dataType={dataType}
                    description={description}
                    pattern={pattern}
                    min={min}
                    max={max}
                    mandatory={mandatory}
                    name={name}
                />
            )
        })

    }

    if (!entity) {
        return <LoadingSpinner message={"Domain wird geladen"}/>
    }

    return (
        <Dialog open={entityId} maxWidth={"xl"} fullWidth>
            <DialogTitle className={entityEditorStyles.dialogHeaderBar}>
                <Typography noWrap variant={"h6"} component={"p"}>Entität bearbeiten</Typography>
                <div style={{flex: 1}}/>
                <IconButton
                    aria-label="close"
                    onClick={onClose}
                >
                    <Close/>
                </IconButton>
            </DialogTitle>
            <Container>
                <TextField fullWidth label={"Name"} value={entity.name} onChange={handleEntityTitleText}/>
                <Box className={entityEditorStyles.tabBox}>
                    <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
                        <Tab label="Felder" {...a11yProps(0)} />
                        <Tab label="Relationen" {...a11yProps(1)} />
                    </Tabs>
                    <TabPanel value={value} index={0}>
                        {renderFields()}
                        <Button fullWidth startIcon={<Add/>} onClick={addField}>Hinzufügen</Button>
                    </TabPanel>
                    <TabPanel value={value} index={1}>
                        {renderRelations()}
                        <Button fullWidth startIcon={<Add/>} onClick={addRelationship}>Hinzufügen</Button>
                    </TabPanel>
                </Box>
                <DialogActions>
                    <Button onClick={() => handleSave(entity)}>Speichern</Button>
                </DialogActions>
            </Container>
        </Dialog>
    )

}

export default EntityEditor;