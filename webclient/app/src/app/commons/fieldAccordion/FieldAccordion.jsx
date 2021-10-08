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
import FieldAccordionStyles from "./FieldAccordionStyles";


function FieldAccordion(props) {

    const {dataType, mandatory, min, max, pattern, description, name, editFieldProperty, dataTypes} = props;
    const fieldAccordionStyles = FieldAccordionStyles();

    function renderAccordionTitle(name) {
        let value = "Neues Feld";
        if (name) {
            value = name;
        }
        return value;
    }

    return (
        <Accordion>
            <AccordionSummary
                expandIcon={<ExpandMore/>}
                aria-controls="panel1a-content"
                id="panel1a-header"
            >
                <Typography className={fieldAccordionStyles.title}>{renderAccordionTitle(name)}</Typography>
                <Typography
                    className={fieldAccordionStyles.subtitle}>{/* Add something interesting here */}</Typography>
            </AccordionSummary>
            <AccordionDetails>
                <Grid container spacing={4}>
                    <Grid item sm={6}>
                        <TextField fullWidth value={name} label={"Name"}
                                   onChange={(event) => editFieldProperty("name", event.target.value)}/>
                    </Grid>
                    <Grid item sm={6}>
                        <FormControl fullWidth>
                            <InputLabel id="demo-simple-select-label">Datentyp</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                value={dataType.id}
                                label="Datatype"
                                onChange={(event) => editFieldProperty("dataType", event.target.value)}
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
                            onChange={(event) => editFieldProperty("description", event.target.value)}
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
                            onChange={(event) => editFieldProperty("pattern", event.target.value)}
                        />
                    </Grid>
                    <Grid item sm={6}>
                        <TextField
                            fullWidth
                            label={"Min"}
                            value={min}
                            disabled={!dataType.allowMin}
                            onChange={(event) => editFieldProperty("min", event.target.value)}
                        />
                    </Grid>
                    <Grid item sm={6}>
                        <TextField
                            fullWidth
                            label={"Max"}
                            value={max}
                            disabled={!dataType.allowMax}
                            onChange={(event) => editFieldProperty("max", event.target.value)}
                        />
                    </Grid>
                    <Grid item sm={12}>
                        <FormControlLabel
                            control={<Checkbox defaultChecked/>}
                            label="Pflichtfeld"
                            value={mandatory}
                            onChange={(event) => editFieldProperty("mandatory", !mandatory)}
                        />
                    </Grid>
                </Grid>
            </AccordionDetails>
        </Accordion>
    )
}

FieldAccordion.propTypes = {
    dataType: PropTypes.object,
    mandatory: PropTypes.bool,
    min: PropTypes.any,
    max: PropTypes.any,
    pattern: PropTypes.string,
    description: PropTypes.string,
    name: PropTypes.string,
    editFieldProperty: PropTypes.func.isRequired,
    dataTypes: PropTypes.array,
};

FieldAccordion.defaultProps = {
    dataType: "",
    mandatory: false,
    min: "",
    max: "",
    pattern: "",
    description: "",
    name: "",
    dataTypes: []
}

export default FieldAccordion;