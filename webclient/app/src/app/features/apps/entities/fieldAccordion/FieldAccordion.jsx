import {Delete, ExpandMore} from "@mui/icons-material";
import {
    Accordion,
    AccordionDetails,
    AccordionSummary,
    Checkbox,
    Divider,
    FormControl,
    FormControlLabel,
    Grid,
    IconButton,
    InputLabel,
    MenuItem,
    Select,
    TextField,
    Stack,
    Typography
} from "@mui/material";
import PropTypes from "prop-types";
import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {ValidatedTextField} from "@starwit/react-starwit";
import RegexConfig from "../../../../../regexConfig";
import FieldAccordionStyles from "./FieldAccordionStyles";

function FieldAccordion(props) {
    const {
        fieldType,
        enumDef,
        enums,
        mandatory,
        min,
        max,
        minLength,
        maxLength,
        pattern,
        name,
        editFieldProperty,
        fieldTypes,
        isCreate,
        handleDelete
    } = props;
    const fieldAccordionStyles = FieldAccordionStyles();
    const {t} = useTranslation();

    const [isMinDisabled, setIsMinDisabled] = useState(false);
    const [isMaxDisabled, setIsMaxDisabled] = useState(false);
    const [isPatternDisabled, setIsPatternDisabled] = useState(false);
    const [fullFieldType, setFullFieldType] = useState(null);

    useEffect(() => {
        const foundFieldType = fieldTypes.find(fieldTypeElement => {
            return fieldTypeElement.name === fieldType;
        });
        if (foundFieldType) {
            setIsMinDisabled(!foundFieldType.allowMin);
            setIsMaxDisabled(!foundFieldType.allowMax);
            setIsPatternDisabled(!foundFieldType.allowPattern);
            setFullFieldType(foundFieldType);
        }
    }, [fieldTypes, fieldType]);

    function renderAccordionTitle(name) {
        let value = t("field.new");
        if (name) {
            value = name;
        }
        return value;
    }

    function renderMinField() {
        let value = min;
        let propertyField = "fieldValidateRulesMin";
        if (fullFieldType?.usesLengthLimit) {
            value = minLength;
            propertyField = "fieldValidateRulesMinlength";
        }
        return <TextField fullWidth label={t("field.min")} value={value ?? ""} disabled={isMinDisabled} type={"number"}
            onChange={event => editFieldProperty(propertyField, event.target.value)}/>;
    }

    function renderMaxField() {
        let value = max;
        let propertyField = "fieldValidateRulesMax";
        if (fullFieldType?.usesLengthLimit) {
            value = maxLength;
            propertyField = "fieldValidateRulesMaxlength";
        }
        return <TextField fullWidth label={t("field.max")} value={value ?? ""} type={"number"} disabled={isMaxDisabled}
            onChange={event => editFieldProperty(propertyField, event.target.value)}/>;
    }

    function handleFieldTypeChange(event) {
        if (event.target.value === "Enum" && enums?.length > 0) {
            editFieldProperty("enumDef", enumDef ? enumDef : enums[0]);
        }
        editFieldProperty("fieldType", event.target.value);
        editFieldProperty("fieldValidateRulesPattern", "");
    }

    function renderEnumSelect() {
        if (fieldType === "Enum" && enums?.length > 0) {
            return (
                <FormControl fullWidth>
                    <InputLabel id="enum-select-label">{t("field.enumSelect")}</InputLabel>
                    <Typography
                        className={fieldAccordionStyles.subtitle}>{/* Add something interesting here */}
                    </Typography>
                    <Select labelId="enum-select-label-id" id="enum-select"
                        value={enumDef ? enumDef : enums[0]} label={t("field.enumSelect")}
                        onChange={event => editFieldProperty("enumDef", event.target.value)}>
                        {enums?.map(enumElement => renderEnumSelectList(enumElement))}
                    </Select>
                </FormControl>
            );
        } else if (fieldType === "Enum") {
            return (
                <FormControl fullWidth>
                    <Typography
                        className={fieldAccordionStyles.subtitle}>{t("field.enumDef.notAvailable")}
                    </Typography>
                </FormControl>
            );
        }
    }

    function renderEnumSelectList(enumElement) {
        if (enumElement.id === enumDef?.id) {
            return (
                <MenuItem value={enumDef} key={enumDef.id}>
                    {enumDef.name}
                </MenuItem>
            );
        } else {
            return (
                <MenuItem value={enumElement} key={enumElement.id}>
                    {enumElement.name}
                </MenuItem>
            );
        }
    }

    return (
        <Grid container alignItems={"center"} justifyItems={"center"}>
            <Grid item sm={11}>
                <Accordion elevation={2}>
                    <AccordionSummary expandIcon={<ExpandMore/>} aria-controls="panel1a-content"
                        id="panel1a-header">
                        <Typography className={fieldAccordionStyles.title}>{renderAccordionTitle(name)}</Typography>
                        <Typography
                            className={fieldAccordionStyles.subtitle}>{/* Add something interesting here */}
                        </Typography>
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
                                    onChange={event => editFieldProperty("fieldName", event.target.value)}
                                />
                            </Grid>
                            <Grid item sm={6}>
                                <Stack spacing={1}>
                                    <FormControl fullWidth>
                                        <InputLabel id="fieldtype-select-label">{t("field.fieldType")}</InputLabel>
                                        <Select labelId="fieldtype-select-label-id" id="fieldtype-select"
                                            value={fieldType} label={t("field.fieldType")}
                                            onChange={handleFieldTypeChange}>
                                            {fieldTypes.map(fieldTypeElement => (
                                                <MenuItem value={fieldTypeElement.name} key={fieldTypeElement.name}>
                                                    {fieldTypeElement.name}
                                                </MenuItem>
                                            ))}
                                        </Select>
                                    </FormControl>
                                    {renderEnumSelect()}
                                </Stack>
                            </Grid>
                            <Grid item sm={12}>
                                <Divider variant={"middle"}>{t("field.restrictions")}</Divider>
                            </Grid>
                            <Grid item sm={12}>
                                <TextField
                                    fullWidth
                                    label={t("field.pattern")}
                                    value={pattern ?? ""}
                                    onChange=
                                        {event => editFieldProperty("fieldValidateRulesPattern", event.target.value)}
                                    disabled={isPatternDisabled}
                                />
                            </Grid>
                            <Grid item sm={6}>
                                {renderMinField()}
                            </Grid>
                            <Grid item sm={6}>
                                {renderMaxField()}
                            </Grid>
                            <Grid item sm={12}>
                                <FormControlLabel
                                    control={<Checkbox checked={mandatory}/>}
                                    label={t("field.required")}
                                    value={mandatory}
                                    onChange={event => editFieldProperty("mandatory", !mandatory)}
                                />
                            </Grid>
                        </Grid>
                    </AccordionDetails>
                </Accordion>
            </Grid>
            <Grid item sm={1}>
                <IconButton aria-label="delete" onClick={handleDelete}>
                    <Delete/>
                </IconButton>
            </Grid>
        </Grid>
    );
}

FieldAccordion.defaultProps = {
    fieldType: "",
    mandatory: false,
    min: "",
    max: "",
    pattern: "",
    name: "",
    fieldTypes: [],
    isCreate: false
};

export default FieldAccordion;
