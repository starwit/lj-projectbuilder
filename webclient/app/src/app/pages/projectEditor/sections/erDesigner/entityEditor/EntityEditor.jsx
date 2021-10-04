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

    const {onSave, entityId, onClose, handleSave} = props;

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

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    function handleEntityTitleText(event) {
        const newEntity = {...entity};
        newEntity.name = event.target.value;
        setEntity(newEntity);
    }

    function handleFieldDataTypeChange(dataTypeId, index) {
        const newEntity = {...entity};
        const foundDataTypeObject = dataTypes.find(dataType => dataType.id === dataTypeId);
        newEntity.fields[index].dataType = foundDataTypeObject;
        setEntity(newEntity)
    }

    function editFieldProperty(key, value, index) {
        const newEntity = {...entity};
        newEntity.fields[index][key] = value;
        setEntity(newEntity)
    }

    function renderFields() {
        return entity.fields.map((field, index) => {
            const {dataType, mandatory, min, max, pattern, description, name} = entity.fields[index];
            return (
                <Accordion>
                    <AccordionSummary
                        expandIcon={<ExpandMore/>}
                        aria-controls="panel1a-content"
                        id="panel1a-header"
                    >
                        <Typography sx={{flexShrink: 0, width: "50%"}}>Feldname</Typography>
                        <Typography
                            sx={{color: 'text.secondary'}}>{/* Add something interesting here */}</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <Grid container spacing={4}>
                            <Grid item sm={6}>
                                <TextField fullWidth value={name} label={"Name"}/>
                            </Grid>
                            <Grid item sm={6}>
                                <FormControl fullWidth>
                                    <InputLabel id="demo-simple-select-label">Datentyp</InputLabel>
                                    <Select
                                        labelId="demo-simple-select-label"
                                        id="demo-simple-select"
                                        value={dataType.id}
                                        label="Datatype"
                                        onChange={(event) => handleFieldDataTypeChange(event.target.value, index)}
                                    >
                                        {dataTypes.map(dataType => (
                                            <MenuItem value={dataType.id}>{dataType.name}</MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>
                            </Grid>
                            <Grid item sm={12}>
                                <TextField
                                    fullWidth
                                    label={"Beschreibung"}
                                    value={description}
                                    onChange={(event) => editFieldProperty("description", event.target.value, index)}
                                />
                            </Grid>
                            <Grid item sm={12}>
                                <Divider variant={"middle"}>Restriktionen</Divider>
                            </Grid>
                            <Grid item sm={12}>
                                <TextField
                                    fullWidth
                                    label={"Pattern"}
                                    value={pattern}
                                    onChange={(event) => editFieldProperty("pattern", event.target.value, index)}
                                />
                            </Grid>
                            <Grid item sm={6}>
                                <TextField
                                    fullWidth
                                    label={"Min"}
                                    value={min}
                                    disabled={!dataType.allowMin}
                                    onChange={(event) => editFieldProperty("min", event.target.value, index)}
                                />
                            </Grid>
                            <Grid item sm={6}>
                                <TextField
                                    fullWidth
                                    label={"Max"}
                                    value={max}
                                    disabled={!dataType.allowMax}
                                    onChange={(event) => editFieldProperty("max", event.target.value, index)}
                                />
                            </Grid>
                            <Grid item sm={12}>
                                <FormControlLabel
                                    control={<Checkbox defaultChecked/>}
                                    label="Pflichtfeld"
                                    value={mandatory}
                                    onChange={(event) => editFieldProperty("mandatory", event.target.value, index)}
                                />
                            </Grid>
                        </Grid>
                    </AccordionDetails>
                </Accordion>
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
                        Relationen
                    </TabPanel>
                </Box>
                <DialogActions>
                    <Button onClick={handleSave}>Speichern</Button>
                </DialogActions>
            </Container>
        </Dialog>
    )

}

export default EntityEditor;