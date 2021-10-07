import React from "react";
import {
    Accordion,
    AccordionDetails,
    AccordionSummary, Checkbox, Divider,
    FormControl, FormControlLabel,
    Grid,
    InputLabel, MenuItem, Select,
    TextField,
    Typography
} from "@mui/material";
import {ExpandMore} from "@mui/icons-material";
import PropTypes from "prop-types";


function RelationshipAccordion(props) {

    const {
        otherEntityRelationshipName,
        otherEntityName,
        relationshipName,
        entities,
        currentEntity,
        editRelationshipProperty,
        relationshipType,
        relationshipTypes
    } = props;

    function renderAccordionTitle() {
        let value = "Neue Relation";
        if (relationshipName && otherEntityRelationshipName) {
            value = `${relationshipName} -> ${otherEntityRelationshipName}`;
        }
        return value;
    }

    function prepareEntitiesSelection() {
        return entities.map(entity => {
            return entity.name;
        })
    }

    function prepareEntitiesFieldSelection() {
        let value = [];
        const foundSelectedEntity = entities.find(entity => otherEntityName === entity.name);
        if (foundSelectedEntity) {
            value = foundSelectedEntity.fields.map(field => field);
        }
        console.log("entitiesField",value)
        return value;
    }

    return (
        <Accordion>
            <AccordionSummary
                expandIcon={<ExpandMore/>}
                aria-controls="panel1a-content"
                id="panel1a-header"
            >
                <Typography sx={{flexShrink: 0, width: "50%"}}>{renderAccordionTitle()}</Typography>
                <Typography
                    sx={{color: 'text.secondary'}}>{/* Add something interesting here */}</Typography>
            </AccordionSummary>
            <AccordionDetails>
                <Grid container spacing={4}>
                    <Grid item sm={12}>
                        <FormControl fullWidth>
                            <InputLabel id="demo-simple-select-label">Relationstyp</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                value={relationshipType}
                                label="Quellfeld"
                                onChange={(event) => editRelationshipProperty("relationshipType", event.target.value)}
                            >
                                {relationshipTypes.map(relationshipType => (
                                    <MenuItem value={relationshipType}>{relationshipType}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </Grid>
                        <Grid item sm={6}>
                        <Typography variant={"h6"} gutterBottom>Quelle</Typography>
                        <FormControl fullWidth>
                            <InputLabel id="demo-simple-select-label">Quellfeld</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                value={relationshipName}
                                label="Quellfeld"
                                onChange={(event) => editRelationshipProperty("relationshipName", event.target.value)}
                            >
                                {currentEntity.fields.map(field => (
                                    <MenuItem value={field.name}>{field.name}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid item sm={6}>
                        <Typography variant={"h6"} gutterBottom>Ziel</Typography>
                        <FormControl fullWidth style={{paddingBottom: "2rem"}}>
                            <InputLabel id="demo-simple-select-label">Ziel Entität</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                value={otherEntityName}
                                label="Ziel Domain"
                                onChange={(event) => editRelationshipProperty("otherEntityName", event.target.value)}
                            >
                                {prepareEntitiesSelection().map(entityName => (
                                    <MenuItem value={entityName}>{entityName}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                        <FormControl fullWidth>
                            <InputLabel id="demo-simple-select-label">Ziel Feld</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                value={otherEntityRelationshipName}
                                label="Ziel Feld"
                                onChange={(event) => editRelationshipProperty("otherEntityRelationshipName", event.target.value)}
                            >
                                {prepareEntitiesFieldSelection().map(field => (
                                    <MenuItem value={field.name}>{field.name}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </Grid>

                </Grid>
            </AccordionDetails>
        </Accordion>
    )
}

RelationshipAccordion.propTypes = {
    otherEntityRelationshipName: PropTypes.string,
    otherEntityName: PropTypes.string,
    relationshipName: PropTypes.string,
    entities: PropTypes.array,
    currentEntity: PropTypes.object,
    editRelationshipProperty: PropTypes.func,
    relationshipType: PropTypes.string,
    relationshipTypes: PropTypes.arrayOf(PropTypes.string)
};

RelationshipAccordion.defaultProps = {}

export default RelationshipAccordion;