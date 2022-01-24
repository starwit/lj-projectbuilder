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
    TextField,
    Typography
} from "@mui/material";
import {ExpandMore} from "@mui/icons-material";
import PropTypes from "prop-types";
import RelationshipAccordionStyles from "./RelationshipAccordionStyles";
import {useTranslation} from "react-i18next";
import { RelationshipType } from "./Relationship";

//some commit
function RelationshipAccordion(props) {

    const {
        relationship,
        entities,
        currentEntity,
        editRelationshipProperty
    } = props;
    const relationshipAccordionStyles = RelationshipAccordionStyles();
    const {t} = useTranslation();
    const targetEntities = entities.filter(e => e.name !== currentEntity.name);


    function renderAccordionTitle() {
        let value = t("relationshipAccordion.newRelation");
        if (relationship.relationshipName && relationship.otherEntityRelationshipName) {
            value = `${currentEntity.name} -> ${relationship.otherEntityRelationshipName}`;
        }
        return value;
    }

    function prepareEntitiesSelection() {
        return targetEntities.map(entity => {
            relationship.relationshipName = entity.name;
            return entity.name;
        })
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
                                id="relationType">{t("relationshipAccordion.relationType")}</InputLabel>
                            <Select
                                labelId="relationType"
                                id="relationTypeSelect"
                                value={relationship.relationshipType}
                                label={t("relationshipAccordion.sourceField")}
                                onChange={(event) => editRelationshipProperty("relationshipType", event.target.value)}
                            >
                                {Object.keys(RelationshipType).map(key => (
                                    <MenuItem value={RelationshipType[key]} key={key} >{RelationshipType[key]}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid item sm={1}>
                        <Typography gutterBottom>{t("relationshipAccordion.source")}</Typography>
                    </Grid>
                    <Grid item sm={5}>
                        <FormControl fullWidth >
                            <TextField
                                label={ t("relationshipAccordion.sourceEntity") }
                                value={currentEntity.name}
                                disabled={true}
                            >
                            </TextField>
                        </FormControl>
                        <FormControl fullWidth>
                            <TextField
                                label={ t("relationshipAccordion.sourceField") }
                                value={relationship.relationshipName}
                            />
                        </FormControl>
                    </Grid>
                    <Grid item sm={1}>
                        <Typography gutterBottom>{t("relationshipAccordion.target")}</Typography>
                    </Grid>
                    <Grid item sm={5}>
                        <FormControl fullWidth className={relationshipAccordionStyles.spacerBottom}>
                            <InputLabel
                                id="targetEntity">{t("relationshipAccordion.targetEntity") + "*"}</InputLabel>
                            <Select
                                labelId="targetEntity"
                                id="targetEntitySelect"
                                value={relationship.otherEntityName}
                                label={t("relationshipAccordion.targetEntity") + "*" }
                                onChange={(event) => editRelationshipProperty("otherEntityName", event.target.value)}
                            >
                                {prepareEntitiesSelection()?.map(entityName => (
                                    <MenuItem value={entityName}>{entityName}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                        <FormControl fullWidth>
                            <TextField
                                label={ t("relationshipAccordion.targetField") }
                                value={relationship.otherEntityRelationshipName}
                            />
                        </FormControl>
                    </Grid>
                </Grid>
            </AccordionDetails>
        </Accordion>
    )
}

RelationshipAccordion.propTypes = {
    Relationship: PropTypes.instanceOf(RelationshipType),
    entities: PropTypes.array,
    currentEntity: PropTypes.object,
    editRelationshipProperty: PropTypes.func,
};

RelationshipAccordion.defaultProps = {}

export default RelationshipAccordion;