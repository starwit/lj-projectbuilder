import React from "react";
import {
    Accordion,
    AccordionDetails,
    AccordionSummary,
    FormControl,
    Grid,
    InputLabel,
    MenuItem,
    Select,
    Typography
} from "@mui/material";
import {ExpandMore} from "@mui/icons-material";
import PropTypes from "prop-types";
import RelationshipAccordionStyles from "./RelationshipAccordionStyles";
import {useTranslation} from "react-i18next";

//some commit
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
    const relationshipAccordionStyles = RelationshipAccordionStyles();
    const {t} = useTranslation();


    function renderAccordionTitle() {
        let value = t("relationshipAccordion.newRelation");
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
        return value;
    }

    return (
        <Accordion>
            <AccordionSummary
                expandIcon={<ExpandMore/>}
                aria-controls="panel1a-content"
                id="panel1a-header"
            >
                <Typography className={relationshipAccordionStyles.title}>{renderAccordionTitle()}</Typography>
                <Typography
                    className={relationshipAccordionStyles.subtitle}>{/* Add something interesting here */}</Typography>
            </AccordionSummary>
            <AccordionDetails>
                <Grid container spacing={4}>
                    <Grid item sm={12}>
                        <FormControl fullWidth>
                            <InputLabel
                                id="demo-simple-select-label">{t("relationshipAccordion.relationType")}</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                value={relationshipType}
                                label={t("relationshipAccordion.sourceField")}
                                onChange={(event) => editRelationshipProperty("relationshipType", event.target.value)}
                                disabled={!relationshipTypes}
                            >
                                {relationshipTypes?.map(relationshipType => (
                                    <MenuItem value={relationshipType}>{relationshipType}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid item sm={6}>
                        <Typography variant={"h6"} gutterBottom>{t("relationshipAccordion.source")}</Typography>
                        <FormControl fullWidth>
                            <InputLabel
                                id="demo-simple-select-label">{t("relationshipAccordion.sourceField")}</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                value={relationshipName}
                                label={t("relationshipAccordion.sourceField")}
                                onChange={(event) => editRelationshipProperty("relationshipName", event.target.value)}
                                disabled={!currentEntity.fields}
                            >
                                {currentEntity?.fields?.map(field => (
                                    <MenuItem value={field.fieldName}>{field.fieldName}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid item sm={6}>
                        <Typography variant={"h6"} gutterBottom>{t("relationshipAccordion.target")}</Typography>
                        <FormControl fullWidth className={relationshipAccordionStyles.spacerBottom}>
                            <InputLabel
                                id="demo-simple-select-label">{t("relationshipAccordion.targetDomain")}</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                value={otherEntityName}
                                label={t("relationshipAccordion.targetDomain")}
                                onChange={(event) => editRelationshipProperty("otherEntityName", event.target.value)}
                            >
                                {prepareEntitiesSelection()?.map(entityName => (
                                    <MenuItem value={entityName}>{entityName}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                        <FormControl fullWidth>
                            <InputLabel
                                id="demo-simple-select-label">{t("relationshipAccordion.targetField")}</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                value={otherEntityRelationshipName}
                                label={t("relationshipAccordion.targetField")}
                                onChange={(event) => editRelationshipProperty("otherEntityRelationshipName", event.target.value)}
                            >
                                {prepareEntitiesFieldSelection().map(field => (
                                    <MenuItem value={field.fieldName}>{field.fieldName}</MenuItem>
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