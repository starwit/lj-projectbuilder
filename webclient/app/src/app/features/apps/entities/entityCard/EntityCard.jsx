import {Card, Grid, IconButton, Table, TableBody, TableCell, TableHead, TableRow, Typography} from "@mui/material";
import {Delete, Edit} from "@mui/icons-material";
import React, {useState} from "react";
import {Statement, ConfirmationDialog} from "@starwit/react-starwit";
import EntityCardStyles from "./EntityCardStyles";
import PropTypes from "prop-types";
import {useTranslation} from "react-i18next";

function EntityCard(props) {
    const entityCardStyles = EntityCardStyles();
    const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false);

    const {entity, onEdit, handleDelete, editable} = props;
    const {t} = useTranslation();

    function renderTitle(name) {
        let value = <Typography variant={"h6"} color={"text.secondary"} noWrap>{t("entity.new")}</Typography>;
        if (name) {
            value = <Typography variant={"h6"} noWrap>{name}</Typography>;
        }
        return value;
    }

    function renderAttributes(entityToRender) {
        return entityToRender.fields.map((field, index) => {
            return (

                <TableRow key={index}>
                    <TableCell>{field.fieldName}</TableCell>
                    <TableCell>{field.fieldType}</TableCell>
                </TableRow>
            );
        });
    }

    function renderFieldsTable(entityToRender) {
        if (!entityToRender.fields || entityToRender.fields.length === 0) {
            return (
                <Statement message={t("entity.fields.empty")}/>
            );
        }
        return (
            <Table size={"small"}>
                <TableHead>
                    <TableRow className={entityCardStyles.tableRow}>
                        <TableCell>{t("field.fieldName")}
                        </TableCell>
                        <TableCell>{t("field.fieldType")}
                        </TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {renderAttributes(entityToRender)}
                </TableBody>
            </Table>
        );
    }

    function renderEditButton(entityId) {
        if (onEdit && editable) {
            return (
                <Grid item sm={2}>
                    <IconButton onClick={() => onEdit(entityId)}>
                        <Edit fontSize={"small"}/></IconButton>
                </Grid>
            );
        }
    }

    function openDeleteDialog() {
        setIsDeleteDialogOpen(true);
    }

    function closeDeleteDialog() {
        setIsDeleteDialogOpen(false);
    }

    function prepareDelete() {
        return handleDelete(entity.id);
    }

    function renderDeleteWrapper() {
        if (handleDelete && editable) {
            return (
                <Grid item sm={2}>
                    <IconButton
                        onClick={openDeleteDialog}
                    >
                        <Delete fontSize={"small"}/>
                    </IconButton>
                </Grid>
            );
        }
    }

    return (
        <div className={"anchor_" + entity.name}>
            <Card className={`${entityCardStyles.entityCard}`}>
                <Grid container>
                    <Grid item sm>
                        {renderTitle(entity.name)}
                    </Grid>
                    {renderEditButton(entity?.id)}
                    {renderDeleteWrapper()}
                </Grid>
                {renderFieldsTable(entity)}
            </Card>
            <ConfirmationDialog
                onSubmit={prepareDelete}
                onClose={closeDeleteDialog}
                title={t("entity.delete.title")}
                message={t("entity.delete.message")}
                open={isDeleteDialogOpen}
            />
        </div>
    );
}

EntityCard.propTypes = {
    entity: PropTypes.object.isRequired,
    onEdit: PropTypes.func,
    handleDelete: PropTypes.func,
    editable: PropTypes.bool
};

EntityCard.defaultProps = {
    editable: true
};

export default EntityCard;
