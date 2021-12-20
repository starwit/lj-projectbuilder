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
import LoadingSpinner from "../loadingSpinner/LoadingSpinner";

function AppTemplateDialog(props) {

    const appTemplateDialogStyles = AppTemplateDialogStyles();
    const { t } = useTranslation();
    const [internalAppTemplate, setInternalAppTemplate] = useState(null);

    const { appTemplate, onClose, handleSave } = props;

    function handleAppTemplateEdit(event){
        console.log("event",event.target.name, event.target.value);
        const {name,value} = event.target;
        let appTemplateNew = {...internalAppTemplate};
        appTemplateNew[name] = value;
        setInternalAppTemplate(appTemplateNew);
    }

    useEffect(()=> {
        setInternalAppTemplate(appTemplate);
    }, [appTemplate])

    if(!appTemplate) {
        return null;
    }
    
    if(!internalAppTemplate && appTemplate) {
        return <LoadingSpinner message={t("appTemplateDialog.isLoading")} />
    }

    return (
        <Dialog open={appTemplate} spacing={2}>
            <DialogTitle className={appTemplateDialogStyles.dialogHeaderBar}>
                <Typography noWrap variant={"h6"} component={"p"}>{t("appTemplateDialog.title", { appTemplateName: internalAppTemplate.name })}</Typography>
                <div className={appTemplateDialogStyles.flex} />
                <IconButton
                    aria-label="close"
                    onClick={onClose}
                >
                    <Close />
                </IconButton>
            </DialogTitle>
            <Box
                component="form"
                sx={{
                    '& .MuiTextField-root': { m: 1, width: '98%' },

                }}
                noValidate
                autoComplete="off"
            >
                <TextField fullWidth label={t("appTemplateDialog.location")} value={internalAppTemplate.location} name="location" onChange={handleAppTemplateEdit}/>
                <TextField fullWidth label={t("appTemplateDialog.branch")} value={internalAppTemplate.branch} name="branch" onChange={handleAppTemplateEdit}/>
                <TextField fullWidth label={t("appTemplateDialog.description")} value={internalAppTemplate.description} name="description" onChange={handleAppTemplateEdit}/>
                <DialogActions>
                    <Button onClick={() => handleSave(internalAppTemplate)}>{t("button.save")}</Button>
                </DialogActions>
            </Box>
        </Dialog>
    )
}

export default AppTemplateDialog;