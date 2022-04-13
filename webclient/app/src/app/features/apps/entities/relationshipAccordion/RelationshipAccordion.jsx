import React from "react";
import {
    Accordion,
    AccordionDetails,
    AccordionSummary,
    FormControl,
    Grid,
    IconButton,
    InputLabel,
    MenuItem,
    Select,
    Stack,
    TextField,
    Typography
} from "@mui/material";
import {Delete, ExpandMore} from "@mui/icons-material";
import PropTypes from "prop-types";
import RelationshipStyles from "./RelationshipStyles";
import {useTranslation} from "react-i18next";
import {RelationshipType} from "../Relationship";
import ValidatedTextField from "../../../../commons/validatedTextField/ValidatedTextField";
import RegexConfig from "../../../../../regexConfig";

function RelationshipAccordion(props) {
    const {relationship, targetEntities, currentEntity, editRelationshipProperty, handleDelete} = props;
    const relationshipStyles = RelationshipStyles();
    const {t} = useTranslation();

    function renderAccordionTitle() {
        let value = t("relationship.new");
        if (currentEntity.name && relationship.otherEntityName) {
            value = `${currentEntity.name} -> ${relationship.otherEntityName} (${relationship.relationshipType})`;
        }
        return value;
    }

    return (
        <Grid container alignItems={"center"} justifyItems={"center"}>
            <Grid item sm={11}>
                <Accordion elevation={2}>
                    <AccordionSummary expandIcon={<ExpandMore/>} aria-controls="panel1a-content"
                        id="panel1a-header">
                        <Typography className={relationshipStyles.title}>{renderAccordionTitle()}</Typography>
                        <Typography
                            className={relationshipStyles.subtitle}>{/* Add something interesting here */}</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <Grid container spacing={4}>
                            <Grid item sm={12}>
                                <FormControl fullWidth>
                                    <InputLabel id="relationType">{t("relationship.relationshipType")}</InputLabel>
                                    <Select
                                        labelId="relationType"
                                        id="relationTypeSelect"
                                        value={relationship.relationshipType}
                                        label={t("relationship.sourceField")}
                                        onChange=
                                            {event => editRelationshipProperty("relationshipType", event.target.value)}
                                    >
                                        {Object.keys(RelationshipType).map(key => (
                                            <MenuItem value={RelationshipType[key]} key={key}>
                                                {RelationshipType[key]}
                                            </MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>
                            </Grid>
                            <Grid item sm={1}>
                                <Typography gutterBottom>{t("relationship.source")}</Typography>
                            </Grid>
                            <Grid item sm={5}>
                                <Stack spacing={3}>
                                    <FormControl fullWidth>
                                        <TextField
                                            label={t("relationship.sourceEntity")}
                                            value={currentEntity.name}
                                            disabled={true}
                                        />
                                    </FormControl>
                                    <FormControl fullWidth>
                                        <ValidatedTextField
                                            label={t("relationship.sourceField")}
                                            value={relationship.relationshipName}
                                            onChange={event => {
                                                editRelationshipProperty("relationshipName", event.target.value);
                                            }}
                                            helperText=
                                                {t("relationship.sourceField.hint",
                                                    {
                                                        entityName:
                                                            relationship.otherEntityName ?
                                                                relationship.otherEntityName :
                                                                t("relationship.targetEntity")
                                                    })
                                                }
                                            regex={RegexConfig.relationship}
                                        />
                                    </FormControl>
                                </Stack>
                            </Grid>
                            <Grid item sm={1}>
                                <Typography gutterBottom>{t("relationship.target")}</Typography>
                            </Grid>
                            <Grid item sm={5}>
                                <Stack spacing={3}>
                                    <FormControl fullWidth>
                                        <InputLabel id="targetEntity"
                                                    error={!RegexConfig.entityTitle.test(relationship.otherEntityName)}>
                                            {t("relationship.targetEntity") + "*"}
                                        </InputLabel>
                                        <Select
                                            error={!RegexConfig.entityTitle.test(relationship.otherEntityName)}
                                            labelId="targetEntity"
                                            id="targetEntitySelect"
                                            value={relationship.otherEntityName}
                                            label={t("relationship.targetEntity") + "*"}
                                            onChange={
                                                event => editRelationshipProperty("otherEntityName", event.target.value)
                                            }
                                        >
                                            {targetEntities.map(entity => (
                                                <MenuItem value={entity.name} key={entity.id}>
                                                    {entity.name}
                                                </MenuItem>
                                            ))}
                                        </Select>
                                    </FormControl>
                                    <FormControl fullWidth>
                                        <ValidatedTextField
                                            label={t("relationship.targetField")}
                                            value={relationship.otherEntityRelationshipName}
                                            onChange={
                                                event =>
                                                    editRelationshipProperty(
                                                        "otherEntityRelationshipName",
                                                        event.target.value
                                                    )
                                            }
                                            helperText={
                                                t("relationship.targetField.hint",
                                                    {
                                                        entityName: currentEntity.name ?
                                                            currentEntity.name :
                                                            t("relationship.sourceEntity")
                                                    })
                                            }
                                            regex={RegexConfig.relationship}
                                        />
                                    </FormControl>
                                </Stack>
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

RelationshipAccordion.propTypes = {
    Relationship: PropTypes.instanceOf(RelationshipType),
    entities: PropTypes.array,
    currentEntity: PropTypes.object,
    editRelationshipProperty: PropTypes.func
};

RelationshipAccordion.defaultProps = {};

export default RelationshipAccordion;
