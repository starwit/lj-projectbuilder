import React, {useEffect} from "react";
import {
    Accordion,
    AccordionDetails,
    AccordionSummary,
    Box, Button, Checkbox, Container,
    Dialog, DialogActions,
    DialogTitle, Divider, FormControl, FormControlLabel, Grid,
    IconButton, InputLabel, MenuItem, Select,
    Tab,
    Tabs, TextField,
    Typography
} from "@mui/material";
import {Add, Close, ExpandMore} from "@mui/icons-material";
import LoadingSpinner from "../../../../../commons/loadingSpinner/LoadingSpinner";
import FieldAccordion from "../../../../../commons/fieldAccordion/FieldAccordion";
import RelationshipAccordion from "../../../../../commons/relationshipAccordion/RelationshipAccordion";
import PropTypes from "prop-types";


function TabPanel(props) {
    const {children, value, index, ...other} = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{p: 3}}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

function EntityEditor(props) {

    const [value, setValue] = React.useState(0);
    const [entity, setEntity] = React.useState(null);
    const [selectedAttribute, setSelectedAttribute] = React.useState(null);

    const {onSave, entityId, onClose, handleSave, entities} = props;

    useEffect(() => {

        setEntity({
            "name": "B",
            description: "",
            "fields": [
                {
                    name: "",
                    dataType: {
                        id: 2,
                        name: "integer",
                        allowMin: true,
                        allowMax: true
                    },
                    description: "",
                    pattern: "",
                    min: "",
                    max: "",
                    mandatory: false
                }
            ],
            "relationships": [
                {
                    "relationshipType": "many-to-one",
                    "otherEntityName": "a",
                    "otherEntityRelationshipName": "b",
                    "relationshipName": "a"
                }
            ]
        },)

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
        setSelectedAttribute(entity.fields.length - 1);

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
        console.log(newEntity);
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
        return entity.relationships.map((relationship, index) => {
            const {otherEntityRelationshipName, otherEntityName, relationshipName} = relationship;
            return <RelationshipAccordion
                otherEntityRelationshipName={otherEntityRelationshipName}
                otherEntityName={otherEntityName}
                relationshipName={relationshipName}
                entities={entities}
                editRelationshipProperty={(key, value) => editRelationshipProperty(key, value, index)}
                currentEntity={entity}
                relationshipTypes={relationshipTypes}
            />
        })
    }

    function renderFields() {
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
            <DialogTitle sx={{m: 0, p: 2}}>
                Entität bearbeiten
                <IconButton
                    aria-label="close"
                    onClick={onClose}
                    sx={{
                        position: 'absolute',
                        right: 8,
                        top: 8,
                        color: (theme) => theme.palette.grey[500],
                    }}
                >
                    <Close/>
                </IconButton>
            </DialogTitle>
            <Container>
                <TextField fullWidth label={"Name"} value={entity.name} onChange={handleEntityTitleText}/>
                <Box sx={{width: '100%'}}>
                    <Box sx={{borderBottom: 1, borderColor: 'divider'}}>
                        <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
                            <Tab label="Felder" {...a11yProps(0)} />
                            <Tab label="Relationen" {...a11yProps(1)} />
                        </Tabs>
                    </Box>
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