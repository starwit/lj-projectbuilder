import {Card, Grid, IconButton, Typography} from "@mui/material";
import {Delete, Edit} from "@mui/icons-material";
import React, {useState} from "react";
import EnumCardStyles from "./EnumCardStyles";
import PropTypes from "prop-types";
import {useTranslation} from "react-i18next";
import ConfirmationDialog from "../../../../commons/confirmationDialog/ConfirmationDialog";

function EnumCard(props) {
    const enumCardStyles = EnumCardStyles();
    const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false);

    const {enumDef, onEdit, handleDelete, editable} = props;
    const {t} = useTranslation();

    function renderTitle(name) {
        let value = <Typography variant={"h6"} color={"text.secondary"} noWrap>{t("enum.new")}</Typography>;
        if (name) {
            value = <Typography variant={"h6"} noWrap>{name}</Typography>;
        }
        return value;
    }

    function renderEditButton(entityId) {
        if (onEdit && editable) {
            return (
                <Grid item sm={2}>
                    <IconButton onClick={() => onEdit(entityId)}>
                        <Edit fontSize={"small"}/>
                    </IconButton>
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
        handleDelete(entity.id);
    }

    function renderDeleteWrapper() {
        if (handleDelete && editable) {
            return (
                <Grid item sm={2}>
                    <IconButton onClick={openDeleteDialog}>
                        <Delete fontSize={"small"}/>
                    </IconButton>
                </Grid>
            );
        }
    }

    return (
        <div className={"anchor_" + enumDef.name}>
            <Card className={`${enumCardStyles.enumCard}`}>
                <Grid container>
                    <Grid item sm>
                        {renderTitle(enumDef.name)}
                    </Grid>
                    {renderEditButton(enumDef?.id)}
                    {renderDeleteWrapper()}
                </Grid>
                <div size={"small"}>
                    {enumDef.value}
                </div>
            </Card>
            <ConfirmationDialog
                onSubmit={prepareDelete}
                onClose={closeDeleteDialog}
                title={t("enum.delete.title")}
                message={t("enum.delete.message")}
                open={isDeleteDialogOpen}
            />
        </div>
    );
}

EnumCard.propTypes = {
    enumDef: PropTypes.object.isRequired,
    onEdit: PropTypes.func,
    handleDelete: PropTypes.func,
    editable: PropTypes.bool
};

EnumCard.defaultProps = {
    editable: true
};

export default EnumCard;
