import {Card, Grid, IconButton, Table, TableBody, TableCell, TableHead, TableRow, Typography} from "@mui/material";
import {Delete, Edit} from "@mui/icons-material";
import React, {useState} from "react";
import Statement from "../statement/Statement";
import EntityCardStyles from "./EntityCardStyles";
import PropTypes from "prop-types";
import {useTranslation} from "react-i18next";

function EntityCard(props) {

    const entityCardStyles = EntityCardStyles();
    const [isDeleting, setIsDeleting] = useState(false);

    const {entity, handleEdit, handleDelete, editable} = props;
    const {t} = useTranslation();

    function renderTitle(name) {
        let value = <Typography variant={"h6"} color={"text.secondary"}>{t("entityCard.newEntity")}</Typography>
        if (name) {
            value = <Typography variant={"h6"}>{name}</Typography>
        }
        return value;
    }

    function renderAttributes(entity) {

        if (!entity.fields || entity.fields.length === 0) {
            return (
                <TableRow key={0}>
                    <TableCell colSpan={4} >
                        <div className={entityCardStyles.statementWrapper}>
                            <Statement message={t("entityCard.noFields")}/>
                        </div>
                    </TableCell>
                </TableRow>
            )
        } else {
            return entity.fields.map((field, index) => {
                    return (
                    <TableRow key={index}>
                        <TableCell />
                        <TableCell>{field.fieldName}</TableCell>
                        <TableCell>{field.fieldType}</TableCell>
                        <TableCell />
                    </TableRow>
                )
            })
        }
    }

    function renderFieldsTable(entity) {
        return (
            <Table size={"small"}>
                <TableHead>
                    <TableRow className={entityCardStyles.tableRow}>
                        <TableCell className={entityCardStyles.relationshipPlaceholder}>
                            <div className={"anchor_" + entity.name + "_l"}/>
                        </TableCell>
                        <TableCell>{t("entityCard.name")}</TableCell>
                        <TableCell>{t("entityCard.dataType")}</TableCell>
                        <TableCell className={entityCardStyles.relationshipPlaceholder}>
                            <div className={"anchor_" + entity.name + "_r"}/>
                        </TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {renderAttributes(entity)}
                </TableBody>
            </Table>
        )
    }

    function renderEditButton() {
        if (handleEdit && editable) {
            return (
                <Grid item sm={2}>
                    <IconButton onClick={() => handleEdit(entity)}>
                        <Edit fontSize={"small"}/></IconButton>
                </Grid>
            )
        }
    }

    function prepareDelete() {
        handleDelete(entity.id)
            .then(setIsDeleting(false))
            .catch(setIsDeleting(false));
    }

    function renderDeleteWrapper() {
        if (handleDelete && editable) {
            return (
                <Grid item sm={2}>
                    <IconButton
                        onClick={prepareDelete}
                        disabled={isDeleting}
                    >
                        <Delete fontSize={"small"}/>
                    </IconButton>
                </Grid>
            )
        }
    }

    function calculateTitleWidth() {
        let titleWidth = 12;
        if (handleDelete) {
            titleWidth -= 2;
        }
        if (handleEdit) {
            titleWidth -= 2;
        }
        return titleWidth;
    }

    return (
        <Card className={`${entityCardStyles.entityCard}`}>
            <Grid container>
                <Grid item sm={calculateTitleWidth()}>
                    {renderTitle(entity.name)}
                </Grid>
                {renderEditButton()}
                {renderDeleteWrapper()}
            </Grid>
            {renderFieldsTable(entity)}
        </Card>
    )

}

EntityCard.propTypes = {
    entity: PropTypes.object.isRequired,
    handleEdit: PropTypes.func,
    handleDelete: PropTypes.func,
    editable: PropTypes.bool
}

EntityCard.defaultProps = {
    editable: true
}

export default EntityCard;