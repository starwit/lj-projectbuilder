import React, { useEffect, useState } from "react";
import {
    Button,
    Box,
    Dialog,
    DialogActions,
    DialogTitle,
    IconButton,
    TextField,
    Typography,
    Checkbox,
    FormControlLabel,
    FormGroup
} from "@mui/material";
import AppTemplateDialogStyles from "./AppTemplateDialogStyles";
import { useTranslation } from "react-i18next";
import { Close } from "@mui/icons-material";
import AppTemplateRest from "../../services/AppTemplateRest"
import ErrorAlert from "../alert/ErrorAlert";

function AppTemplateDialog(props) {
    const { appTemplate, open, onClose, onRefresh, isCreateDialog } = props;
    const { t } = useTranslation();
    const [internalAppTemplate, setInternalAppTemplate] = useState(null);
    const [alert, setAlert] = useState({"open":false, "title": "ERROR", "message": ""});

    const appTemplateDialogStyles = AppTemplateDialogStyles();
    const appTemplateRest = new AppTemplateRest();


    const onDialogClose = () => {
        onClose();
        closeAlert();
        setInternalAppTemplate(appTemplate);
    }

    const handleChange = (event) => {
        const { name, value } = event.target;
        let appTemplateNew = { ...internalAppTemplate };
        appTemplateNew[name] = value;
        setInternalAppTemplate(appTemplateNew);
    }

    const handleCredentialsCheckbox = (event) => {
        const { name, checked } = event.target;
        let appTemplateNew = { ...internalAppTemplate };
        appTemplateNew[name] = checked;
        setInternalAppTemplate(appTemplateNew);
    }

    const handleSave = (toSave) => {
        if (isCreateDialog) {
            appTemplateRest.create(toSave).then(response => {
                handleSaveResponse(response);
            }).catch(err => {
                setAlert({"open":true, "title": t("alert.error"), "message": JSON.stringify(err.response.data)});
            });
        } else {
            appTemplateRest.update(toSave).then(response => {
                handleSaveResponse(response);
            }).catch(err => {
                setAlert({"open":true, "title": t("alert.error"), "message": JSON.stringify(err.response.data)});
            });
        }
    }

    const closeAlert = () => {
        setAlert({"open":false, "title": t("alert.error"), "message": ""});
    }

    const handleSaveResponse = (response) => {
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
                <ErrorAlert alert={alert} onClose={closeAlert} />
                <TextField fullWidth label={t("appTemplateDialog.location")} value={internalAppTemplate.location} name="location" onChange={handleChange} />
                <TextField fullWidth label={t("appTemplateDialog.branch")} value={internalAppTemplate.branch} name="branch" onChange={handleChange} />
                <TextField fullWidth label={t("appTemplateDialog.description")} value={internalAppTemplate.description} name="description" onChange={handleChange} />
                <FormGroup className={appTemplateDialogStyles.checkbox}>
                    <FormControlLabel fullWidth 
                        control={<Checkbox  checked={internalAppTemplate.credentialsRequired} 
                        name="credentialsRequired" onChange={handleCredentialsCheckbox} />} 
                        label={t("appTemplateDialog.credentialsRequired")} 
                    />
                </FormGroup>
                <DialogActions>
                    <Button onClick={onDialogClose}>{t("button.cancel")}</Button>
                    <Button onClick={() => handleSave(internalAppTemplate)}>{t("button.save")}</Button>
                </DialogActions>
            </Box>
        </Dialog>
    )
}

export default AppTemplateDialog;