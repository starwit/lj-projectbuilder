import React, { useEffect } from "react";
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
import LoadingSpinner from "../loadingSpinner/LoadingSpinner";
import AppTemplateDialogStyles from "./AppTemplateDialogStyles";
import { useTranslation } from "react-i18next";
import { Close } from "@mui/icons-material";

function AppTemplateDialog(props) {

    let [appTemplate, setAppTemplate] = React.useState(null);
    const appTemplateDialogStyles = AppTemplateDialogStyles();
    const { t } = useTranslation();

    const { appTemplateId, onClose, handleSave, appTemplates } = props;

    useEffect(() => {
        setAppTemplate({ ...appTemplates.find(appTemplate => appTemplate.id === appTemplateId) })
    }, [appTemplateId, appTemplates])

    if (!appTemplate) {
        return <LoadingSpinner message={t("appTemplateDialog.isLoading")} />
    }

    return (
        <Dialog open={appTemplateId} spacing={2}>
            <DialogTitle className={appTemplateDialogStyles.dialogHeaderBar}>
                <Typography noWrap variant={"h6"} component={"p"}>{t("appTemplateDialog.title", { appTemplateName: appTemplate.name })}</Typography>
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
                <TextField fullWidth label={t("appTemplateDialog.location")} value={appTemplate.location} />
                <TextField fullWidth label={t("appTemplateDialog.branch")} value={appTemplate.branch} />
                <TextField fullWidth label={t("appTemplateDialog.description")} value={appTemplate.description} />
                <DialogActions>
                    <Button onClick={() => handleSave(appTemplate)}>{t("button.save")}</Button>
                </DialogActions>
            </Box>
        </Dialog>
    )
}

export default AppTemplateDialog;