import React, { useEffect, useState } from "react";
import {
    Button,
    Box,
    Dialog,
    DialogActions,
    DialogTitle,
    IconButton,
    TextField,
    Typography
} from "@mui/material";
import AppTemplateDialogStyles from "./AppTemplateDialogStyles";
import { useTranslation } from "react-i18next";
import { Close } from "@mui/icons-material";
import AppTemplateRest from "../../services/AppTemplateRest"

function AppTemplateDialog(props) {

    const appTemplateDialogStyles = AppTemplateDialogStyles();
    const { t } = useTranslation();
    const [internalAppTemplate, setInternalAppTemplate] = useState(null);
    const appTemplateRest = new AppTemplateRest();

    const { appTemplate, open, onClose, onRefresh, isCreateDialog } = props;

    const onDialogClose = () => {
        onClose();
        setInternalAppTemplate(appTemplate);
    }

    function handleChange(event) {
        const { name, value } = event.target;
        let appTemplateNew = { ...internalAppTemplate };
        appTemplateNew[name] = value;
        setInternalAppTemplate(appTemplateNew);
    }

    function handleSave(toSave) {
        if (isCreateDialog) {
            appTemplateRest.create(toSave).then(response => {
                handleSaveResponse(response);
            });
        } else {
            appTemplateRest.update(toSave).then(response => {
                handleSaveResponse(response);
            });
        }
    }

    function handleSaveResponse(response) {
        setInternalAppTemplate(response.data);
        onRefresh();
        onClose();
    }

    useEffect(() => {
        setInternalAppTemplate(appTemplate);
    }, [appTemplate]);

    if (!appTemplate) {
        return null;
    }

    const insertTitle = () => {
        if (isCreateDialog) {
            return (<Typography noWrap variant={"h6"} component={"p"}>{t("appTemplateDialog.new.title")}</Typography>);
        } else {
            return (<Typography noWrap variant={"h6"} component={"p"}>{t("appTemplateDialog.title", { appTemplateName: internalAppTemplate.name })}</Typography>);
        }
    }

    return (
        <Dialog onClose={onDialogClose} open={open} spacing={2}>
            <DialogTitle className={appTemplateDialogStyles.dialogHeaderBar}>
                {insertTitle()}
                <div className={appTemplateDialogStyles.flex} />
                <IconButton
                    aria-label="close"
                    onClick={onDialogClose}
                >
                    <Close />
                </IconButton>
            </DialogTitle>
            <Box
                component="form"
                sx={{
                    '& .MuiTextField-root': { m: 1, width: '95%' },

                }}
                noValidate
                autoComplete="off"
            >
                <TextField fullWidth label={t("appTemplateDialog.location")} value={internalAppTemplate.location} name="location" onChange={handleChange} />
                <TextField fullWidth label={t("appTemplateDialog.branch")} value={internalAppTemplate.branch} name="branch" onChange={handleChange} />
                <TextField fullWidth label={t("appTemplateDialog.description")} value={internalAppTemplate.description} name="description" onChange={handleChange} />
                <DialogActions>
                    <Button onClick={onDialogClose}>{t("button.cancel")}</Button>
                    <Button onClick={() => handleSave(internalAppTemplate)}>{t("button.save")}</Button>
                </DialogActions>
            </Box>
        </Dialog>
    )
}

export default AppTemplateDialog;