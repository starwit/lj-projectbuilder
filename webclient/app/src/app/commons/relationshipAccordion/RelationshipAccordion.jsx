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
    Typography,
    FormHelperText
} from "@mui/material";
import {ExpandMore} from "@mui/icons-material";
import PropTypes from "prop-types";
import RelationshipStyles from "./RelationshipStyles";
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
    const relationshipStyles = RelationshipStyles();
    const {t} = useTranslation();
    const targetEntities = entities.filter(e => e.name !== currentEntity.name);


    function renderAccordionTitle() {
        let value = t("relationship.newRelation");
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
                <Typography className={relationshipStyles.title}>{renderAccordionTitle()}</Typography>
                <Typography
                    className={relationshipStyles.subtitle}>{/* Add something interesting here */}</Typography>
            </AccordionSummary>
            <AccordionDetails>
                <Grid container spacing={4}>
                    <Grid item sm={12}>
                        <FormControl fullWidth>
                            <InputLabel
                                id="relationType">{t("relationship.relationType")}</InputLabel>
                            <Select
                                labelId="relationType"
                                id="relationTypeSelect"
                                value={relationship.relationshipType}
                                label={t("relationship.sourceField")}
                                onChange={(event) => editRelationshipProperty("relationshipType", event.target.value)}
                            >
                                {Object.keys(RelationshipType).map(key => (
                                    <MenuItem value={RelationshipType[key]} key={key} >{RelationshipType[key]}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid item sm={1}>
                        <Typography gutterBottom>{t("relationship.source")}</Typography>
                    </Grid>
                    <Grid item sm={5}>
                        <FormControl fullWidth >
                            <TextField
                                label={ t("relationship.sourceEntity") }
                                value={currentEntity.name}
                                disabled={true}
                                helperText={ t("relationship.sourceEntity.description") }
                            >
                            </TextField>
                        </FormControl>
                        <FormControl fullWidth>
                            <TextField
                                label={ t("relationship.sourceField") }
                                value={relationship.relationshipName}
                                helperText={ t("relationship.sourceField.description", {entityName: relationship.otherEntityName ? relationship.otherEntityName : t("relationship.targetEntity")} )}
                            />
                        </FormControl>
                    </Grid>
                    <Grid item sm={1}>
                        <Typography gutterBottom>{t("relationship.target")}</Typography>
                    </Grid>
                    <Grid item sm={5}>
                        <FormControl fullWidth className={relationshipStyles.spacerBottom}>
                            <InputLabel
                                id="targetEntity">{t("relationship.targetEntity") + "*"}</InputLabel>
                            <Select
                                labelId="targetEntity"
                                id="targetEntitySelect"
                                value={relationship.otherEntityName}
                                label={t("relationship.targetEntity") + "*" }
                                onChange={(event) => editRelationshipProperty("otherEntityName", event.target.value)}
                            >
                                {targetEntities.map(entity => (
                                    <MenuItem value={entity.name} key={entity.id} >{entity.name}</MenuItem>
                                ))}
                            </Select>
                            <FormHelperText id="targetEntityHelperText">{ t("relationship.targetEntity.description") }</FormHelperText>
                        </FormControl>
                        <FormControl fullWidth>
                            <TextField
                                label={ t("relationship.targetField") }
                                value={relationship.otherEntityRelationshipName}
                                helperText={ t("relationship.targetField.description", {entityName: currentEntity.name ? currentEntity.name : t("relationship.sourceEntity")} )}
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