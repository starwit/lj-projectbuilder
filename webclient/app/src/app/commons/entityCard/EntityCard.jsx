import {Card, Grid, IconButton, Table, TableBody, TableCell, TableHead, TableRow, Typography} from "@mui/material";
import {Delete, Edit} from "@mui/icons-material";
import React from "react";
import Statement from "../statement/Statement";
import EntityCardStyles from "./EntityCardStyles";
import PropTypes from "prop-types";
import {useTranslation} from "react-i18next";

function EntityCard(props) {

    const entityCardStyles = EntityCardStyles();
    const {entity, handleEdit, handleDelete} = props;
    const {t} = useTranslation();

    function renderTitle(name) {
        let value = <Typography variant={"h6"} color={"text.secondary"}>{t("entityCard.newEntity")}</Typography>
        if (name) {
            value = <Typography variant={"h6"}>{name}</Typography>
        }
        return value;
    }

    function renderAttributes(entity) {

        return entity.fields.map((field, index) => {
            return (

                <TableRow key={index}>
                    <TableCell className={entityCardStyles.relationshipPlaceholder}>
                        <div className={"anchor_" + entity.name + "_" + field.name + "_l"}/>
                    </TableCell>
                    <TableCell>{field.name}</TableCell>
                    <TableCell>{field.dataType.name}</TableCell>
                    <TableCell className={entityCardStyles.relationshipPlaceholder}>
                        <div className={"anchor_" + entity.name + "_" + field.name + "_r"}/>
                    </TableCell>
                </TableRow>
            )
        })
    }

    function renderFieldsTable(entity) {
        if (!entity.fields || entity.fields.length === 0) {
            return (
                <div className={entityCardStyles.statementWrapper}>
                    <Statement message={t("entityCard.noFields")}/>
                </div>
            )
        }
        return (
            <Table size={"small"}>
                <TableHead>
                    <TableRow className={entityCardStyles.tableRow}>
                        <TableCell/>
                        <TableCell>{t("entityCard.name")}</TableCell>
                        <TableCell>{t("entityCard.dataType")}</TableCell>
                        <TableCell/>

                    </TableRow>
                </TableHead>
                <TableBody>
                    {renderAttributes(entity)}
                </TableBody>
            </Table>
        )
    }

    function renderEditButton() {
        if (handleEdit) {
            return (
                <Grid item sm={2}>
                    <IconButton onClick={() => handleEdit(entity)}>
                        <Edit fontSize={"small"}/></IconButton>
                </Grid>
            )
        }
    }

    function renderDeleteClick() {
        if (handleDelete) {
            return (
                <Grid item sm={2}>
                    <IconButton
                        onClick={() => handleDelete(entity.id)}
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
                {renderDeleteClick()}
            </Grid>
            {renderFieldsTable(entity)}
        </Card>
    )

}

EntityCard.propTypes = {
    entity: PropTypes.object.isRequired,
    handleEdit: PropTypes.func,
    handleDelete: PropTypes.func
}

export default EntityCard;