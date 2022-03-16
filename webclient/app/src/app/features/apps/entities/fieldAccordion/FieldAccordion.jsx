import React from "react";
import {
    Accordion,
    AccordionDetails,
    AccordionSummary,
    Checkbox,
    Divider,
    FormControl,
    FormControlLabel,
    Grid,
    InputLabel,
    MenuItem,
    Select,
    TextField,
    Typography
} from "@mui/material";
import {ExpandMore} from "@mui/icons-material";
import PropTypes from "prop-types";
import FieldAccordionStyles from "./FieldAccordionStyles";
import {useTranslation} from "react-i18next";
import ValidatedTextField from "../../../../commons/validatedTextField/ValidatedTextField";
import RegexConfig from "../../../../../regexConfig";


function FieldAccordion(props) {

    const {
        dataType,
        mandatory,
        min,
        max,
        pattern,
        name,
        editFieldProperty,
        dataTypes,
        isCreate
    } = props;
    const fieldAccordionStyles = FieldAccordionStyles();
    const {t} = useTranslation();

    function renderAccordionTitle(name) {
        let value = t("field.new");
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
                        <ValidatedTextField
                            isCreate={isCreate}
                            regex={RegexConfig.fieldName}
                            helperText={t("field.fieldName.hint")}
                            fullWidth
                            value={name}
                            label={t("field.fieldName") + "*"}
                            onChange={(event) => editFieldProperty("fieldName", event.target.value)}
                        />
                    </Grid>
                    <Grid item sm={6}>
                        <FormControl fullWidth>
                            <InputLabel id="demo-simple-select-label">{t("field.fieldType")}</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                value={dataType}
                                label={t("field.fieldType")}
                                onChange={(event) => editFieldProperty("fieldType", event.target.value)}
                            >
                                {dataTypes.map(dataType => (
                                    <MenuItem value={dataType.name} key={dataType.id}>{dataType.name}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid item sm={12}>
                        <Divider variant={"middle"}>{t("field.restrictions")}</Divider>
                    </Grid>
                    <Grid item sm={12}>
                        <TextField
                            fullWidth
                            label={t("field.pattern")}
                            value={pattern ?? ""}
                            onChange={(event) => editFieldProperty("fieldValidateRulesPattern", event.target.value)}
                        />
                    </Grid>
                    <Grid item sm={6}>
                        <TextField
                            fullWidth
                            label={t("field.min")}
                            value={min ?? ""}
                            disabled={!dataType.allowMin}
                            type={"number"}
                            onChange={(event) => editFieldProperty("fieldValidateRulesMin", event.target.value)}
                        />
                    </Grid>
                    <Grid item sm={6}>
                        <TextField
                            fullWidth
                            label={t("field.max")}
                            value={max ?? ""}
                            type={"number"}
                            disabled={!dataType.allowMax}
                            onChange={(event) => editFieldProperty("fieldValidateRulesMax", event.target.value)}
                        />
                    </Grid>
                    <Grid item sm={12}>
                        <FormControlLabel
                            control={<Checkbox defaultChecked/>}
                            label={t("field.required")}
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
    dataType: PropTypes.string,
    mandatory: PropTypes.bool,
    min: PropTypes.any,
    max: PropTypes.any,
    pattern: PropTypes.string,
    name: PropTypes.string,
    editFieldProperty: PropTypes.func.isRequired,
    dataTypes: PropTypes.array,
    isCreate: PropTypes.bool
};

FieldAccordion.defaultProps = {
    dataType: "",
    mandatory: false,
    min: "",
    max: "",
    pattern: "",
    name: "",
    dataTypes: [],
    isCreate: false
}

export default FieldAccordion;